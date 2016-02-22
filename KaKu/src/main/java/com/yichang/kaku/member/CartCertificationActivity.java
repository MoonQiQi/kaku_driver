package com.yichang.kaku.member;

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
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.MemberCartCertificationReq;
import com.yichang.kaku.response.MemberCartCertificationResp;
import com.yichang.kaku.tools.Base64Coder;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CartCertificationActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    private EditText et_cart_number, et_cart_length, et_cart_load, et_cart_space;
    private ImageView iv_cart_upload;
    private Button btn_cart_submit;


    private String length, number, load, space;

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

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_cart_certification);

        init();
    }

    private void init() {
        initTitleBar();

        initEditText();

        iv_cart_upload = (ImageView) findViewById(R.id.iv_cart_upload);
        iv_cart_upload.setOnClickListener(this);

        btn_cart_submit = (Button) findViewById(R.id.btn_cart_submit);
        btn_cart_submit.setOnClickListener(this);

        window = new PopupWindow();
    }

    private void initEditText() {
        et_cart_number = (EditText) findViewById(R.id.et_cart_number);
        et_cart_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {

                        String str = et_cart_number.getText().toString();
                        if (str.length() > 0 && !str.matches("^[\\u4e00-\\u9fa5]{1}[A-Za-z]{1}[A-Za-z_0-9]{5}$")) {
                            LogUtil.showShortToast(getApplicationContext(), "数据格式错误，请输入正确的车牌号，例如：京J12345");
                            et_cart_number.setText(null);
                        }
                    }

                } catch (Exception e) {
                    LogUtil.showShortToast(getApplicationContext(), e.toString());
                }
            }
        });
        et_cart_length = (EditText) findViewById(R.id.et_cart_length);
        et_cart_length.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {

                        String str = et_cart_length.getText().toString();
                        if (str.length() > 0 && !str.matches("^[1-9]\\d{0,}\\.\\d{1,2}$")) {
                            LogUtil.showShortToast(getApplicationContext(), "数据格式错误，请输入正确的车长，例如：10.50");
                            et_cart_length.setText(null);
                        }
                    }

                } catch (Exception e) {
                    LogUtil.showShortToast(getApplicationContext(), e.toString());
                }
            }
        });
        et_cart_load = (EditText) findViewById(R.id.et_cart_load);
        et_cart_load.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {

                        String str = et_cart_load.getText().toString();
                        if (str.length() > 0 && !str.matches("^[1-9]\\d{0,}\\.\\d{1,2}$")) {
                            LogUtil.showShortToast(getApplicationContext(), "数据格式错误，请输入正确的载重，例如：10.50");
                            et_cart_load.setText(null);
                        }
                    }

                } catch (Exception e) {
                    LogUtil.showShortToast(getApplicationContext(), e.toString());
                }
            }
        });
        et_cart_space = (EditText) findViewById(R.id.et_cart_space);
        et_cart_space.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus) {

                        String str = et_cart_space.getText().toString();
                        if (str.length() > 0 && !str.matches("^[1-9]\\d{0,}\\.\\d{1,2}$")) {
                            LogUtil.showShortToast(getApplicationContext(), "数据格式错误，请输入正确的载货空间，例如：10.50");
                            et_cart_space.setText(null);
                        }
                    }

                } catch (Exception e) {
                    LogUtil.showShortToast(getApplicationContext(), e.toString());
                }
            }
        });

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车辆认证");


    }

    private String file;

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.iv_cart_upload == id) {
//            上传驾驶证
            showPopWindow(v);

        } else if (R.id.btn_cart_submit == id) {
//            提交信息
/*如果字段检查没通过，则返回，不执行接口*/

            if (!checkParms()) {
                return;
            }

            Utils.NoNet(context);
            showProgressDialog();

            MemberCartCertificationReq req = new MemberCartCertificationReq();
            req.code = "10029";
            req.flag_type = "0";//安卓始终为0
            req.id_driver_car = Utils.getIdCar();
            req.num_length = length;
            req.num_load = load;
            req.num_space = space;
            req.id_no = number;


            req.image_no = transImgToString();
            KaKuApiProvider.uploadCartCertificationInfo(req, new BaseCallback<MemberCartCertificationResp>(MemberCartCertificationResp.class) {
                @Override
                public void onSuccessful(int statusCode, Header[] headers, MemberCartCertificationResp t) {
                    if (t != null) {
                        LogUtil.E("uploadCartCertificationInfo res: " + t.res);
                        if (Constants.RES.equals(t.res)) {
                            LogUtil.showShortToast(context, t.msg);
                            //设置返回结果
                            KaKuApplication.cartInfoIcon = null;
                            finish();
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


        } else if (R.id.tv_takephoto == id) {
            fecthFromCamear();

        } else if (R.id.tv_myphoto == id) {
            fecthFromGallery();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        }
    }

   /* private void setParamsN() {
        length =null;
        load =null;
        space =null;
        number =null;


    }*/

    private Boolean checkParms() {

        length = et_cart_length.getText().toString().trim();
        load = et_cart_load.getText().toString().trim();
        space = et_cart_space.getText().toString().trim();
        number = et_cart_number.getText().toString().trim();

        if (TextUtils.isEmpty(length)) {
            LogUtil.showShortToast(context, "车长不能为空");
            return false;
        }
        if (TextUtils.isEmpty(load)) {
            LogUtil.showShortToast(context, "载重不能为空");
            return false;
        }
        if (TextUtils.isEmpty(space)) {
            LogUtil.showShortToast(context, "载货空间不能为空");
            return false;
        }
        if (TextUtils.isEmpty(number)) {
            LogUtil.showShortToast(context, "车牌号不能为空");
            return false;
        }
        if (KaKuApplication.cartInfoIcon == null) {
            LogUtil.showShortToast(context, "请上传行驶证正本图片");
            return false;
        }

        return true;
    }

    public String transImgToString() {


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if ("".equals(KaKuApplication.cartInfoIcon) || KaKuApplication.cartInfoIcon == null) {
            file = "";
        } else {
            KaKuApplication.cartInfoIcon.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray();
            // 将图片流以字符串形式存储下来
            file = new String(Base64Coder.encodeLines(b));

        }
        return file;


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

            KaKuApplication.cartInfoIcon = photo;
            iv_cart_upload.setImageBitmap(photo);
            if (isTAKEPHOTO) {
                if (photoFile.exists()) {
                    boolean isdelete = photoFile.delete();
                    LogUtil.E("isdelete:: " + isdelete);
                }
                isTAKEPHOTO = false;
            }
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
        intent.putExtra("aspectX", 1.00);
        intent.putExtra("aspectY", 1.67);

        // outputX,outputY 是剪裁图片的宽高
//chaih 设置图片的长宽
        int sizeX = (int) Math.ceil(size * 1.67);
        intent.putExtra("outputX", sizeX);
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
                    startPhotoZoom(Uri.fromFile(photoFile), 300);
                window.dismiss();
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null)
                    startPhotoZoom(data.getData(), 300);
                window.dismiss();
                break;

            case PHOTO_REQUEST_CUT:
                if (data != null)
                    setPicToView(data);
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
