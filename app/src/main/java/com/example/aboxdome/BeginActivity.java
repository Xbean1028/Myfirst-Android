package com.example.aboxdome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class BeginActivity extends AppCompatActivity {
    
    private static final String TAG = "BeginActivity";
    SharedPreferences sharedPreferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_begin);

        Log.d(TAG, "onCreate: lalala...");
        sharedPreferences = getSharedPreferences(ContantsValues.HH_FILENAME,MODE_PRIVATE);
        String autologin = sharedPreferences.getString(ContantsValues.HH_AUTOLOGIN,"0");
        if(autologin.equals("0")){
            Intent intent = new Intent(BeginActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            String username = sharedPreferences.getString(ContantsValues.HH_USERNAME,"nobody");
            String password = sharedPreferences.getString(ContantsValues.HH_PASSWORD,"");
            //自动登录，但是还要判断用户名和密码是否正确，因为有可能修改了密码，还有获取token
            LoginTask loginTask = new LoginTask();
            loginTask.execute(username,password);
        }
    }
    //内部类用于  登录操作
    public class LoginTask extends AsyncTask<String,Void,ABRet> {
        private String username;
        private String password;

        //后台操作
        @Override
        protected ABRet doInBackground(String... params) {
            username = params[0];
            password = params[1];
            Log.d(TAG, "doInBackground: "+username+":  :"+password);

            ABRet abRet = null;
            try {
                abRet = ABSDK.getInstance().loginWithUsername(username, password);
            }catch(Exception e){
                e.printStackTrace();
            }
            return abRet;
        }
        //前端操作（UI线程）
        @Override
        protected void onPostExecute(ABRet abRet) {
            super.onPostExecute(abRet);

            String retCode  = abRet.getCode();

            //判断abRet的返回值，“00000”为成功
            if(retCode.equals("00000")){
                //登录验证通过后，选择跳转到主页面
                Toast.makeText(BeginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(BeginActivity.this,MainActivity.class);
                startActivity(intent);

            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(BeginActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BeginActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
