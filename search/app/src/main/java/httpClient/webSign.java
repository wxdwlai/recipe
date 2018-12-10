package httpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2018/3/24.
 */

public class webSign {

    private static String IP = "192.168.137.1:8081";

    /*
     *通过GET方式获取HTTP服务器数据
     */

    public static String excuteHttpGet(String account, String password) throws IOException {

        HttpURLConnection httpURLConnection = null;
        InputStream is = null;

        String path = "http://"+IP+"/ServletTest/RegisterServlet";
        path = path + "?account=" + account + "&password=" + password;
        URL url = new URL(path);

        httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");//设置访问方式
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Charset","UTF-8");
        httpURLConnection.setConnectTimeout(3000);//设置超时时间
        httpURLConnection.setReadTimeout(3000);
        if(httpURLConnection.getResponseCode()==200){
            is = httpURLConnection.getInputStream();
            return parseInfo(is);
        }
        return "服务器连接超时...";
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
}
