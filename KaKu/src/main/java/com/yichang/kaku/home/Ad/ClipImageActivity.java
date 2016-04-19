package com.yichang.kaku.home.Ad;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.driver.DriverInfoActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 裁剪图片的Activity
 *
 * @author xiechengfa2000@163.com
 * @ClassName: CropImageActivity
 * @Description:
 * @date 2015-5-8 下午3:39:22
 */
public class ClipImageActivity extends Activity implements OnClickListener {
    public static final String RESULT_PATH = "crop_image";
    private static final String KEY = "path";
    private ClipImageLayout mClipImageLayout = null;

    public static void startActivity(Activity activity, String path, int code) {
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.putExtra(KEY, path);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image_layout);

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.clipImageLayout);
        String path = getIntent().getStringExtra(KEY);

        // 有的系统返回的图片是旋转了，有的没有旋转，所以处理
        int degreee = readBitmapDegree(path);
        Bitmap bitmap = createBitmap(path);
        if (bitmap != null) {
            if (degreee == 0) {
                mClipImageLayout.setImageBitmap(bitmap);
            } else {
                mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
            }
        } else {
            finish();
        }
        findViewById(R.id.okBtn).setOnClickListener(this);
        findViewById(R.id.cancleBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.okBtn) {
            Bitmap bitmap = mClipImageLayout.clip();
            Bitmap bitmap1 = compressImage(bitmap);
            if ("chetie".equals(KaKuApplication.flag_image)) {
                String path1 = Environment.getExternalStorageDirectory() + "/" + AdImageActivity.TMP_PATH1;
                saveBitmap(bitmap1, path1);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path1);
                setResult(RESULT_OK, intent);
            } else if ("ben".equals(KaKuApplication.flag_image)) {
                String path4 = Environment.getExternalStorageDirectory() + "/" + QiangImageActivity.TMP_PATH4;
                saveBitmap(bitmap1, path4);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path4);
                setResult(RESULT_OK, intent);
            } else if ("che".equals(KaKuApplication.flag_image)) {
                String path5 = Environment.getExternalStorageDirectory() + "/" + QiangImageActivity.TMP_PATH5;
                saveBitmap(bitmap1, path5);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path5);
                setResult(RESULT_OK, intent);
            } else if ("head".equals(KaKuApplication.flag_image)) {
                String path6 = Environment.getExternalStorageDirectory() + "/" + DriverInfoActivity.TMP_PATH6;
                saveBitmap(bitmap1, path6);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path6);
                setResult(RESULT_OK, intent);
            } else if ("xingshizheng".equals(KaKuApplication.flag_image)) {
                String path7 = Environment.getExternalStorageDirectory() + "/" + XingShiZhengImageActivity.TMP_PATH7;
                saveBitmap(bitmap1, path7);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path7);
                setResult(RESULT_OK, intent);
            } else if ("jiashizheng".equals(KaKuApplication.flag_image)) {
                String path8 = Environment.getExternalStorageDirectory() + "/" + JiaShiZhengActivity.TMP_PATH8;
                saveBitmap(bitmap1, path8);
                Intent intent = new Intent();
                intent.putExtra(RESULT_PATH, path8);
                setResult(RESULT_OK, intent);
            }
        }
        finish();
    }

    private void saveBitmap(Bitmap bitmap, String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建图片
     *
     * @param path
     * @return
     */
    private Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        FileInputStream is = null;
        Bitmap bitmap = null;
      /*  try {
            is = new FileInputStream(path);
            bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);

        } catch (IOException e) {
            e.printStackTrace();
        } */
        bitmap  = getSmallBitmap(path);
        //finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }

        return bitmap;
    }

    // 读取图像的旋转度
    private int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 旋转图片
    private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 360, 360);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        while ( (baos.toByteArray().length / 1024) > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
