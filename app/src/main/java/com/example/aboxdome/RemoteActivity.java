package com.example.aboxdome;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhc.absdk.ABRet;
import com.dhc.absdk.ABSDK;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RemoteActivity extends AppCompatActivity {

    GridView gridViewRemoteLayout;//展示控件
    RemoteAdapeter remoteAdapeter;//自定义适配器
    RemoteButtonBean remoteButtonBean;//自定义数据
    ArrayList<RemoteButtonBean> remoteButtonList;//数据源
    String buttonName;
    int buttonPos;
    int buttonFlag;
    int index;

    SQLiteDatabase db;//数据库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(RemoteActivity.this);
        db = dbOpenHelper.getWritableDatabase();
        SharedPreferences sp = getSharedPreferences(ContantsValues.HH_FILENAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int dbFlag = sp.getInt("dbFlag",0);
        if (dbFlag == 0) {
            init();
            readDate();
            editor.putInt("dbFlag",1);
        }
        else{
            readDate();
        }


        init();

        gridViewRemoteLayout = (GridView)findViewById(R.id.gridViewReomte);
        remoteAdapeter = new RemoteAdapeter();
        gridViewRemoteLayout.setAdapter(remoteAdapeter);

        //点击按钮
        gridViewRemoteLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                buttonPos = position;       //被点击的item的位置
                buttonName = remoteButtonList.get(position).getName();
                buttonFlag = remoteButtonList.get(position).getFlag();
                if(buttonFlag == 0){        //执行学习功能
                    //Toast.makeText(RemoteActivity.this,"短摁",Toast.LENGTH_SHORT).show();
                    StudyHomeTask studyHomeTask = new StudyHomeTask();
                    studyHomeTask.execute();
                }
                else if(buttonFlag == 1){   //发送对应红外信号
                    HomeTask homeTask = new HomeTask();
                    homeTask.execute();
                }
                else{
                    Toast.makeText(RemoteActivity.this,"按钮Flag出错",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 长按按钮
        gridViewRemoteLayout.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                buttonPos = position;       //被点击的item的位置
                buttonName = remoteButtonList.get(position).getName();
                buttonFlag = remoteButtonList.get(position).getFlag();
                if(buttonFlag == 1){        //打开按钮编辑对话框
                    //Toast.makeText(RemoteActivity.this,"长按了已经学习的按钮",Toast.LENGTH_SHORT).show();
                    /*
                    StudyHomeTask studyHomeTask = new StudyHomeTask();
                    studyHomeTask.execute();*/
                    index = 0;
                    AlertDialog alertDialog4 = new AlertDialog.Builder(RemoteActivity.this)
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
                                    Toast.makeText(RemoteActivity.this, "这是确定按钮" + "点的是：" + ContantsValues.options[index], Toast.LENGTH_SHORT).show();
                                    if (index == 0) {       //重新学习
                                        StudyHomeTask studyHomeTask = new StudyHomeTask();
                                        studyHomeTask.execute();
                                    }
                                    else if (index == 1){   //修改名称
                                        changeName();
                                    }
                                    else if (index == 2) {  //删除按钮
                                        remoteButtonList.get(buttonPos).setName("未命名");
                                        remoteButtonList.get(buttonPos).setFlag(0);
                                        ContentValues values = new ContentValues();
                                        values.put("name","未命名");
                                        values.put("flag",0);
                                        db.update("buttons",values,"pos=?",new String[]{String.valueOf(buttonPos)});
                                        remoteAdapeter.notifyDataSetChanged();  //更新界面
                                    }
                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(RemoteActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog4.show();
                }
                else{
                    //Toast.makeText(RemoteActivity.this,"长按了未学习的按钮",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
    //初始化数据（向数据库中添加数据）
    public void init(){
        ContentValues values = new ContentValues();
        for(int i=0 ;i<ContantsValues.HH_NUMBER;i++){
            values.put("pos",i);
            values.put("name","未命名");
            values.put("flag",0);
            db.insert("buttons",null,values);
            values.clear();
        }
    }

    //从数据库中读取到ArrayList中
    public void readDate(){
        Cursor cursor = db.query("buttons",null,null,null,null,null,null);
        remoteButtonList = new ArrayList<RemoteButtonBean>();
        if(cursor.moveToFirst()){
            do{
                int pos = cursor.getInt(cursor.getColumnIndex("pos"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int flag = cursor.getInt(cursor.getColumnIndex("flag"));
                RemoteButtonBean remoteButtonBean = new RemoteButtonBean(pos,name,flag);
                remoteButtonList.add(remoteButtonBean);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }



    //自定义适配器
    public class RemoteAdapeter extends BaseAdapter{


        @Override
        public int getCount(){
            return ContantsValues.HH_NUMBER;
        }


        @Override
        public Object getItem(int position){
            return position;
        }


        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view;
            TextView textViewName = null;
            ImageView imageViewButton = null;
            if(convertView==null){
                LayoutInflater layoutInflater = getLayoutInflater();
                view = layoutInflater.inflate(R.layout.layout_button,null);
                convertView = view;
                //自定义控件
                textViewName = (TextView)view.findViewById(R.id.textView_name);
                imageViewButton = (ImageView)view.findViewById(R.id.imageView_button);
            }
            else{
                textViewName = (TextView)convertView.findViewById(R.id.textView_name);
                imageViewButton = (ImageView)convertView.findViewById(R.id.imageView_button);
            }
            textViewName.setText(remoteButtonList.get(position).getName());
            if(remoteButtonList.get(position).getFlag()==1){
                imageViewButton.setImageResource(R.drawable.button_on);
            }
            else{
                imageViewButton.setImageResource(R.drawable.button_off);
            }

            return convertView;
        }
    }

    //修改name和flag
    public void changeName(){
        final EditText et = new EditText(RemoteActivity.this);
        new AlertDialog.Builder(RemoteActivity.this).setTitle("请给按钮命名")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后，修改命名
                        String name = et.getText().toString();
                        remoteButtonList.get(buttonPos).setName(name);
                        remoteAdapeter.notifyDataSetChanged();

                        //进行数据库的Update
                        ContentValues values = new ContentValues();
                        values.put("name",name);
                        values.put("flag",1);
                        db.update("buttons",values,"pos = ?",new String[]{String.valueOf(buttonPos)});
                    }
                }).setNegativeButton("取消",null).show();
        remoteButtonList.get(buttonPos).setFlag(1);



        //更新界面
        remoteAdapeter.notifyDataSetChanged();
    }

    //学习线程
    public class StudyHomeTask extends AsyncTask<Void,Void, ABRet> {
        @Override
        public ABRet doInBackground(Void... params){
            ABRet abRet = null;
            String pos = String.valueOf(remoteButtonList.get(buttonPos).getPos());
            abRet = ABSDK.getInstance().studyIrByIrDevName(ContantsValues.DE_RED,pos);
            return abRet;
        }
        @Override
        protected void onPostExecute(ABRet abRet) {
            String retCode = abRet.getCode();
            if(retCode.equals("00000")){
                String pos = String.valueOf(remoteButtonList.get(buttonPos).getPos());
                Toast.makeText(RemoteActivity.this,"学习成功pos:"+pos,Toast.LENGTH_SHORT).show();

                changeName();

            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(RemoteActivity.this,"学习："+errMsg,Toast.LENGTH_SHORT).show();
            }
        }
    }

    //发送线程
    public class HomeTask extends AsyncTask<Void,Void, ABRet>{
        @Override
        public ABRet doInBackground(Void... params){
            ABRet abRet = null;
            String pos = String.valueOf(remoteButtonList.get(buttonPos).getPos());
            abRet = ABSDK.getInstance().sendIr(ContantsValues.DE_RED,pos);
            return abRet;
        }
        @Override
        protected void onPostExecute(ABRet abRet) {
            String retCode = abRet.getCode();
            if(retCode.equals("00000")){
                String pos = String.valueOf(remoteButtonList.get(buttonPos).getPos());
                Toast.makeText(RemoteActivity.this,"发送成功pos:"+pos,Toast.LENGTH_SHORT).show();

            }
            else{
                ContantsValues.init();
                String errMsg = ContantsValues.codeValues.get(retCode);
                Toast.makeText(RemoteActivity.this,"发送"+errMsg,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
