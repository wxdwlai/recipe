package httpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dell on 2018/3/27.
 */

public class webPic {
    private static String IP = "192.168.137.1:8081";
    private static String path = "";

    //利用下面的图片服务器进行图片的显示
    public static Bitmap ShowPic(String path){
        Bitmap bm = null;
        path = "http://192.168.137.1:8080/"+path;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(3000);
            connection.connect();
            if(connection.getResponseCode()==200){
                bm = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
    public static Drawable getPicture(String route){
        try {
            Drawable drawable = Drawable.createFromStream(new URL(route).openStream(),"1.jpg");
            return drawable;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
