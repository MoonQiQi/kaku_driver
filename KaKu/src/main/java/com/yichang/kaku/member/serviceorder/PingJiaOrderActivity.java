package com.yichang.kaku.member.serviceorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.PingJiaActivity;
import com.yichang.kaku.tools.Base64Coder;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.uploadimages.activity.AlbumActivity;
import com.yichang.kaku.view.uploadimages.activity.GalleryActivity;
import com.yichang.kaku.view.uploadimages.util.Bimp;
import com.yichang.kaku.view.uploadimages.util.FileUtils;
import com.yichang.kaku.view.uploadimages.util.ImageItem;
import com.yichang.kaku.view.uploadimages.util.PublicWay;
import com.yichang.kaku.view.uploadimages.util.Res;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PingJiaOrderActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private EditText et_pingjiaorder_content;
    private ImageView iv_pingjiaorder_image;
    private PopupWindow window;
    private TextView tv_takephoto;
    private TextView tv_myphoto;
    private TextView tv_exitphoto,tv_pingjiaorder_name,tv_pingjiaorder_addr;
    private String imagePath;
    private RatingBar star_pingjia1;
    private Button btn_pingjiaorder_fabiao;

    // 创建一个以当前时间为名称的文件
    private File dataFile;
    private File photoFile;
    private boolean isTAKEPHOTO = false;
    private static final int ZERO = 0;// 拍照
    private static final int PHOTO_REQUEST_TAKEPHOTO = 4;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 5;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 6;// 结果
    private Bitmap photo;

    private GridView noScrollgridview;
    private GridAdapter gvAdapter;
    public static Bitmap bimap;

    ///scrollView
    private ScrollView scroll_view;
    private Boolean isShowTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjiaorder);
        Res.init(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_pingjiaorder_name.setText(KaKuApplication.name_shop);
        tv_pingjiaorder_addr.setText(KaKuApplication.addr_shop);
        et_pingjiaorder_content.setText(KaKuApplication.pingjia_shop);
        BitmapUtil.getInstance(context).download(iv_pingjiaorder_image,KaKuApplication.qian_zhui+ KaKuApplication.image_shop);
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我要点评");
        et_pingjiaorder_content = (EditText) findViewById(R.id.et_pingjiaorder_content);
        iv_pingjiaorder_image= (ImageView) findViewById(R.id.iv_pingjiaorder_image);
        tv_pingjiaorder_name= (TextView) findViewById(R.id.tv_pingjiaorder_name);
        tv_pingjiaorder_addr= (TextView) findViewById(R.id.tv_pingjiaorder_addr);
        KaKuApplication.pingjia_shop = "";
        et_pingjiaorder_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        star_pingjia1 = (RatingBar) findViewById(R.id.star_pingjia1);
        btn_pingjiaorder_fabiao = (Button) findViewById(R.id.btn_pingjiaorder_fabiao);
        btn_pingjiaorder_fabiao.setOnClickListener(this);
        window = new PopupWindow();

        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvAdapter = new GridAdapter(this);
        gvAdapter.update();
        noScrollgridview.setAdapter(gvAdapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentView, View view, int arg2,
                                    long arg3) {
                if (Utils.Many()){
                    return;
                }
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    Log.i("ddddddd", "----------");
                    ShowWindow(view);
                    window.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

        scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        isShowTop = getIntent().getBooleanExtra("showTop", false);
        if (KaKuApplication.flag_IsDetailToPingJia){
            Intent intent = getIntent();
            KaKuApplication.addr_shop = intent.getExtras().getString("addr_shop");;
            KaKuApplication.name_shop = intent.getExtras().getString("name_shop");;
            KaKuApplication.image_shop = intent.getExtras().getString("image_shop");;
            KaKuApplication.flag_IsDetailToPingJia = false;
            tv_pingjiaorder_name.setText(KaKuApplication.name_shop);
            tv_pingjiaorder_addr.setText(KaKuApplication.addr_shop);
            BitmapUtil.getInstance(context).download(iv_pingjiaorder_image,KaKuApplication.qian_zhui+ KaKuApplication.image_shop);
            Bimp.tempSelectBitmap.clear();
            Bimp.max=0;
        }
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
        } else if (R.id.tv_takephoto == id) {
            photo();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            Intent intent = new Intent(context, AlbumActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            window.dismiss();

        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();

        } else if (R.id.btn_pingjiaorder_fabiao == id) {
            KaKuApplication.pingjia_shop = et_pingjiaorder_content.getText().toString().trim();
            if (TextUtils.isEmpty(KaKuApplication.pingjia_shop)) {
                LogUtil.showShortToast(context, "评价内容不能为空");
                return;
            }
            Upload();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;
        listView.setLayoutParams(params);
    }

    public void ShowWindow(View v) {
        KaKuApplication.pingjia_shop = et_pingjiaorder_content.getText().toString().trim();
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

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String hint = (String) msg.obj;
            if (!TextUtils.isEmpty(hint)) {
                LogUtil.showShortToast(getApplicationContext(),
                        msg.obj.toString());
            }
            if (msg.what == 1) {
                scroll_view.fullScroll(ScrollView.FOCUS_DOWN);
            }
            super.handleMessage(msg);
        }
    };

    public void Upload() {
        showProgressDialog();
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                // 设置上传参数
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("code", "8007"));
                formparams.add(new BasicNameValuePair("id_shop", KaKuApplication.id_shop));
                formparams.add(new BasicNameValuePair("id_driver", Utils.getIdDriver()));
                formparams.add(new BasicNameValuePair("star_eval", String.valueOf(star_pingjia1.getRating())));
                formparams.add(new BasicNameValuePair("content_eval", KaKuApplication.pingjia_shop));
                //根据不同个数的图片给接口赋值
                for (int i = 1; i < Bimp.tempSelectBitmap.size() + 1; i++) {
                    formparams.add(new BasicNameValuePair("image" + i + "_eval", transBitmapToString(Bimp.tempSelectBitmap.get(i - 1).getBitmap())));
                }

                LogUtil.E("----" + formparams.toString());
                HttpPost post = new HttpPost(UrlCtnt.BASEIP);
                UrlEncodedFormEntity entity;
                try {
                    entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                    post.addHeader("Accept", "text/javascript, text/html, application/xml, text/xml");
                    post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
                    post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
                    post.addHeader("Connection", "Keep-Alive");
                    post.addHeader("Cache-Control", "no-cache");
                    post.addHeader("Content-Type", "application/x-www-form-urlencoded");
                    post.setEntity(entity);
                    HttpResponse response = client.execute(post);
                    LogUtil.E("statuscode:" + response.getStatusLine().getStatusCode());
                    String json = EntityUtils.toString(response.getEntity());
                    LogUtil.E("json:" + json);
                    JSONObject object = new JSONObject(json);
                    String res = object.getString("res");
                    String msg = object.getString("msg");
                    LogUtil.E("msg:" + msg);
                    LogUtil.E("res:" + res);

                    Message message = new Message();
                    if (200 == response.getStatusLine().getStatusCode()) {
                        LogUtil.E("上传完成~~~");
                        message.obj = msg;
                        //h.sendMessage(message);
                        client.getConnectionManager().shutdown();
//  todo                      清空评论内容
                        stopProgressDialog();
                    } else {
                        LogUtil.E("上传失败~~~");
                        message.obj = msg;
                        //h.sendMessage(message);
                        client.getConnectionManager().shutdown();
                        stopProgressDialog();
                    }
                    startActivity(new Intent(context, PingJiaActivity.class));
                    finish();
                } catch (Exception e) {
                    LogUtil.E("上传失败" + e.toString());
                    stopProgressDialog();
                    e.printStackTrace();
                }
            }
        }.start();
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

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        private Bitmap holderBmp;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 4) {
                return 4;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                if (holderBmp == null) {
                    holderBmp = BitmapFactory.decodeResource(
                            getResources(), R.drawable.icon_addpic_unfocused);
                }
                holder.image.setImageBitmap(holderBmp);
                if (position == 4) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        gvAdapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            Bimp.max += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        gvAdapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 4 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            //System.exit(0);
            this.finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //页面关闭时清空评论内容
        //KaKuApplication.pingjiaOrderContent = "";
        //页面关闭时清空图片列表
        /*Bimp.tempSelectBitmap.clear();
        Bimp.max=0;*/
        super.onDestroy();

    }
}
