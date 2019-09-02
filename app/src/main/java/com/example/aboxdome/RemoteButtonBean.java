package com.example.aboxdome;

public class RemoteButtonBean {



    private int pos;         //位置，红外标记
    private String name;    //按钮名
    private int flag;       //标记是否学习过

    public RemoteButtonBean(){

    }
    public RemoteButtonBean(int pos, String name, int flag){
        this.pos = pos;
        this.name = name;
        this.flag = flag;
    }
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }

}
