  # []()我的Android开发第一个例子

在此声明此项目为哈工大在大连华信实训项目，感谢华信的指导

同组人：Bean、张宗烁、汪珏

使用Android studio开发  
 主要功能：  
 能实现登录功能，检查账号密码是否正确  
 登录时下方显示账号密码并跳转  
 有两个页面可以跳转  
 点击更换可以更换图片  
 显示欢迎**（登录的账户名）


## []()目录结构：

 /manifests/AndroidManifest.xml 配置，需要设置程序入口  
 /Java/ 各个activity（页面）的Java文件  
 /res/drawable 图片  
 -layout xml配置文件  
 -value 存放常量

--------

## []()主要实现部分：（源代码最后附上）

 页面跳转


```
Intent intent = new Intent();
intent.setClass(LoginActivity.this, MainActivity.class);
startActivity(intent);
finish();

```
 显示信息


```
Toast t = Toast.makeText(LoginActivity.this, usname + "," + psword, Toast.LENGTH_SHORT);
t.show();

```
 传递消息（发送端）


```
Intent intent = new Intent();
intent.putExtra("name", usname);
//name是消息名称，usname是消息内容
intent.putExtra("pass", psword);
startActivity(intent);

```
 传递消息（接收端）


```
String name=getIntent().getStringExtra("name");
String pass=getIntent().getStringExtra("pass");

```
 日志


```
Log.d("debug", "onCreate()...");
Log.e("error", "onCreate()...");
Log.w("warn", "onCreate()...");
Log.v("verbose", "onCreate()...");

```
 获取控件


```
btnLogin = findViewById(R.id.btn_login);
tvusname = findViewById(R.id.edt_usename);
tvpsword = findViewById(R.id.edt_psword);

```
 点击控件


```
btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usname = tvusname.getText().toString();
                String psword = tvpsword.getText().toString();
                String s[] = {"wdc,123",,"wec,234", "wsc,345", "wxc,456", "dhe,123456"};
                int len = s.length;
                int flag = 0;
                for (int i = 0; i < len; i++) {
                    String name = s[i].split(",")[0];
                    String pass = s[i].split(",")[1];
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                       startActivity(intent);
                        finish();
                        Toast t = Toast.makeText(LoginActivity.this, usname + "," + psword, Toast.LENGTH_SHORT);
                        t.show();
                        intent.putExtra("name", usname);
                        intent.putExtra("pass", psword);
                        startActivity(intent);
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    Toast t = Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

```
 更换图片


```
btnchange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            iv_image.setImageResource(R.drawable.timg);
            }
        });

```

--------

## []()示例：

 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190717221130418.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hiZWFuMTAyOA==,size_16,color_FFFFFF,t_70)  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190717221311584.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hiZWFuMTAyOA==,size_16,color_FFFFFF,t_70)  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190717221349286.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hiZWFuMTAyOA==,size_16,color_FFFFFF,t_70)

--------

## []()源代码：

 LoginActivity.java


```
package com.example.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //创建button，拿到事件
    Button btnLogin;
    TextView tvusname;
    TextView tvpsword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("android", "This is onCreate");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        Log.d("debug", "onCreate()...");
        Log.e("error", "onCreate()...");
        Log.w("warn", "onCreate()...");
        Log.v("verbose", "onCreate()...");

        btnLogin = findViewById(R.id.btn_login);
        tvusname = findViewById(R.id.edt_usename);
        tvpsword = findViewById(R.id.edt_psword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usname = tvusname.getText().toString();
                String psword = tvpsword.getText().toString();
                String s[] = {"wdc,123","wec,234", "wsc,345", "wxc,456", "dhe,123456"};
                int len = s.length;
                int flag = 0;
                //dhe+123456
                for (int i = 0; i < len; i++) {
                    String name = s[i].split(",")[0];
                    String pass = s[i].split(",")[1];
                    if (name.equals(usname) && pass.equals(psword)) {
                        //tiaozhuan
                        Intent intent = new Intent();
                        //liacanshu
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        //start
                        startActivity(intent);
                        //页面finish
                        finish();
                        Toast t = Toast.makeText(LoginActivity.this, usname + "," + psword, Toast.LENGTH_SHORT);
                        t.show();
                        //传输
                        //Intent intent2=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("name", usname);
                        intent.putExtra("pass", psword);
                        startActivity(intent);
                        flag = 1;
                    }
                }

                if (flag == 0) {
                    Toast t = Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT);
                    t.show();
                }

                //Toast t = Toast.makeText(LoginActivity.this, usname+","+psword,Toast.LENGTH_SHORT);
                //t.show();

            }
        });
    }

    protected void onStart() {
        super.onStart();
        Log.i("android", "This is onStart");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i("android", "This is onRestart");
    }

    protected void onResume() {
        super.onResume();
        Log.i("android", "This is onResume");
    }

    protected void onStop() {
        super.onStop();
        Log.i("android", "This is onRstop");
    }
}

```
 MainActivity.java


```
package com.example.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnback;
    Button btnchange;
    TextView tvname3;
    ImageView iv_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name=getIntent().getStringExtra("name");
        String pass=getIntent().getStringExtra("pass");
        tvname3 = findViewById(R.id.tvname3);
        tvname3.setText("欢迎 "+name);
        btnback = findViewById(R.id.btn_back);
        btnchange = findViewById(R.id.btn_change);
        iv_image = findViewById(R.id.iv_image);
        btnchange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //iv_image.setImageResource(R.drawable.ic_launcher_round);
                iv_image.setImageResource(R.drawable.timg);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                    //tiaozhuan
                    Intent intent = new Intent();
                    //liacanshu
                    intent.setClass(MainActivity.this,LoginActivity.class);
                    //start
                    startActivity(intent);
                    //页面finish
                    finish();
            }
        });
    }
}


```
 如有错误，欢迎批评指正。

   
