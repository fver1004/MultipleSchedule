package io.github.fver1004.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class TableActivity extends AppCompatActivity {
    TextView title;
    TextView table[] = new TextView[80];
    boolean table_Sujectselected[] = new boolean[65];
    boolean table_Studentselected[][] = new boolean[6][65];
    int     table_SSselected[][] = new int[6][65];
    SharedPreferences save;
    SharedPreferences.Editor editor;
    int studentCount = 6;
    int tableid[] = {
            R.id.t000,R.id.t001,R.id.t002,R.id.t003,R.id.t004,
            R.id.t010,R.id.t011,R.id.t012,R.id.t013,R.id.t014,
            R.id.t020,R.id.t021,R.id.t022,R.id.t023,R.id.t024,
            R.id.t030,R.id.t031,R.id.t032,R.id.t033,R.id.t034,
            R.id.t040,R.id.t041,R.id.t042,R.id.t043,R.id.t044,
            R.id.t050,R.id.t051,R.id.t052,R.id.t053,R.id.t054,
            R.id.t060,R.id.t061,R.id.t062,R.id.t063,R.id.t064,
            R.id.t070,R.id.t071,R.id.t072,R.id.t073,R.id.t074,
            R.id.t080,R.id.t081,R.id.t082,R.id.t083,R.id.t084,
            R.id.t090,R.id.t091,R.id.t092,R.id.t093,R.id.t094,
            R.id.t100,R.id.t101,R.id.t102,R.id.t103,R.id.t104,
            R.id.t110,R.id.t111,R.id.t112,R.id.t113,R.id.t114,
            R.id.t120,R.id.t121,R.id.t122,R.id.t123,R.id.t124};

/*onCreate---------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);



        //타이틀 객체 받기
        title = (TextView)findViewById(R.id.tableActivity_title);

        //테이블 selected값 초기화
        for(int i = 0; i < 65; i++){
            table_Sujectselected[i] = false;
            table[i] = (TextView) findViewById(tableid[i]);
            for(int j=0;j<studentCount;j++) {
                //table_Studentselected[j][i] = false;
                table_SSselected[j][i] = 9;
            }
        }
        /*
        String test1 = "", test2 = "";
        for(int stIndex = 0; stIndex< studentCount; stIndex++){
            test1 = test1 + (stIndex+1)+"학년:\n";
            test2 = test2 + (stIndex+1)+"학년:\n";
            for(int rIndex = 0; rIndex < 13; rIndex++){

                for(int cIndex = 0; cIndex < 5; cIndex++){
                    test1 = test1 + table_SSselected[stIndex][(5*rIndex)+cIndex] + ",";
                    test2 = test2 + table_SSselected[stIndex][(5*rIndex)+cIndex] + ",";
                }
                test1 = test1 + "\n";
                test2 = test2 + "\n";

            }
            test1 = test1 + "\n";
            test2 = test2 + "\n";
        }
        Log.d("asdf","초기 studentSelected:\n"+test1);
        Log.d("asdf","초기 ssSelected:\n"+test2);
        */

    }


/*onResume----------------------------------------------------*/
    int option, subjectCount, subjectIndex;
    String scheduleName;

    @Override
    protected void onResume() {
        super.onResume();

        //인텐트로 값 받아오기
        Intent intent = getIntent();
        option = intent.getExtras().getInt("option");
        scheduleName = intent.getStringExtra("ScheduleName");
        //Toast.makeText(getApplicationContext(),"Resume:값받기:"+scheduleName,Toast.LENGTH_LONG).show();

        //SharedPreference 로드
        save = getSharedPreferences(scheduleName, 0);
        editor = save.edit();

        //완료되고 다음 넘어온거
        if(option==0){
            subjectCount = intent.getExtras().getInt("subjectCount");
            tableSelect();}
        //설정 창
        else{
            subjectIndex = intent.getExtras().getInt("subjectIndex");
            subjectSelect();}

    }

/*tableSelect : 3단계--------------------------------------------*/
    void tableSelect(){
        title.setText("학년별 시간 설정");

        //table_ss, table_st 값 받아오기
        int ss, nn;
        for(int stIndex=0;stIndex < studentCount; stIndex++){
            for(int rIndex= 0;rIndex < 13; rIndex++){
                ss = save.getInt(scheduleName+"."+stIndex+"SSSelected"+rIndex,99999);
                nn = save.getInt(scheduleName+"."+stIndex+"StudentSelected"+rIndex,0);
                for(int cIndex =5-1; cIndex >= 0; cIndex--){
                    table_SSselected[stIndex][(5*rIndex)+cIndex] = ss % 10;
                    ss = ss / 10;
                    table_Studentselected[stIndex][(5*rIndex)+cIndex] = (nn & 1) == 1;
                    //Log.d("asdf",""+table_Studentselected[stIndex][(5*rIndex)+cIndex]);
                    nn = nn >> 1;
                }
            }
        }


        //학생기준 테이블 리스너;
        for(int i = 0; i < 65; i++){

            table[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(studentSelected==10)
                        Toast.makeText(getApplicationContext(),"학년을 선택하세요",Toast.LENGTH_SHORT).show();
                    else{
                        int getTableIndex = Arrays.asList(table).indexOf(view);
                        if(table_Sujectselected[getTableIndex]==false){
                            //view.
                        }
                        else {
                            if(table_Studentselected[studentSelected][getTableIndex]==false){
                                table[getTableIndex].setTextColor(0xff000000);
                                table[getTableIndex].setText((studentSelected + 1) + "학년");
                                table_Studentselected[studentSelected][getTableIndex]=true;
                                table_SSselected[studentSelected][getTableIndex]=subjectSelected;

                            }else{
                                //해당 값이 true일때, 해당 수업에 의해 설정되어있을 때
                                if(table_SSselected[studentSelected][getTableIndex]==subjectSelected){
                                    table[getTableIndex].setText("");
                                    table_Studentselected[studentSelected][getTableIndex]=false;
                                    table_SSselected[studentSelected][getTableIndex]=9;//9는 없는것!
                                }
                                //해당 값이 true일 때, 해당 수업에 의해 설정되어있지 않을 때
                                else{
                                    Toast.makeText(getApplicationContext(),"해당 시간에 다른 수업이 잡혀있습니다.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            String test1 = "", test2 = "";
                            for(int stIndex = 0; stIndex< studentCount; stIndex++){
                                test1 = test1 + (stIndex+1)+"학년:\n";
                                test2 = test2 + (stIndex+1)+"학년:\n";
                                for(int rIndex = 0; rIndex < 13; rIndex++){

                                    for(int cIndex = 0; cIndex < 5; cIndex++){
                                        test1 = test1 + table_SSselected[stIndex][(5*rIndex)+cIndex] + ",";
                                        test2 = test2 + table_SSselected[stIndex][(5*rIndex)+cIndex] + ",";
                                    }
                                    test1 = test1 + "\n";
                                    test2 = test2 + "\n";

                                }
                                test1 = test1 + "\n";
                                test2 = test2 + "\n";
                            }
                            Log.d("asdf"," studentSelected:\n"+test1);
                            Log.d("asdf"," ssSelected:\n"+test2);

                        }
                    }

                }
            });
        }

        //버튼초기화
        setStudentButton();
        setSubjectButton();
        setBottomLeftButton();
        setSaveButton();

    }



/*subjectSelect : 2.5단계--------------------------------------*/
    int remove_sbuttonid[]={R.id.student_all, R.id.student1,R.id.student2,R.id.student3,
            R.id.student4,R.id.student5,R.id.student6};

    void subjectSelect(){

        title.setText("해당과목의 수업시간을 선택하세요.");
        LinearLayout subjectList_layout = (LinearLayout) findViewById(R.id.subject_list);
        final EditText subjectNameEdit = new EditText(this);
        subjectList_layout.addView(subjectNameEdit);

        //학년버튼 없애기
        for(int i=0;i<7;i++) {
            Button remove_sbutton = (Button) findViewById(remove_sbuttonid[i]);
            remove_sbutton.setText("");

        }

        //테이블 설정
        for(int i = 0; i < 65; i++){

            table[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int getTableIndex = Arrays.asList(table).indexOf(view);
                    if(table_Sujectselected[getTableIndex]==false){
                        table_Sujectselected[getTableIndex] = true;//선택함
                        view.setBackgroundColor(0xbbffffff);}
                    else{
                        table_Sujectselected[getTableIndex] = false;//선택안함
                        view.setBackgroundColor(0xffffffff);}
                }
            });
        }


        //왼쪽하단 버튼 설정

        TextView returnToSubjectActivityButton = (TextView) findViewById(R.id.button_bottomleft);
        returnToSubjectActivityButton.setText("설정 저장");
        returnToSubjectActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Subject 내용, Table 내용 저장
                int length = 5;
                int n;
                for(int i = 0; i < 13; i++){
                    n = 0;
                    for (int j = 0; j < length; j++){
                        n = (n << 1 ) + (table_Sujectselected[(5*i)+j] ? 1 : 0);
                    }
                    editor.putInt(scheduleName+"."+i+"subjectTime"+subjectIndex,n);
                }
                editor.putString(scheduleName+"."+"SubjectName"+subjectIndex,subjectNameEdit.getText().toString());
                editor.commit();
                returnToSetSubjectActivity();
            }
        });


        //상태 불러오기 후 테이블 갱신
        subjectNameEdit.setText(save.getString(scheduleName+"."+"SubjectName"+subjectIndex,"Subject"));

        int length = 5;
        int n;
        for(int i = 0; i < 13; i++){
            n = save.getInt(scheduleName+"."+i+"subjectTime"+subjectIndex, 0);
            for (int j = length-1; j >=0; j--){
                table_Sujectselected[(5*i)+j] = (n & 1) == 1;
                n = n >> 1;
                if(table_Sujectselected[(5*i)+j]==true)
                    table[(5*i)+j].setBackgroundColor(0xbbffffff);
            }}


    }


/*setStudentButton----------------------------------------------------*/
    int studentSelected = 10;
    Button studentButton[] = new Button[studentCount];
    int studentButtonid[]={
            R.id.student1,R.id.student2,R.id.student3,
            R.id.student4,R.id.student5,R.id.student6};

    void setStudentButton(){
        for(int i=0;i<6;i++){
            studentButton[i] = (Button) findViewById(studentButtonid[i]);
            studentButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //버튼색상 변경
                    studentSelected=Arrays.asList(studentButton).indexOf(view);
                    for(int i=0;i<studentCount;i++) {
                        if (i == studentSelected)
                            studentButton[i].setBackgroundColor(0x00000000);
                        else
                            studentButton[i].setBackgroundColor(0xff2183b0);
                    }

                    //선택값 불러오기
                    for(int i=0;i<65;i++){


                        if(table_Studentselected[studentSelected][i]==true){
                            table[i].setText((studentSelected+1)+"학년");
                        }else
                            table[i].setText("");
                    }

                }
            });
        }
    }


/*setSubjectButton---------------------------------------------*/
    ArrayList<Button> subjectBList = new ArrayList<Button>();
    int subjectSelected = 0;
    Button Sbutton;
    void setSubjectButton(){
        //버튼 삭제 후 갱신
        LinearLayout subjectList_layout = (LinearLayout) findViewById(R.id.subject_list);
        for(View view : subjectBList)
            subjectList_layout.removeView(view);



        for(int i=0;i<subjectCount;i++){
            Sbutton = new Button(this);
            Sbutton.setText(save.getString(scheduleName+"."+"SubjectName"+i,"제목없음"));
            Toast.makeText(getApplicationContext(),"값: "+scheduleName,Toast.LENGTH_LONG).show();

            //버튼 옵션 설정
            LinearLayout.LayoutParams subjectB_Params = new LinearLayout.LayoutParams
                    (0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            Sbutton.setLayoutParams(subjectB_Params);
            Sbutton.setBackgroundColor(0xff2183b0);

            //버튼 리스너 설정
            Sbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subjectSelected=subjectBList.indexOf(view);
                    for(int i=0;i<subjectCount;i++) {
                        if (i == subjectSelected)
                            subjectBList.get(i).setBackgroundColor(0x00000000);
                        else
                            subjectBList.get(i).setBackgroundColor(0xff2183b0);}

                    int length = 5;
                    int n;
                    for(int i = 0; i < 13; i++){
                        n = save.getInt(scheduleName+"."+i+"subjectTime"+subjectSelected, 0);
                        for (int j = length-1; j >=0; j--){
                            table_Sujectselected[(5*i)+j] = (n & 1) == 1;
                            n = n >> 1;
                            if(table_Sujectselected[(5*i)+j]==true)
                                table[(5*i)+j].setBackgroundColor(0xbbffffff);
                            else
                                table[(5*i)+j].setBackgroundColor(0xffffffff);

                            //이미 선택된 텍스트값 빨간색으로
                            if(studentSelected==10){}
                            else if(table_SSselected[studentSelected][(5*i)+j]==subjectSelected)
                                table[(5*i)+j].setTextColor(0xff000000);
                            else
                                table[(5*i)+j].setTextColor(0x77ff0000);
                        }}


                }});


            subjectList_layout.addView(Sbutton);
            //저장
            subjectBList.add(Sbutton);
        }
    }


    void setBottomLeftButton(){
        TextView returnToSubjectActivityButton = (TextView) findViewById(R.id.button_bottomleft);
        returnToSubjectActivityButton.setText("이전");
        returnToSubjectActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToSetSubjectActivity();
            }
        });
    }

    void setSaveButton(){
        TextView saveButton = (TextView) findViewById(R.id.button_save);
        saveButton.setText("완료");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = 5;
                int st_array, ss_array;
                for(int rIndex = 0; rIndex < 13; rIndex++){
                    for(int stIndex = 0; stIndex < studentCount; stIndex++) {
                        st_array = 0;
                        ss_array = 0;
                        for (int j = 0; j < length; j++) {
                            st_array = (st_array << 1) + (table_Studentselected[stIndex][(5 * rIndex) + j] ? 1 : 0);
                            ss_array = (ss_array * 10) + table_SSselected[stIndex][5 * rIndex +j];
                        }
                        editor.putInt(scheduleName+"."+stIndex+"StudentSelected"+rIndex,st_array);
                        editor.putInt(scheduleName+"."+stIndex+"SSSelected"+rIndex,ss_array);
                    }

                }
                editor.commit();


                /*
                String test1 = "", test2 = "";
                for(int stIndex = 0; stIndex< studentCount; stIndex++){
                    test1 = test1 + (stIndex+1)+"학년:\n";
                    test2 = test2 + (stIndex+1)+"학년:\n";
                    for(int rIndex = 0; rIndex < 13; rIndex++){
                        test1 = test1 + save.getInt(scheduleName+"."+stIndex+"StudentSelected"+rIndex,0) + ",";
                        test2 = test2 + save.getInt(scheduleName+"."+stIndex+"SSSelected"+rIndex,0)+",";
                    }
                    test1 = test1 + "\n";
                    test2 = test2 + "\n";
                }
                Log.d("asdf","studentSelected:\n"+test1);
                Log.d("asdf","ssSelected:\n"+test2);
                */

                Intent intent = new Intent(TableActivity.this, ResultActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("subjectCount",subjectCount);
                intent.putExtra("ScheduleName",scheduleName);

                //이전 액티비티 종료하면서 결과창으로
                SetSubjectActivity subjectActivity = (SetSubjectActivity)SetSubjectActivity.subjectActivity;
                subjectActivity.finish();

                startActivity(intent);
                finish();
            }});
    }




    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"저장되지 않은 채로 끝내겠다?Y/N 다이얼로그 출력",Toast.LENGTH_LONG).show();
    }

    void returnToSetSubjectActivity(){
        Intent intent = new Intent(this, SetSubjectActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
