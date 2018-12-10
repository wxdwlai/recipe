package httpClient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by dell on 2018/4/17.
 */

public class webShouCang {

    private static String IP = "192.168.137.1:8081";
    private static String path = "";

    /*
     *显示当前的收藏状态
     * 传递当前用户信息和菜谱的信息到服务器端，然后服务器端查找数据库看是否已经有当前的记录
     * 如果有当前的记录的话那么则表示当前菜谱已经被收藏
     * 如果没有的话界面上显示未收藏的状态
     */
    public static boolean ShowResult(String userId,String foodId){
        boolean bool = false;
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        String path = "http://"+IP+"/ServletTest/ShouCangServlet";
        path = path + "?userId="+userId+"&foodId="+foodId;
        URL url = null;
        try {
            url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");//设置访问方式
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Charset","UTF-8");
            httpURLConnection.setConnectTimeout(3000);//设置超时时间
            httpURLConnection.setReadTimeout(3000);
            if(httpURLConnection.getResponseCode()==200){
                is = httpURLConnection.getInputStream();
                String str = parseInfo(is);
                if(str.contains("true"))
                    bool = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static String[] getResult(String userId){
        String[] result = null;
        HttpURLConnection httpURLConnection = null;
        path = "http://"+IP+"/ServletTest/ShowCollectResult";
        path = path+"?userId="+userId;
        System.out.println("PATH"+path);
        URL url = null;
        try {
            url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            if(httpURLConnection.getResponseCode()==200){
                InputStream is = httpURLConnection.getInputStream();
                String str = parseInfo(is);
                System.out.print("SSSSSSS:"+str);//////????????????
//                str.replace("\\*\\n","");
                result = str.split("\\*");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(String s:result)
//            System.out.println(s);
        return result;
    }

    private static String parseInfo(InputStream is) throws IOException {
        byte[] data = read(is);
        //转化为字符串
        return  new String(data,"UTF-8");
    }

    private static byte[] read(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        is.close();
        return outputStream.toByteArray();
    }

    /*
     *服务器端名字没取好，hhh
     * 改变当前的收藏状态
     * 发送当前用户信息、菜谱信息和将来的收藏状态信息
     * 如果之前是已收藏状态，那么在服务器端的数据库进行删除相关数据操作
     * 如果之前是未收藏状态，那么将来的状态是收藏，那么则在数据库插入相关数据
     */
    public static boolean ChangeState(String userId, String foodId, String string) throws JSONException {
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        String path = "http://"+IP+"/ServletTest/ShouCangStatusServlet";
        String s = userId+"*"+foodId+"*"+string;
        URL url = null;
        boolean bool = false;
        try {
            url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setRequestProperty("Content-Type","application/html; charset=UTF-8");

            byte[] bytes = s.getBytes();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(bytes);
            os.close();
            if(httpURLConnection.getResponseCode()==200){
                is = httpURLConnection.getInputStream();
                String str = parseInfo(is);
                if(str.contains("success"))
                    bool = true;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool;
    }
}
