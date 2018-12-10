package com.example.dell.search;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class PersonalSettingActivity extends AppCompatActivity {

    private Button touxiang;
    private ImageView my_picture;
    private EditText per_sign;
    private EditText name;
    private EditText sex;
    private EditText birth;
    private EditText job;
    private EditText home;
    private Button fanhui1;
    private CharSequence item1[] = {"男", "女"};
    private CharSequence item2[] = {"全职主妇","上班族","学生","个体商人","其他"};

    private static final String TAG = "PersonalSettingActivity";
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        inti();
        //头像导入

        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog(v);
            }
        });
        //个性签名编辑
        per_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //昵称
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //性别
        sex.setFocusable(false);
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonalSettingActivity.this);

                builder.setTitle("性别选择");
                builder.setSingleChoiceItems(item1, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String select_item = item1[which].toString();
                        sex.setText(select_item);
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
//生日
        birth.setFocusable(false);
        birth.setOnClickListener(new View.OnClickListener() {
            Calendar cal = Calendar.getInstance();
            int myear = cal.get(Calendar.YEAR);
            int mmonth = cal.get(Calendar.MONTH);
            int mday = cal.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int myear = year, mmonth = month, mmday = dayOfMonth;
                        StringBuffer days;
                        days = new StringBuffer().append(myear).append("年").append(mmonth).append("月").append(dayOfMonth).append("日");
                        birth.setText(days);
                    }
                };
                new DatePickerDialog(PersonalSettingActivity.this,onDateSetListener,myear,mmonth,mday).show();
            }
        });

        //职业
        job.setFocusable(false);
        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(PersonalSettingActivity.this);

                builder.setTitle("职业选择");
                builder.setSingleChoiceItems(item2, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String select_item = item2[which].toString();
                        job.setText(select_item);
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
//返回设置界面
        fanhui1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalSettingActivity.this, PersonalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalSettingActivity.this);
        builder.setTitle("设置头像");
        String items[] = {"相册","拍摄"};
        builder.setNegativeButton("取消",null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case CHOOSE_PICTURE:
                        Intent openAlbumIntent =new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image*/");
                        startActivityForResult(openAlbumIntent,1);
                        break;
                    case TAKE_PICTURE:
                        takePicture();
                        break;
                }
            }

            private void takePicture() {
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};//使得sd卡获得写的权限
                if(Build.VERSION.SDK_INT >=14){
                    //检查是否拥有权限
                    int check = ContextCompat.checkSelfPermission(PersonalSettingActivity.this,permissions[0]);
                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                    if(check != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(PersonalSettingActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                    }
                }
                Intent opencameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//启动系统相机
                File file = new File(Environment.getExternalStorageDirectory(),"image.jpg");
                if(Build.VERSION.SDK_INT>=14){
                    opencameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//传递权限
                    tempuri = FileProvider.getUriForFile(PersonalSettingActivity.this, "com.example.dell.search.fileProvider", file);
                }
                else{
                    tempuri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                }
                opencameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,tempuri);//更改系统默认存储路径
                startActivityForResult(opencameraIntent,TAKE_PICTURE);
            }

            protected void onActivityResult(int requestCode,int resultCode,Intent data){
                PersonalSettingActivity.super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK) { // 如果返回码是可以用的
                    switch (requestCode) {
                        case TAKE_PICTURE:
                            startPhotoZoom(tempuri); // 开始对图片进行裁剪处理
                            break;
                        case CHOOSE_PICTURE:
                            startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                            break;
                        case CROP_SMALL_PICTURE:
                            if (data != null) {
                                setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                            }
                            break;
                    }
                }
            }
            /**
             * 裁剪图片方法实现
             *
             * @param uri
             */
            protected void startPhotoZoom(Uri uri) {
                if (uri == null) {
                    Log.i("tag", "The uri is not exist.");
                }
                tempuri = uri;
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                // 设置裁剪
                intent.putExtra("crop", "true");
                // aspectX aspectY 是宽高的比例
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                // outputX outputY 是裁剪图片宽高
                intent.putExtra("outputX", 150);
                intent.putExtra("outputY", 150);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_SMALL_PICTURE);
            }

            /**
             * 保存裁剪之后的图片数据
             *
             * @param
             */
            protected void setImageToView(Intent data) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Log.d(TAG,"setImageToView:"+photo);
                    photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                    my_picture.setImageBitmap(photo);
                    uploadPic(photo);
                }
            }

            private void uploadPic(Bitmap bitmap) {
                // 上传至服务器
                // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
                // 注意这里得到的图片已经是圆形图片了
                // bitmap是没有做个圆形处理的，但已经被裁剪了
                String imagePath = ImageUtils.savePhoto(bitmap, Environment
                        .getExternalStorageDirectory().getAbsolutePath(), String
                        .valueOf(System.currentTimeMillis()));
                Log.e("imagePath", imagePath+"");
                if(imagePath != null){
                    // 拿着imagePath上传了
                    // ...
                    Log.d(TAG,"imagePath:"+imagePath);
                }
            }
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                PersonalSettingActivity.super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // 没有获取 到权限，从新请求，或者关闭app
                    Toast.makeText(PersonalSettingActivity.this, "需要存储权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.create().show();
    }

    public void inti () {
        touxiang = (Button)findViewById(R.id.touxiang);
        my_picture=(ImageView)findViewById(R.id.my_picture);
        per_sign = (EditText) findViewById(R.id.per_sign);
        name = (EditText) findViewById(R.id.name);
        sex = (EditText) findViewById(R.id.sex);
        birth = (EditText) findViewById(R.id.birth);
        job = (EditText) findViewById(R.id.job);
        home = (EditText) findViewById(R.id.home);
        fanhui1 = (Button) findViewById(R.id.fanhui1);
    }
}
