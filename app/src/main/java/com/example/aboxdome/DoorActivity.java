package com.example.aboxdome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DoorActivity extends AppCompatActivity {
    String door ="a8-门磁传感器";
    String i;
    Button redoor;
    Button remain;
    ImageView imdoor;
    Map<String, Object> dicDatas;
    TextView textView_door;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        re();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                re();
            }
        }, 0 , 1000);
        redoor=findViewById(R.id.btn_redoor);
        redoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re();
            }
        });
        remain=findViewById(R.id.btn_remain);
        remain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void re (){
        DoorTask doorTask = new DoorTask();
        doorTask.execute();

    }
    public class DoorTask extends AsyncTask<Void,Void,ABRet> {
        @Override
        protected ABRet doInBackground(Void... voids) {
            ABRet abRet = ABSDK.getInstance().getDoorStatus(door);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            textView_door=findViewById(R.id.text_door);
            dicDatas=abRet.getDicDatas();
            ContantsValues.init();
            String co=abRet.getCode();
            i=dicDatas.get("status").toString();
            String k = ContantsValues.codeValues.get(co);
            imdoor=findViewById(R.id.im_door);
            if(co.equals("00000")){
                if(i.equals("0")){
                    //关闭
                    imdoor.setImageResource(R.drawable.door_off);
                    textView_door.setText("门已关闭");
                }else{
                    //开启
                    imdoor.setImageResource(R.drawable.door_on);
                    textView_door.setText("门已开启");
                }
            }else {

                Toast t = Toast.makeText(DoorActivity.this, k, Toast.LENGTH_LONG);
                t.show();
            }
        }
    }
}
