package com.yichang.kaku.member.driver;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.Ad.ClipImageActivity;
import com.yichang.kaku.member.QRCodeActivity;
import com.yichang.kaku.obj.DriveInfoObj;
import com.yichang.kaku.request.MemberDriverInfoReq;
import com.yichang.kaku.request.MemberUploadIconReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.response.MemberDriverInfoResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.QiangImageResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

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

    private final int START_ALBUM_REQUESTCODE = 4;//相册
    private final int CAMERA_WITH_DATA = 2;//拍照
    private final int CROP_RESULT_CODE = 3;//结果
    public static final String TMP_PATH6 = "clip_temp6.jpg";
    public String token1;
    public String key1;
    public String path1;
    private Bitmap photo1;
    private PopupWindow window;
    //    拍照；从相册中选择；取消
    private TextView tv_takephoto, tv_myphoto, tv_exitphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_driverinfo);

    }

    @Override
    protected void onResume() {
        super.onResume();
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

        KaKuApiProvider.getDriverInfo(req, new KakuResponseListener<MemberDriverInfoResp>(this, MemberDriverInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getDriverInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.driver);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
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
                BitmapUtil.getInstance(context).download(iv_info_icon, KaKuApplication.qian_zhuikong + driver.getHead());
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
            KaKuApplication.flag_image = "head";
            startCapture();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            KaKuApplication.flag_image = "head";
            startAlbum();
            window.dismiss();
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

    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), TMP_PATH6)));
        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    private void startAlbum() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, START_ALBUM_REQUESTCODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, START_ALBUM_REQUESTCODE);
            } catch (Exception e2) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    public String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver()
                .query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CROP_RESULT_CODE:
                path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                photo1 = BitmapFactory.decodeFile(path1);
                KaKuApplication.ImageHead = photo1;
                iv_info_icon.setImageBitmap(photo1);
                QiNiuYunToken("1");
                break;
            case START_ALBUM_REQUESTCODE:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            case CAMERA_WITH_DATA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(Environment.getExternalStorageDirectory()
                        + "/" + TMP_PATH6);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KaKuApplication.ImageHead = null;
    }


    public void QiNiuYunToken(final String sort) {
        Utils.NoNet(context);
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = sort;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new KakuResponseListener<QiNiuYunTokenResp>(this,QiNiuYunTokenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiniuyuntoken res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                            token1 = t.token;
                            key1 = t.key;
                            uploadImg(token1, key1, sort);

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }

    private void uploadImg(final String token , final String key , final String sort){

        new Thread(new Runnable(){
            @Override
            public void run() {
                String file = path1;
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()){
                                    Upload();
                                }
                            }
                        }, null);
            }
        }).start();
    }

    public void Upload(){
        Utils.NoNet(context);
        showProgressDialog();
        MemberUploadIconReq req = new MemberUploadIconReq();
        req.code = "10027";
        req.head = key1;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.headUpload(req, new KakuResponseListener<QiangImageResp>(this,QiangImageResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

}
