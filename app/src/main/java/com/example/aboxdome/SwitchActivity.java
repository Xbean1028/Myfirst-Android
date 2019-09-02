package com.example.aboxdome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;
import com.example.aboxdome.ContantsValues;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SwitchActivity extends AppCompatActivity {
    Button buttonback;
    Button btnres;
    TextView tvSwitchtt;
    static  String a8 = "ZigBee插座控制器2";
    private ImageView start1 =null; // 开始
    private ImageView start2 =null; // 开始
    protected boolean isBrewing = false; // 按钮置换
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_switch);




        //图片处理

        setContentView(R.layout.activity_switch);
        // 绑定
        start1 = (ImageView) findViewById(R.id.start1);
        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnChangeTaskon btnChangeTaskon =new BtnChangeTaskon();
                btnChangeTaskon.execute();
            }
        });
        start2 = (ImageView) findViewById(R.id.start2);
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnChangeTaskoff btnChangeTaskoff =new BtnChangeTaskoff();
                btnChangeTaskoff.execute();
            }
        });

        //ABRet state;
        buttonback = findViewById(R.id.btnswitchback);
        btnres = findViewById(R.id.btnres);
        //自动刷新
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                ReadTask readTask =new ReadTask();
                readTask.execute();
            }
        }, 0 , 500);
        //返回
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //刷新
        btnres.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReadTask readTask =new ReadTask();
                readTask.execute();
                Toast.makeText(SwitchActivity.this,"刷新",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onView(){
        Bitmap bmp1=BitmapFactory.decodeResource(getResources(), R.drawable.sock_on1);//打开资源图片
        Bitmap bmp2=BitmapFactory.decodeResource(getResources(), R.drawable.sock_on2);
        start1.setImageBitmap(bmp1);
        start2.setImageBitmap(bmp2);

        isBrewing = true;
    }
    //停止
    public void offView(){
        Bitmap bmp1= BitmapFactory.decodeResource(getResources(), R.drawable.sock_off1);//打开资源图片
        Bitmap bmp2= BitmapFactory.decodeResource(getResources(), R.drawable.sock_off2);
        start1.setImageBitmap(bmp1);
        start2.setImageBitmap(bmp2);

        isBrewing = false;
    }
    //读取插座状态
    public class ReadTask extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... voids) {
            ABRet state = ABSDK.getInstance().getSockStatus(a8);
            return state;
        }

        @Override
        protected void onPostExecute(ABRet state) {
            super.onPostExecute(state);
            tvSwitchtt = (TextView)findViewById(R.id.textView_switchtt);
            String returnopen = state.getCode();
            if (returnopen.equals("00000")) {
                Map statenow = state.getDicDatas();
                String status = statenow.get("status").toString();
                if (status.equals("1")) {
                    //开
                    onView();
                    tvSwitchtt.setText("插座状态：开启");
                } else {
                    //关
                    offView();
                    tvSwitchtt.setText("插座状态：关闭");
                }

            } else {
                //出错
                String retCode = state.getCode();
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast t = Toast.makeText(SwitchActivity.this, errMsg+retCode+"11", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
    //按钮控制控制插座on-off
    public class BtnChangeTaskon extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... params) {
            ABRet abRet = ABSDK.getInstance().getSockStatus(a8);;
            //获取
            Map statenow = abRet.getDicDatas();
            String status = statenow.get("status").toString();
            if (status.equals("1")) {
                //开
                abRet = ABSDK.getInstance().sockCtrl(a8, "0");
            } else {
                //abRet = ABSDK.getInstance().sockCtrl(a8, "1");
            }
            return abRet;
        }
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            String returnopen = abRet.getCode();
            if (returnopen.equals("00000")) {
                Toast.makeText(SwitchActivity.this,"power off",Toast.LENGTH_SHORT).show();
            } else {
                //出错
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(returnopen);
                Toast t = Toast.makeText(SwitchActivity.this, errMsg+"22", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
    //按钮控制控制插座off-on
    public class BtnChangeTaskoff extends AsyncTask<Void, Void, ABRet> {

        @Override
        protected ABRet doInBackground(Void... params) {
            ABRet abRet = ABSDK.getInstance().getSockStatus(a8);;
            //获取
            Map statenow = abRet.getDicDatas();
            String status = statenow.get("status").toString();
            if (status.equals("1")) {
                //开
                //abRet = ABSDK.getInstance().sockCtrl(a8, "0");
            } else {
                abRet = ABSDK.getInstance().sockCtrl(a8, "1");
            }
            return abRet;
        }
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);
            String returnopen = abRet.getCode();
            if (returnopen.equals("00000")) {
                Toast.makeText(SwitchActivity.this,"power on",Toast.LENGTH_SHORT).show();

            } else {
                //出错
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(returnopen);
                Toast t = Toast.makeText(SwitchActivity.this, errMsg+"22", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
}
