package io.github.fver1004.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class ResultActivity extends AppCompatActivity {

    int buttonId[] = {R.id.button_Mon,R.id.button_Tue,R.id.button_Wed,R.id.button_Thu,R.id.button_Fri};
    int tableRowId[] = {R.id.r00, R.id.r01, R.id.r02,R.id.r03,R.id.r04,R.id.r05,R.id.r06,R.id.r07,R.id.r08,R.id.r09,R.id.r10,R.id.r11,R.id.r12};
    boolean table_Studentselected[][] = new boolean[6][65];
    boolean table_Sujectselected[][][];
    int     table_SSSelected[][][];
    boolean daySubects[][];

    Button back, complete;
    TextView yo;
    int subjectCount, rowCount=13, colCount=5, studentCount=6;
    //String subjectName[] = new String[6];
    int count_daySubjects[] = {0,0,0,0,0};//월,화,수,목,금
    //boolean count_forCheckExt[] = {false, false, false, false, false};
    String dayOfWeek[] ={"Mon", "Tue", "Wed", "Thu", "Fri"};
    SharedPreferences save;
    int selectedDayIndex = 1;
    Button dayButton[] = new Button[colCount];
    LinearLayout tableRow[] = new LinearLayout[rowCount];
    String scheduleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        subjectCount = intent.getIntExtra("subjectCount",0);
        scheduleName = intent.getStringExtra("ScheduleName");
        save = getSharedPreferences(scheduleName, 0);

        table_Sujectselected = new boolean[subjectCount][colCount][rowCount];
        table_SSSelected = new int[studentCount][colCount][rowCount];
        daySubects = new boolean[colCount][subjectCount];//[요일][수업]의 존재여부



        int n;
        for(int sIndex=0;sIndex < subjectCount; sIndex++){

            //과목값 세팅
            for(int rIndex= 0;rIndex < rowCount; rIndex++){
                n = save.getInt(scheduleName+"."+rIndex+"subjectTime"+sIndex,0);

                for(int cIndex =colCount-1; cIndex >= 0; cIndex--){
                    table_Sujectselected[sIndex][cIndex][rIndex] = (n & 1) ==1;
                    n = n >> 1;}}

            //요일당 과목 몇개 있는지 카운트++
            for(int cIndex = 0; cIndex < colCount; cIndex++){
                for(int rIndex= 0;rIndex < rowCount; rIndex++){
                    if(table_Sujectselected[sIndex][cIndex][rIndex] == true) {
                        count_daySubjects[cIndex]++;
                        daySubects[cIndex][sIndex] = true;
                        break;
                    }else{daySubects[cIndex][sIndex]=false;}}}
        }


        //ss 값 받아오기
        int ss;
        for(int stIndex=0;stIndex < studentCount; stIndex++){
            for(int rIndex= 0;rIndex < rowCount; rIndex++){
                ss = save.getInt(scheduleName+"."+stIndex+"SSSelected"+rIndex,9);
                for(int cIndex =colCount-1; cIndex >= 0; cIndex--){
                    table_SSSelected[stIndex][cIndex][rIndex] = ss % 10;
                    ss = ss / 10;
                }
            }
        }


            //TableRow 객체 받아오기
        for(int i = 0; i < rowCount; i++)
            tableRow[i] = (LinearLayout) findViewById(tableRowId[i]);

        //월요일 테이블 만들기
        //초기설정:월요일 선택
        //setDayTable();


        //버튼 리스너 설정
        yo = (TextView)findViewById(R.id.yo);
        back = (Button) findViewById(R.id.button_back);
        complete = (Button) findViewById(R.id.button_Complete);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {returnToSetSubjectActivity();}});
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }});
        for(int i = 0; i < colCount; i++){
            dayButton[i] = (Button) findViewById(buttonId[i]);
            dayButton[i].setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onClick(View view) {
                    setDayTable(view);

                }});}



    }



    TextView textViewForAdding;
    ArrayList<TextView> tableList = new ArrayList<TextView>();
    int clickedButtonIndex=0;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void setDayTable(View view){
        final int dpp = (int)getResources().getDimension(R.dimen.Dpp);

        clickedButtonIndex = Arrays.asList(dayButton).indexOf(view);
        if(clickedButtonIndex != selectedDayIndex){
            dayButton[selectedDayIndex].setBackgroundColor(0x00000000);
            dayButton[clickedButtonIndex].setBackgroundColor(0x77ffffff);



            //기존에 존재하는 테이블 삭제
            for(TextView v : tableList){
                for(int i = 0; i < count_daySubjects[selectedDayIndex];i++){
                    for(int j = 0; j < rowCount;j++){
                        tableRow[j].removeView(v);
                    }
                }
            }

            //테이블 생성
            LinearLayout.LayoutParams viewParams_1 = new LinearLayout.LayoutParams
                    (0, ViewGroup.LayoutParams.MATCH_PARENT,1);
            viewParams_1.setMargins(0,0,dpp,0);

            for(int sIndex = 0; sIndex < subjectCount;sIndex++){
                if(daySubects[clickedButtonIndex][sIndex]){
                    for(int rIndex = 0; rIndex < rowCount; rIndex++){
                        textViewForAdding = new TextView(this);
                        textViewForAdding.setBackgroundColor(0xffffffff);
                        textViewForAdding.setLayoutParams(viewParams_1);

                        //해당시간에 해당수업 있을 경우
                        if(table_Sujectselected[sIndex][clickedButtonIndex][rIndex] == true){
                            //textViewForAdding.setText(""+sIndex);
                            textViewForAdding.setBackgroundColor(0xbbffffff);
                            textViewForAdding.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            String name = save.getString(scheduleName+"."+"SubjectName"+sIndex,"Subject") + "\n";

                            //무슨학년이 듣는지 확인
                            for(int stIndex =0; stIndex < studentCount; stIndex++)
                                    if(table_SSSelected[stIndex][clickedButtonIndex][rIndex]==sIndex)
                                        name = name + (stIndex+1) + " ";


                            textViewForAdding.setText(name);

                        }



                        tableRow[rIndex].addView(textViewForAdding);
                        tableList.add(textViewForAdding);

                    }
                }
            }


            selectedDayIndex = clickedButtonIndex;
            yo.setText(dayOfWeek[clickedButtonIndex]);


        }

    }


    void addTable(){

    }



    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"저장되지 않은 채로 끝내겠다?Y/N 다이얼로그 출력",Toast.LENGTH_LONG).show();
    }

    void returnToSetSubjectActivity(){
        Intent intent = new Intent(this, SetSubjectActivity.class);
        intent.putExtra("ScheduleName",scheduleName);
        startActivity(intent);
        finish();
    }


}
