package io.github.fver1004.schedule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
/**
 * Created by Kiyeon on 2017. 6. 30..
 */
public class SetSubjectActivity extends AppCompatActivity {

    public static Activity subjectActivity;
    ArrayList<LinearLayout> layoutList = new ArrayList<LinearLayout>();
    ArrayList<TextView> buttonList = new ArrayList<TextView>();
    ArrayList<TextView> nameList = new ArrayList<TextView>();
    ArrayList<TextView> timeList = new ArrayList<TextView>();
    LinearLayout listBox, linearLayout;
    TextView button, nameView, timeView;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    Intent getIntent;
    int subjectCount=0;
    String scheduleName;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        subjectActivity = SetSubjectActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setsubject);

        //초기 값 받아오기
        getIntent = getIntent();
        scheduleName = getIntent.getStringExtra("ScheduleName");
        Log.d("asdf","받는값: "+scheduleName);
        save = getSharedPreferences(scheduleName, 0);
        editor = save.edit();
        subjectCount = save.getInt(scheduleName+"."+"subjectCount",0);

        listBox = (LinearLayout) findViewById(R.id.listBox);

        //Next버튼 초기화
        TextView nextButton = (TextView) findViewById(R.id.buttonToTable);
        Button addButton = (Button) findViewById(R.id.addSubjectButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //완료값이랑 같이 보내기
                editor.putInt(scheduleName+"."+"subjectCount",subjectCount);
                editor.commit();
                Intent intent = new Intent(SetSubjectActivity.this, TableActivity.class);
                intent.putExtra("option",0);
                intent.putExtra("subjectCount",subjectCount);
                intent.putExtra("ScheduleName",scheduleName);
                startActivity(intent);
            }
        });

        //add버튼 초기화
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subjectCount<9){
                    subjectCount++;
                    createList();
                }else
                    Toast.makeText(getApplicationContext(),"9개이상 생성 불가",Toast.LENGTH_LONG).show();
            }});

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();

        subjectCount = save.getInt(scheduleName+"."+"subjectCount",0);

        //초기화 - 삭제
        if(subjectCount != 0){
            int length = layoutList.size();
            for(int i=0;i < length;i++){
                nameList.remove(0);
                timeList.remove(0);
                buttonList.remove(0);
                layoutList.get(0).removeAllViews();
                layoutList.remove(0);}
            listBox.removeAllViews();}


        //초기화 - 생성
        for(int i = 0; i<subjectCount;i++){
            createList();
            nameList.get(i).setText(save.getString(scheduleName+"."+"SubjectName"+i,"Subject"));}

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void createList(){

        final int linear_height = (int)getResources().getDimension(R.dimen.Linear_height);
        final int linear_padding = (int)getResources().getDimension(R.dimen.Linear_padding);
        final int linear_margin_bottom = (int)getResources().getDimension(R.dimen.Linear_margin_bottom);

        //새로운 레이아웃, 뷰 생성
        linearLayout = new LinearLayout(SetSubjectActivity.this);
        button = new TextView(SetSubjectActivity.this);
        nameView = new TextView(SetSubjectActivity.this);
        timeView = new TextView(SetSubjectActivity.this);

        //옵션 설정.
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        linear_height);
        layoutParams.setMargins(0,0,0,linear_margin_bottom);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setBackgroundColor(0xff2183b0);
        linearLayout.setPadding(linear_padding,linear_padding,linear_padding,linear_padding);

        LinearLayout.LayoutParams nameViewParams = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.MATCH_PARENT, 2);
        nameView.setLayoutParams(nameViewParams);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        nameView.setTextColor(0xffffffff);
        nameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        nameView.setText("Subject");

        LinearLayout.LayoutParams timeViewParams = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.MATCH_PARENT, 4);
        timeView.setLayoutParams(timeViewParams);
        timeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        timeView.setTextColor(0xffffffff);
        timeView.setText("initialized");

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        button.setLayoutParams(buttonParams);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        button.setText("설정");
        button.setTextColor(0xff2173a0);
        button.setBackgroundColor(0xffffffff);

        //버튼 리스너 삽입
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //테이블 액티비티로 넘어간다
                //넘어갈때 수정할 값 다 보내준다.
                editor.putInt(scheduleName+"."+"subjectCount",subjectCount);
                editor.commit();
                Intent intent = new Intent(SetSubjectActivity.this, TableActivity.class);
                intent.putExtra("option",1);
                intent.putExtra("subjectIndex",buttonList.indexOf(view));
                intent.putExtra("ScheduleName",scheduleName);
                startActivity(intent);
            }
        });

        //뷰 삽입
        linearLayout.addView(nameView);
        linearLayout.addView(timeView);
        linearLayout.addView(button);
        listBox.addView(linearLayout);

        //arrayList에 저장
        layoutList.add(linearLayout);
        nameList.add(nameView);
        timeList.add(timeView);
        buttonList.add(button);
    }



    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"저장되지 않은 채로 끝내겠다?Y/N 다이얼로그 출력",Toast.LENGTH_LONG).show();
    }

}
