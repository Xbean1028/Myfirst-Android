package com.example.aboxdome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

import java.util.Map;

public class LoginHignActivity extends AppCompatActivity {
    Button btnlogin;
    String username;
    String password;
    TextView tvusname;
    TextView tvpsword;
    CheckBox checkBoxreme;
    CheckBox checkBoxre;
    SharedPreferences sharedPreferences;
    Map<String, Object> dicDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences =getSharedPreferences(ContantsValues.HH_FILENAME,MODE_PRIVATE);

        tvpsword =findViewById(R.id.ed_psword);
        tvusname =findViewById(R.id.ed_usname);
        checkBoxreme=findViewById(R.id.checkBoxReme);
        checkBoxre=findViewById(R.id.checkBoxre);
        username=sharedPreferences.getString(ContantsValues.HH_USERNAME,"no");
        password=sharedPreferences.getString(ContantsValues.HH_PASSWORD,"no");
        if(!username.equals("no")){
            tvpsword.setText(password);
            tvusname.setText(username);
        }
        btnlogin =(Button)findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usname = tvusname.getText().toString();
                String psword = tvpsword.getText().toString();
                LoginTask loginTask = new LoginTask();
                loginTask.execute(psword,usname);
            }
        });
    }

    public class LoginTask extends AsyncTask<String,Void,ABRet>{
        String password;
        String username;
        @Override
        protected ABRet doInBackground(String... a) {
            password=a[0];
            username=a[1];
            ABRet abRet = ABSDK.getInstance().loginWithUsername(username,password);
            return abRet;
        }

        @Override
        protected void onPostExecute(ABRet abRet) {

            ContantsValues.init();
            super.onPostExecute(abRet);
            String co=abRet.getCode();


            if(co.equals("00000")) {
                Toast t=Toast.makeText(LoginHignActivity.this,"登录成功",Toast.LENGTH_LONG);
                t.show();
                if(checkBoxreme.isChecked()){
                    sharedPreferences.edit()
                            .putString(ContantsValues.HH_USERNAME,username)
                            .putString(ContantsValues.HH_PASSWORD,password)
                            .commit();
                }
                if(checkBoxre.isChecked()){
                    sharedPreferences.edit()
                            .putString(ContantsValues.HH_USERNAME,username)
                            .putString(ContantsValues.HH_PASSWORD,password)
                            .putString(ContantsValues.HH_AUTOLOGIN,"o")
                            .commit();
                }
                Intent intent = new Intent();
                intent.setClass(LoginHignActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                String i = ContantsValues.codeValues.get(co);
                Toast t=Toast.makeText(LoginHignActivity.this,i,Toast.LENGTH_LONG);
                t.show();
            }
        }
    }
}
