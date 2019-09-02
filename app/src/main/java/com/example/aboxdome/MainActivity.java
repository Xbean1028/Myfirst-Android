package com.example.aboxdome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

public class MainActivity extends AppCompatActivity {

    Button btnLoginout;
    Button btnRed;
    Button btnRemote;
    TextView tvWelcome;
    Button btnOption;
    Button btnSwitch;
    Button btnDoor;
    Button btnTH;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //tvWelcome = (TextView) findViewById(R.id.textView_welcome);
        btnLoginout = (Button)findViewById(R.id.button_logout);
        btnLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        /*
        btnRed = (Button)findViewById(R.id.button_red);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfraRedActivity.class);
                startActivity(intent);
            }
        });*/
        btnRemote = (Button)findViewById(R.id.button_remote);
        btnRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RemoteActivity.class);
                startActivity(intent);
            }
        });
/*
        Button btnRename = (Button)findViewById(R.id.button_rename);
        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("请给按钮命名")
                        .setIcon(android.R.drawable.sym_def_app_icon)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                                String welcome = et.getText().toString();
                                tvWelcome.setText(welcome);

                            }
                        }).setNegativeButton("取消",null).show();
            }
        });

        btnOption = (Button)findViewById(R.id.button_option);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 0;
                AlertDialog alertDialog4 = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请选择你要执行的操作")
                        .setIcon(R.mipmap.ic_launcher)
                        .setSingleChoiceItems(ContantsValues.options, 0, new DialogInterface.OnClickListener() {//添加单选框
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                index = i;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "这是确定按钮" + "点的是：" + ContantsValues.options[index], Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog4.show();
            }
        });
*/
        btnSwitch = (Button)findViewById(R.id.button_switch);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SwitchActivity.class);
                startActivity(intent);
            }
        });

        btnDoor = (Button)findViewById(R.id.button_door);
        btnDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DoorActivity.class);
                startActivity(intent);
            }
        });

        btnTH = (Button)findViewById(R.id.button_th);
        btnTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,THActivity.class);
                startActivity(intent);
            }
        });
    }



}
