package io.github.fver1004.schedule;

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
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kiyeon on 2017. 7. 4..
 */

public class StartOption2Activity extends AppCompatActivity {
    ArrayList<LinearLayout> list_scheduleLayout = new ArrayList<LinearLayout>();
    ArrayList<TextView> list_scheduleN = new ArrayList<TextView>();
    ArrayList<Button> list_scheduleB = new ArrayList<Button>();
    LinearLayout scheduleList, scheduleLayout;
    SharedPreferences save;
    SharedPreferences.Editor editor;
    TextView scheduleN;
    Button scheduleB;
    String sName;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startoption2);

        save = getSharedPreferences("ScheduleList", 0);
        editor = save.edit();

        scheduleList = (LinearLayout) findViewById(R.id.ScheduleList);
        createList();

    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void createList(){
        int linear_height = (int)getResources().getDimension(R.dimen.Linear_height);
        int linear_padding = (int)getResources().getDimension(R.dimen.Linear_padding);
        int linear_margin_bottom = (int)getResources().getDimension(R.dimen.Linear_margin_bottom);

        //저장되어있는 시간표<타이틀,존재유무> 쌍 가져오기
        Map<String,?> lists = save.getAll();
        int length = lists.size();

        //시간표 개수만큼 리스트 생성
        for(Map.Entry<String,?> entry : lists.entrySet()){
            if(entry.getValue().toString() == "true"){
                sName = entry.getKey();
                //Log.d("asdf","sName은 : "+sName);

                scheduleLayout = new LinearLayout(this);
                scheduleN = new TextView(this);
                scheduleB = new Button(this);

                //옵션 설정.
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                linear_height);
                layoutParams.setMargins(0,0,0,linear_margin_bottom);
                scheduleLayout.setOrientation(LinearLayout.HORIZONTAL);
                scheduleLayout.setLayoutParams(layoutParams);
                scheduleLayout.setBackgroundColor(0xff2183b0);
                scheduleLayout.setPadding(linear_padding,linear_padding,linear_padding,linear_padding);

                LinearLayout.LayoutParams nameViewParams = new LinearLayout.LayoutParams
                        (0, ViewGroup.LayoutParams.MATCH_PARENT, 2);
                scheduleN.setLayoutParams(nameViewParams);
                scheduleN.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                scheduleN.setTextColor(0xffffffff);
                scheduleN.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                scheduleN.setText(sName);


                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams
                        (0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                scheduleB.setLayoutParams(buttonParams);
                scheduleB.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                scheduleB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                scheduleB.setText("샥제");
                scheduleB.setTextColor(0xff2173a0);
                scheduleB.setBackgroundColor(0xffffffff);

                scheduleB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(StartOption2Activity.this, SetSubjectActivity.class);
                        intent.putExtra("ScheduleName",list_scheduleN.get(list_scheduleB.indexOf(view)).getText().toString());
                        //Log.d("asdf","보내는값: "+sName);
                        startActivity(intent);
                        finish();
                    }
                });

                //뷰 삽입
                scheduleLayout.addView(scheduleN);
                scheduleLayout.addView(scheduleB);
                scheduleList.addView(scheduleLayout);

                list_scheduleLayout.add(scheduleLayout);
                list_scheduleN.add(scheduleN);
                list_scheduleB.add(scheduleB);


            }
        }
    }

    void removeList(){

    }
}
