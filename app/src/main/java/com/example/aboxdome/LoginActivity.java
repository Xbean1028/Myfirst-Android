package com.example.aboxdome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    SharedPreferences sharedPreferences;
    private Button btnLogin;
    private EditText etUser;
    private EditText etPass;
    private CheckBox cbLogin;
    private CheckBox cbRemem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(ContantsValues.HH_FILENAME,MODE_PRIVATE);
        etUser = (EditText)findViewById(R.id.editText_user);
        etPass = (EditText)findViewById(R.id.editText_pass);
        btnLogin = (Button)findViewById(R.id.button_login);
        cbLogin = (CheckBox)findViewById(R.id.checkBox_login);//自动登录
        cbRemem = (CheckBox)findViewById(R.id.checkBox_remem);//记住密码

        String remember = sharedPreferences.getString(ContantsValues.HH_REMEMBER,"0");
        if(remember.equals("1")){
            cbRemem.setChecked(true);
            String username = sharedPreferences.getString(ContantsValues.HH_USERNAME,"");
            String password = sharedPreferences.getString(ContantsValues.HH_PASSWORD,"");
            etUser.setText(username);
            etPass.setText(password);
        }
        String autologin = sharedPreferences.getString(ContantsValues.HH_AUTOLOGIN,"0");
        if(autologin.equals("1")){
            cbLogin.setChecked(true);
        }


        cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbRemem.setChecked(true);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();
                Log.d(TAG, "onClick: "+username+":  :"+password);
                LoginTask loginTask = new LoginTask();
                loginTask.execute(username,password);
            }
        });
        Log.d(TAG, "onCreate: finish!!!");
    }

    //内部类用于处理耗时的操作
    public class LoginTask extends AsyncTask<String,Void,ABRet>{
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
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                if (cbLogin.isChecked()) {
                    sharedPreferences.edit().putString(ContantsValues.HH_USERNAME,username)
                            .putString(ContantsValues.HH_PASSWORD,password)
                            .putString(ContantsValues.HH_AUTOLOGIN,"1")
                            .putString(ContantsValues.HH_REMEMBER,"1").commit();
                }
                else {
                    if(cbRemem.isChecked()){
                        sharedPreferences.edit().putString(ContantsValues.HH_REMEMBER,"1")
                                .putString(ContantsValues.HH_USERNAME,username)
                                .putString(ContantsValues.HH_PASSWORD,password).commit();
                    }
                    else{
                        sharedPreferences.edit().putString(ContantsValues.HH_REMEMBER,"0")
                                .putString(ContantsValues.HH_USERNAME,"")
                                .putString(ContantsValues.HH_PASSWORD,"").commit();
                    }
                    sharedPreferences.edit().putString(ContantsValues.HH_AUTOLOGIN, "0").commit();
                }


                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(LoginActivity.this,errMsg,Toast.LENGTH_SHORT).show();
            }
        }
    }


}
