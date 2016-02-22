package com.yichang.kaku.member.truckorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.Base64Coder;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.UrlCtnt;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TruckOrderReturnActivity extends BaseActivity implements OnClickListener {
    private TextView left,right,title;
    private EditText et_fanxiu;
    private Button btn_return_commit;
    private PopupWindow window;
    private ImageView iv_return_add;
    private TextView tv_takephoto;
    private TextView tv_myphoto;
    private TextView tv_exitphoto;
    private String imagePath;

    private String mId_bill;

    // 创建一个以当前时间为名称的文件
    private File dataFile;
    private File photoFile;
    private boolean isTAKEPHOTO = false;
    private static final int ZERO = 0;// 拍照
    private static final int PHOTO_REQUEST_TAKEPHOTO = 4;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 5;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 6;// 结果
    private Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanxiu);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        left=(TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title=(TextView) findViewById(R.id.tv_mid);
        title.setText("申请售后服务");
        et_fanxiu = (EditText) findViewById(R.id.et_fanxiu);
        btn_return_commit = (Button) findViewById(R.id.btn_fanxiu_commit);
        btn_return_commit.setOnClickListener(this);
        iv_return_add = (ImageView) findViewById(R.id.iv_fanxiu_add);
        iv_return_add.setOnClickListener(this);

        window = new PopupWindow();

        mId_bill=getIntent().getStringExtra("id_bill");
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()){
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.btn_fanxiu_commit == id){
            if ("".equals(et_fanxiu.getText().toString().trim())){
                return;
            }
            Upload();
        } else if (R.id.tv_takephoto == id) {
            fecthFromCamear();

        } else if (R.id.tv_myphoto == id) {
            fecthFromGallery();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        } else if (R.id.iv_fanxiu_add == id){
            ShowWindow(v);
        }
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (photoFile.exists())
                    startPhotoZoom(Uri.fromFile(photoFile), 354);
                window.dismiss();
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 354);
                window.dismiss();
                break;

            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String hint = (String) msg.obj;
            if (!TextUtils.isEmpty(hint)) {
                LogUtil.showShortToast(getApplicationContext(),
                        msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void fecthFromCamear() {
        photoFile = new File(Environment.getExternalStorageDirectory(),
                getPhotoFileName());
        LogUtil.E("photoFile: " + photoFile);
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        isTAKEPHOTO = true;
    }

    /**
     * 通过相册获得图片
     *
     * @param
     */
    private void fecthFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 保存图片到本地
     */
    public void saveFile(Bitmap bm) throws IOException {
        if (dataFile == null)
            dataFile = new File(this.getFilesDir(), "curImag"
                    + Utils.getIdDriver() + ".jpg");
        if (dataFile.exists())
            dataFile.delete();// 如果有同名文件存在先删除
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(dataFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        imagePath = dataFile.toString();
        LogUtil.E("imagePath : " + imagePath);
    }

    /**
     * 打开手机相机拍照
     */
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 将进行剪裁后的图片显示到UI界面上
     */

    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            photo = bundle.getParcelable("data");
            try {
                saveFile(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            KaKuApplication.picture_return = photo;
            iv_return_add.setImageBitmap(photo);
            if (isTAKEPHOTO) {
                if (photoFile.exists()) {
                    boolean isdelete = photoFile.delete();
                    LogUtil.E("isdelete:: " + isdelete);
                }
                isTAKEPHOTO = false;
            }
        }
    }

    public void ShowWindow(View v){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        window.setWidth(outMetrics.widthPixels);
        window.setHeight(outMetrics.heightPixels / 4);
        View view = getLayoutInflater().inflate(R.layout.popupwindow, null);
        tv_takephoto = (TextView) view.findViewById(R.id.tv_takephoto);
        tv_takephoto.setOnClickListener(this);
        tv_myphoto = (TextView) view.findViewById(R.id.tv_myphoto);
        tv_myphoto.setOnClickListener(this);
        tv_exitphoto = (TextView) view.findViewById(R.id.tv_exitphoto);
        tv_exitphoto.setOnClickListener(this);
        window.setContentView(view);
        window.setBackgroundDrawable(null);
        window.showAtLocation(v, Gravity.BOTTOM, 0, 10);
    }

    public void Upload() {
        showProgressDialog();
        new Thread() {
            private String file;

            public void run() {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if ("".equals(KaKuApplication.picture_return)||KaKuApplication.picture_return == null) {
                    file = "";
                } else {
                    KaKuApplication.picture_return.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] b = stream.toByteArray();
                    // 将图片流以字符串形式存储下来
                    file = new String(Base64Coder.encodeLines(b));
                }
                HttpClient client = new DefaultHttpClient();
                // 设置上传参数
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("code", "30014"));
                formparams.add(new BasicNameValuePair("id_bill",mId_bill));
                formparams.add(new BasicNameValuePair("describe_repair", String.valueOf(et_fanxiu.getText().toString().trim())));
                formparams.add(new BasicNameValuePair("image_repair", file));
                LogUtil.E("----"+formparams.toString());
                HttpPost post = new HttpPost(UrlCtnt.BASEIP);
                UrlEncodedFormEntity entity;
                try {
                    entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                    post.addHeader("Accept","text/javascript, text/html, application/xml, text/xml");
                    post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
                    post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
                    post.addHeader("Connection", "Keep-Alive");
                    post.addHeader("Cache-Control", "no-cache");
                    post.addHeader("Content-Type", "application/x-www-form-urlencoded");
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);
                    LogUtil.E("statuscode:"+response.getStatusLine().getStatusCode());
                    String json = EntityUtils.toString(response.getEntity());
                    JSONObject object = new JSONObject(json);
                    String res = object.getString("res");
                    String msg = object.getString("msg");
                    LogUtil.E("json:"+json);

                    Message message = new Message();
                    if (200 == response.getStatusLine().getStatusCode()) {
                        LogUtil.E("上传完成~~~");
                        message.obj = msg;
                        h.sendMessage(message);
                        client.getConnectionManager().shutdown();
                        stopProgressDialog();
                    } else {
                        LogUtil.E("上传失败~~~");
                        message.obj = msg;
                        h.sendMessage(message);
                        client.getConnectionManager().shutdown();
                        stopProgressDialog();
                    }
                    GoToOrderList();
                } catch (Exception e) {
                    LogUtil.E("上传失败"+e.toString());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void GoToOrderList(){
        /*KaKuApplication.state_order = "";
        KaKuApplication.color_order = "";*/
        KaKuApplication.truck_order_state = "";
        startActivity(new Intent(context, TruckOrderListActivity.class));
        finish();
    }

}
