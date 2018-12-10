package com.example.dell.use;

/**
 * Created by dell on 2018/4/23.
 */

public class Comment {

    private String img;
    private String head;
    private String userId;
    private String time;
    private String userCom;

//    public Comment(String s1,String s2,String s3,String s4){
//        head = s1;
//        userId = s2;
//        time = s3;
//        userCom = s4;
//    }

    public Comment(String userId,String userCom,String time){
        this.userId = userId;
        this.userCom = userCom;
        this.time = time;
    }

    public void setImg(String s){
        img = s;
    }
    public void setHead(String s){
        head = s;
    }
    public void setUserId(String s){
        userId = s;
    }
    public void setTime(String s){
        time = s;
    }
    public void setUserCom(String s){
        userCom = s;
    }

    public String getImg()
    {
        return img;
    }
    public String getHead(){
        return head;
    }
    public String getUserId(){
        return userId;
    }
    public String getTime(){
        return time;
    }
    public String getUserCom(){
        return userCom;
    }
}
