package io.github.fver1004.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by Kiyeon on 2017. 7. 4..
 */
public class StartOption1Activity extends AppCompatActivity {

    SharedPreferences save;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startoption1);

        final EditText editText = (EditText) findViewById(R.id.setScheduleName);
        Button button = (Button) findViewById(R.id.setScheduleName_button);
        save = getSharedPreferences("ScheduleList", 0);
        editor = save.edit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scheduleName = editText.getText().toString();

                //해당 이름이 존재하지 않을 경우
                if(save.getBoolean(scheduleName, false) == false){
                    editor.putBoolean(scheduleName,true);
                    editor.putInt("subjectCount",0);
                    editor.commit();
                    Intent intent = new Intent(StartOption1Activity.this, SetSubjectActivity.class);
                    intent.putExtra("ScheduleName",scheduleName);
                    startActivity(intent);

                //존재할 경우
                }else{
                    Toast.makeText(getApplicationContext(),"이미 존재하는 이름입니다.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
