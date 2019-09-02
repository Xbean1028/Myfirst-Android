package com.example.aboxdome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class StartActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String usname;
    String psword;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        sharedPreferences = getSharedPreferences(ContantsValues.HH_FILENAME,MODE_PRIVATE);
        usname=sharedPreferences.getString(ContantsValues.HH_USERNAME,"no");
        psword=sharedPreferences.getString(ContantsValues.HH_PASSWORD,"no");
        flag=sharedPreferences.getString(ContantsValues.HH_AUTOLOGIN,"x");
        if(flag.equals("x")){
            Intent intent = new Intent();
            intent.setClass(StartActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            if(usname.equals("no")){
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                LoginTask loginTask = new LoginTask();
                loginTask.execute(psword,usname);
            }
        }
    }
    public class LoginTask extends AsyncTask<String,Void, ABRet> {
        String password;
        String username;

        @Override
        protected ABRet doInBackground(String... a) {
            password = a[0];
            username = a[1];
            ABRet abRet = ABSDK.getInstance().loginWithUsername(username, password);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {

            super.onPostExecute(abRet);
            String co = abRet.getCode();
            if (co.equals("00000")) {
                Toast t = Toast.makeText(StartActivity.this, "登录成功", Toast.LENGTH_LONG);
                t.show();
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast t = Toast.makeText(StartActivity.this, "登录失败", Toast.LENGTH_LONG);
                t.show();
                Intent intent = new Intent();
                intent.setClass(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
