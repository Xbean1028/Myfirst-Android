package com.example.aboxdome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class THActivity extends AppCompatActivity {
    String th ="a8-温湿度传感器";
    Button bth_th;
    Button bth_ret;
    String j;
    String i;
    Map<String, Object> dicDatas;
    TextView text_t,text_h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_th);
        reth();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                reth();
            }
        }, 0 , 1000);
        bth_th=(Button) findViewById(R.id.btn_reth);
        bth_th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reth();
            }
        });
        bth_ret=(Button)findViewById(R.id.btn_ret);
        bth_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void reth (){
        ThTask thTask =new ThTask();
        thTask.execute();
    }
    public class ThTask extends AsyncTask<Void,Void,ABRet>{

        @Override
        protected ABRet doInBackground(Void... voids) {
            ABRet abRet = ABSDK.getInstance().getThStatus(th);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            text_h=findViewById(R.id.text_ha);
            text_t=findViewById(R.id.text_ta);
            ContantsValues.init();
            String co=abRet.getCode();
            dicDatas=abRet.getDicDatas();
            i=dicDatas.get("temperature").toString();
            j=dicDatas.get("humidity").toString();
            String k=ContantsValues.codeValues.get(co);

            if(co.equals("00000")){
                //显示
                text_t.setText(i+" ℃");
                text_h.setText(j+" %");
            }else {

                Toast t = Toast.makeText(THActivity.this, k, Toast.LENGTH_LONG);
                t.show();
            }
        }
    }
}
