package com.yichang.kaku.member.driver;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.QRCodeActivity;
import com.yichang.kaku.obj.DriveInfoObj;
import com.yichang.kaku.request.MemberDriverInfoReq;
import com.yichang.kaku.response.MemberDriverInfoResp;
import com.yichang.kaku.tools.Base64Coder;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.webService.UrlCtnt;

import org.apache.http.Header;
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

public class DriverInfoActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {
    //titleBar,返回，购物车，标题,标题栏布局
    private TextView left, right, title;
    //    头像
    private ImageView iv_info_icon;
    //    姓名，电话，认证
    private TextView tv_info_name, tv_info_phone, tv_info_certification;
    //    二维码
    private ImageView iv_info_erweima;
    //点击箭头进入编辑页
    private TextView tv_info_referralcode;


    private RelativeLayout rela_info_icon,rela_info_name,rela_info_certification,rela_info_qrcode;

    private static final int NAMECHANGED = 101;// 结果

    private static DriveInfoObj driverInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_driverinfo);
        init();

    }

    private void init() {
        initTitleBar();
        iv_info_icon = (ImageView) findViewById(R.id.iv_info_icon);
        tv_info_name = (TextView) findViewById(R.id.tv_info_name);
        tv_info_phone = (TextView) findViewById(R.id.tv_info_phone);
        tv_info_certification = (TextView) findViewById(R.id.tv_info_certification);
        iv_info_erweima = (ImageView) findViewById(R.id.iv_info_erweima);

        rela_info_icon = (RelativeLayout) findViewById(R.id.rela_info_icon);
        rela_info_icon.setOnClickListener(this);
        rela_info_name = (RelativeLayout) findViewById(R.id.rela_info_name);
        rela_info_name.setOnClickListener(this);
        rela_info_certification = (RelativeLayout) findViewById(R.id.rela_info_certification);
        rela_info_certification.setOnClickListener(this);
        rela_info_qrcode = (RelativeLayout) findViewById(R.id.rela_info_qrcode);
        rela_info_qrcode.setOnClickListener(this);

        tv_info_referralcode= (TextView) findViewById(R.id.tv_info_referralcode);

        window = new PopupWindow();
        getDriverInfo();
    }

    private void getDriverInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        MemberDriverInfoReq req = new MemberDriverInfoReq();
        req.code = "10012";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getDriverInfo(req, new BaseCallback<MemberDriverInfoResp>(MemberDriverInfoResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, MemberDriverInfoResp t) {
                if (t != null) {
                    LogUtil.E("getDriverInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.driver);
                        LogUtil.E("t.driver : " + t.driver);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }

    private void setData(DriveInfoObj driver) {

        if (driver != null) {
            this.driverInfo = driver;
            switch (driver.getFlag_approve()) {
                case "Y":
                    tv_info_certification.setText("已认证");
                    break;
                case "N":
                    tv_info_certification.setText("未认证");
                    break;
                case "D":
                    tv_info_certification.setText("认证中");
                    break;
                case "F":
                    tv_info_certification.setText("未通过");
                    break;
            }


            if (!TextUtils.isEmpty(driver.getHead())) {
                BitmapUtil.getInstance(context).download(iv_info_icon, KaKuApplication.qian_zhui + driver.getHead());
            }
            tv_info_name.setText(driver.getName_driver());
            tv_info_phone.setText(driver.getPhone_driver());
            tv_info_referralcode.setText(driver.getCode_recommended());
        }

    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);

        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("个人资料");


    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.rela_info_icon == id) {
//            编辑头像
            showPopWindow(v);
        } else if (R.id.rela_info_name == id) {
//            编辑昵称

            Intent intent = new Intent(context, ModifyNameActivity.class);
            intent.putExtra("name", tv_info_name.getText().toString().trim());
            startActivityForResult(intent, 100);
        } else if (R.id.rela_info_certification == id) {
//            编辑认证信息
            gotoCertification();

        } else if (R.id.rela_info_qrcode == id) {
//            进入二维码界面
            gotoQRCode();
        } else if (R.id.tv_takephoto == id) {
            fecthFromCamear();

        } else if (R.id.tv_myphoto == id) {
            fecthFromGallery();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        }
    }

    private void gotoCertification() {
        Intent intent = new Intent(context, DriverCertificationActivity.class);
        intent.putExtra("flag", driverInfo.getFlag_approve());
        intent.putExtra("name", driverInfo.getName_real());
        intent.putExtra("id", driverInfo.getCard_driver());

        startActivityForResult(intent, 110);
    }

    private void gotoQRCode() {
        Intent intent = new Intent(context, QRCodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("driverInfo", driverInfo);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
    }

// todo   以下为上传头像代码段，待完善-------------------------------------------------------------

    /**
     * @return void    返回类型
     * @throws
     * @Title: upload
     * @说 明:
     * @参 数:
     */
    public void Upload() {
        showProgressDialog();
        new Thread() {
            private String file;

            public void run() {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if ("".equals(KaKuApplication.driverInfoIcon)||KaKuApplication.driverInfoIcon == null) {
                    file = "";
                } else {
                    KaKuApplication.driverInfoIcon.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] b = stream.toByteArray();
                    // 将图片流以字符串形式存储下来
                    file = new String(Base64Coder.encodeLines(b));
                }
                HttpClient client = new DefaultHttpClient();
                // 设置上传参数
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("code", "10027"));
                formparams.add(new BasicNameValuePair("id_driver", Utils.getIdDriver()));
                formparams.add(new BasicNameValuePair("head", file));
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
                        handler.sendMessage(message);
                        client.getConnectionManager().shutdown();
                        stopProgressDialog();
                    } else {
                        LogUtil.E("上传失败~~~");
                        message.obj = msg;
                        handler.sendMessage(message);
                        client.getConnectionManager().shutdown();
                        stopProgressDialog();
                    }
//            GoToOrderList();
                } catch (Exception e) {
                    LogUtil.E("上传失败"+e.toString());
                    stopProgressDialog();
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String hint = (String) msg.obj;
            if (!TextUtils.isEmpty(hint)) {
                LogUtil.showShortToast(getApplicationContext(), msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    };

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

            KaKuApplication.driverInfoIcon = photo;
            iv_info_icon.setImageBitmap(photo);
            if (isTAKEPHOTO) {
                if (photoFile.exists()) {
                    boolean isdelete = photoFile.delete();
                    LogUtil.E("isdelete:: " + isdelete);
                }
                isTAKEPHOTO = false;
            }
        }
    }

    // 创建一个以当前时间为名称的文件
    private File dataFile;
    private File photoFile;
    private boolean isTAKEPHOTO = false;
    private static final int ZERO = 0;// 拍照
    private static final int PHOTO_REQUEST_TAKEPHOTO = 4;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 5;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 6;// 结果
    private Bitmap photo;

    private String imagePath;

    private PopupWindow window;
    //    拍照；从相册中选择；取消
    private TextView tv_takephoto, tv_myphoto, tv_exitphoto;

    public void showPopWindow(View v) {
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
                Upload();
                break;


        }

        switch (resultCode) {
            //判断是否修改过姓名
            case NAMECHANGED:
                tv_info_name.setText(data.getStringExtra("drivername"));
                //               getDriverInfo();
                break;
            case 111:
                tv_info_certification.setText("认证中");
                driverInfo.setFlag_approve("D");
                driverInfo.setCard_driver(data.getStringExtra("certifi_ID"));
                driverInfo.setName_real(data.getStringExtra("certifi_name"));


                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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

}
