package com.example.aboxdome;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class InfraRedActivity extends AppCompatActivity {

    Button btnHome;
    Button btnStuidyHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infra_red);

        //学习主页
        btnStuidyHome = (Button)findViewById(R.id.button_study_home);
        btnStuidyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyHomeTask studyHomeTask = new StudyHomeTask();
                studyHomeTask.execute();
            }
        });

        //发送主页信号
        btnHome = (Button)findViewById(R.id.button_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeTask homeTask = new HomeTask();
                homeTask.execute();
            }
        });

    }

    public class StudyHomeTask extends AsyncTask<Void,Void, ABRet>{

        @Override
        public ABRet doInBackground(Void... params){
            ABRet abRet = null;
            abRet = ABSDK.getInstance().studyIrByIrDevName(ContantsValues.DE_RED,"001");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            String retCode = abRet.getCode();
            if(retCode.equals("00000")){
                Toast.makeText(InfraRedActivity.this,"学习成功",Toast.LENGTH_SHORT).show();

            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(InfraRedActivity.this,"学习："+errMsg,Toast.LENGTH_SHORT).show();

            }
        }
    }

    public class HomeTask extends AsyncTask<Void,Void, ABRet>{

        @Override
        public ABRet doInBackground(Void... params){
            ABRet abRet = null;
            abRet = ABSDK.getInstance().sendIr(ContantsValues.DE_RED,"001");
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            String retCode = abRet.getCode();
            if(retCode.equals("00000")){
                Toast.makeText(InfraRedActivity.this,"发送成功",Toast.LENGTH_SHORT).show();

            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(InfraRedActivity.this,"发送"+errMsg,Toast.LENGTH_SHORT).show();

            }
        }
    }
}
