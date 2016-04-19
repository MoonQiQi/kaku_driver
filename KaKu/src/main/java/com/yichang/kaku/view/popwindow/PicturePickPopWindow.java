package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yichang.kaku.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PicturePickPopWindow extends PopupWindow implements View.OnClickListener {

    public static final int REQUEST_DOMAIN = 10000;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 10001;
    private static final int PHOTO_REQUEST_GALLERY = 10002;
    private static final int PHOTO_REQUEST_CUT = 10003;
    private final int picWH;

    private Activity context;
    private SimpleDateFormat dateFormat;
    private File photoFile;
    private Callback mCallback;

    public PicturePickPopWindow(Activity context, int picWH) {
        super(context);
        this.context = context;
        this.picWH = picWH;
        init(context);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_ch, null);

        setContentView(rootView);
        setFocusable(true);
        setBackgroundDrawable(null);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#44000000")));
        setAnimationStyle(R.style.popwin_anim_style);
        setOutsideTouchable(true);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        rootView.findViewById(R.id.tv_takephoto).setOnClickListener(this);
        rootView.findViewById(R.id.tv_myphoto).setOnClickListener(this);
        rootView.findViewById(R.id.tv_exitphoto).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_takephoto:
                fecthFromCamear();
                break;
            case R.id.tv_myphoto:
                fecthFromGallery();
                break;
            case R.id.tv_exitphoto:
                break;
        }
        dismiss();
    }

    /**
     * 通过相册获得图片
     */
    private void fecthFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 通过照相获得图片
     */
    private void fecthFromCamear() {

        File imagesFolder = new File(Environment.getExternalStorageDirectory() + "/kakuserver/takephoto");

        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();//如果文件夹不存在的话就创建文件夹
        }

        photoFile = new File(imagesFolder, getPhotoFileName());
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        context.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());

        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        }

        return dateFormat.format(date) + ".jpg";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO://拍照返回
                    startPhotoCut(Uri.fromFile(photoFile), picWH);
                    break;
                case PHOTO_REQUEST_GALLERY://相册返回
                    startPhotoCut(data.getData(), picWH);
                    break;
                case PHOTO_REQUEST_CUT://裁剪返回
                    if (mCallback != null) {
                        mCallback.photoCutCallback((Bitmap) data.getExtras().getParcelable("data"));
                    }
                    break;
            }
        }
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void startPhotoCut(Uri uri, int size) {
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

        context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public void setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    public interface Callback {
        void photoCutCallback(Bitmap bitmap);
    }

}
