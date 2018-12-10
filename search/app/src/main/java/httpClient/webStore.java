package httpClient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewDebug;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 2018/3/25.
 */

public class webStore {

    private static String IP = "192.168.137.1:8081";
    private static String info;
    private static List<String> list = null;

    public static void excuteHttpGet(){
        //创建一个URL对象
        URL url = null;
        try {
            url = new URL("https://www.taobao.com/");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");//设置访问方式
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Charset","UTF-8");
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);
            if(httpURLConnection.getResponseCode() != 200) throw new RuntimeException("请求url失败");

            InputStream is = httpURLConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] excuteHttpGet(String keyWord){
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        String path = "http://"+IP+"/ServletTest/StoreServlet";
        path = path+"?keyWord="+keyWord;
        String [] rs = new String[10];

        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);

            if(httpURLConnection.getResponseCode()==200){
                is = httpURLConnection.getInputStream();
                info = parseInfo(is);
                rs = info.split("[0-9]{1,}");
//                System.out.println(rs.length+"******************\n");
//                return info;
                for(int i=0;i<rs.length;i++){
                    System.out.println(i+":"+rs[i]);
                }
                return rs;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public static String excuteGetSteps(String keyWord){
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        String path = "http://"+IP+"/ServletTest/ResultServlet";
        path = path+"?keyWord="+keyWord;
        list = new ArrayList<String>();

        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);

            if(httpURLConnection.getResponseCode()==200){
                is = httpURLConnection.getInputStream();
                info = parseInfo(is);
                return info;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
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


    public static String getPath(String string){
        String path = null;
        String pattern = "images/\\d.jpg";
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(string);
        if(matcher.find()){
            path = matcher.group();
        }
        return path;
    }
    public static Bitmap getPic(){
        HttpURLConnection connection = null;
        InputStream is = null;
        String path = "http://"+IP+"/"+getPath(info);
//        String path = "http://"+IP+"/images/1.jpg";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            is = conn.getInputStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
