package com.yichang.kaku.member.truckorder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.SendTruckOrderCommentReq;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.SendTruckOrderCommentResp;
import com.yichang.kaku.tools.Base64Coder;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCommentActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private ListView lv_comment_list;

    private List<OrderCommentObj> commentList = new ArrayList<>();

    private Button btn_submit;

    private List<ConfirmOrderProductObj> shopCarList = new ArrayList<>();

    private String mId_bill;

    private String strDefaultImg;

    private int mAdapterIndex;

    private Map<Integer, Bitmap> bmpMap = new HashMap<>();
    private String token, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_order_comment);
        init();
    }

    private void init() {

        iniTitleBar();

        lv_comment_list = (ListView) findViewById(R.id.lv_comment_list);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTruckOrderComment(lv_comment_list.getChildCount() - 1);

            }
        });

        mId_bill = getIntent().getStringExtra("id_bill");

        shopCarList = (List<ConfirmOrderProductObj>) getIntent().getSerializableExtra("shopCarList");

        window = new PopupWindow();


        setListView();
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.tianjiatupian);

        Bitmap bmp = drawableToBitmap(context, drawable);
        strDefaultImg = transBitmapToString(bmp);

    }

    Bitmap drawableToBitmap(Context context, Drawable drawable) {

        //从原始bitmap创建一个bitmap
        Bitmap bitmap = Bitmap
                .createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);

        //创建一个带有已创建bitmap的画布
        Canvas canvas = new Canvas(bitmap);

        //设置drawable图片的边缘，以改变图片的大小
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        //将图片画在bitmap的画布上

        drawable.draw(canvas);
        return bitmap;
    }

    private void sendTruckOrderComment(final int index) {
        String strRating;
        String comment;
        String strBmp;
        String id_goods;

        Bitmap bmp;
        if (index < 0) {
            LogUtil.showShortToast(context, "评论成功");
            finish();
            return;
        } else {
            if (bmpMap.containsKey(index)) {
                bmp = (Bitmap) bmpMap.get(index);
            } else {
                bmp = null;
            }

            View view = lv_comment_list.getChildAt(index);
            RatingBar rb = (RatingBar) view.findViewById(R.id.rb_comment);
            strRating = String.valueOf(rb.getRating());

            comment = ((TextView) view.findViewById(R.id.et_order_comment)).getText().toString();
            if ("".equals(comment)) {
                comment = "好评！";
            }

            ImageView image = (ImageView) view.findViewById(R.id.iv_comment_add);

            if (bmp != null) {
                strBmp = transBitmapToString(bmp);
            } else {
                strBmp = "";
            }

            id_goods = (String) image.getTag();

        }

        SendTruckOrderCommentReq req = new SendTruckOrderCommentReq();
        req.code = "30022";
        req.id_driver = Utils.getIdDriver();
        req.id_bill = mId_bill;
        req.id_goods = id_goods;
        req.content_eval = comment;
        req.image_eval = key;
        req.star_eval = strRating;

        KaKuApiProvider.sendTruckOrderComment(req, new KakuResponseListener<SendTruckOrderCommentResp>(this, SendTruckOrderCommentResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getTruckOrderDetailInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        sendTruckOrderComment(index - 1);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    private void setListView() {

        TruckOrderCommentAdapter adapter = new TruckOrderCommentAdapter(context, shopCarList);
        adapter.setCallBack(new TruckOrderCommentAdapter.ICallBack() {
            @Override
            public void setPicToView(ImageView view, int adapterIndex) {
                view_img = view;
                mAdapterIndex = adapterIndex;
                showPopWindow(view);
            }
        });
        lv_comment_list.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(lv_comment_list);
    }


    private void iniTitleBar() {

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("评价订单");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();

        /*点击事件*/
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_takephoto == id) {
            fecthFromCamear();

        } else if (R.id.tv_myphoto == id) {
            fecthFromGallery();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        }

    }

    private ImageView view_img;

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

    private void setPicToView(Intent picdata, ImageView imageView) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            photo = bundle.getParcelable("data");
            try {
                saveFile(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            KaKuApplication.OrderCommentIcon = photo;
            imageView.setImageBitmap(photo);

            bmpMap.put(mAdapterIndex, photo);

            if (isTAKEPHOTO) {
                if (photoFile.exists()) {
                    boolean isdelete = photoFile.delete();
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
            dataFile = new File(this.getFilesDir(), getPhotoFileName() + ".jpg");
        if (dataFile.exists())
            dataFile.delete();// 如果有同名文件存在先删除
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(dataFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        imagePath = dataFile.toString();
        QiNiuYunToken(imagePath);
        LogUtil.E("imagePath : " + imagePath);
    }

    /**
     * 剪裁图片
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
                    setPicToView(data, view_img);
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

    private String transBitmapToString(Bitmap bitmap) {
        String file;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //如果图片是空则返回空串“”
        if (bitmap == null) {
            return "";
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] b = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        file = new String(Base64Coder.encodeLines(b));

        return file;
    }

    public void QiNiuYunToken(final String imagePath) {
        Utils.NoNet(context);
        QiNiuYunTokenReq req = new QiNiuYunTokenReq();
        req.code = "qn01";
        req.sort = "1";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.QiNiuYunToken(req, new KakuResponseListener<QiNiuYunTokenResp>(this, QiNiuYunTokenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiniuyuntoken res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        token = t.token;
                        key = t.key;
                        uploadImg(token, key, "1", imagePath);

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private String uploadImg(final String token, final String key, final String sort, final String imagePath) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String file = imagePath;

                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()) {
                                }
                            }
                        }, null);
            }
        }).start();

        return key;
    }

}
