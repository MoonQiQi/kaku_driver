package com.yichang.kaku.home.mycar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
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
import com.yichang.kaku.home.ad.ClipImageActivity;
import com.yichang.kaku.home.baoyang.BaoYangActivity;
import com.yichang.kaku.home.baoyang.YellowOilActivity;
import com.yichang.kaku.obj.SeriesObj;
import com.yichang.kaku.request.DeleteMyCarReq;
import com.yichang.kaku.request.FaDongJiInfoReq;
import com.yichang.kaku.request.ModifyFaDongJiReq;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.SaveCarReq;
import com.yichang.kaku.response.DeleteMyCarResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.FaDongJiInfoResp;
import com.yichang.kaku.response.ModifyFaDongJiInfoResp;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCarActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private String[] jian_list = new String[]{"京", "沪", "豫", "浙", "苏", "粤", "鲁", "晋", "冀", "川", "赣", "辽", "吉", "黑", "皖", "鄂", "湘",
            "渝", "闽", "陕", "桂", "甘", "宁", "蒙", "津", "贵", "云", "琼", "青", "新", "藏", "港", "澳"};
    private TextView tv_addcar_carnum, tv_addcar_car, tv_addcar_time, tv_addcar_fadongji, tv_addcar_paifang, tv_addcar_mali;
    private RelativeLayout rela_addcar_grid, rela_addcar_time, rela_addcar_fadongji, rela_addcar_paifang, rela_addcar_mali;
    private GridView gv_addcar_city;
    private List<String> list_city = new ArrayList<>();
    private ImageView iv_addcar_car, iv_addcar_xingshizheng, iv_addcar_moren;
    private List<SeriesObj> chexi_list = new ArrayList<>();
    private EditText et_addcar_licheng, et_addcar_carnum;
    private Button btn_addcar_save;
    private String name_brand, image_brand, id_chexi, id_data, id_car_series;
    public static final String TMP_PATH11 = "clip_temp11.jpg";
    public String token1;
    public String key1 = "";
    public String path1;
    public String image_no;
    private String flag_car;
    private Bitmap photo1;
    private String id_data2;
    private PopupWindow window;
    private TextView tv_takephoto, tv_myphoto, tv_exitphoto;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private final int START_ALBUM_REQUESTCODE = 4;//相册
    private int mYear;
    private int mMonth;
    private int mDay;
    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AddCarActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }

    };
    private long currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);

        for (int i = 0; i < jian_list.length; i++) {
            list_city.add(jian_list[i]);
        }
        id_data = getIntent().getStringExtra("id_data");
        iv_addcar_car = (ImageView) findViewById(R.id.iv_addcar_car);
        iv_addcar_xingshizheng = (ImageView) findViewById(R.id.iv_addcar_xingshizheng);
        iv_addcar_moren = (ImageView) findViewById(R.id.iv_addcar_moren);
        iv_addcar_moren.setOnClickListener(this);
        iv_addcar_xingshizheng.setOnClickListener(this);
        tv_addcar_car = (TextView) findViewById(R.id.tv_addcar_car);
        tv_addcar_mali = (TextView) findViewById(R.id.tv_addcar_mali);
        tv_addcar_fadongji = (TextView) findViewById(R.id.tv_addcar_fadongji);
        tv_addcar_paifang = (TextView) findViewById(R.id.tv_addcar_paifang);
        tv_addcar_time = (TextView) findViewById(R.id.tv_addcar_time);
        tv_addcar_carnum = (TextView) findViewById(R.id.tv_addcar_carnum);
        tv_addcar_carnum.setOnClickListener(this);
        btn_addcar_save = (Button) findViewById(R.id.btn_addcar_save);
        btn_addcar_save.setOnClickListener(this);
        et_addcar_licheng = (EditText) findViewById(R.id.et_addcar_licheng);
        et_addcar_carnum = (EditText) findViewById(R.id.et_addcar_carnum);
        rela_addcar_grid = (RelativeLayout) findViewById(R.id.rela_addcar_grid);
        rela_addcar_time = (RelativeLayout) findViewById(R.id.rela_addcar_time);
        rela_addcar_time.setOnClickListener(this);
        rela_addcar_fadongji = (RelativeLayout) findViewById(R.id.rela_addcar_fadongji);
        rela_addcar_fadongji.setOnClickListener(this);
        rela_addcar_paifang = (RelativeLayout) findViewById(R.id.rela_addcar_paifang);
        rela_addcar_paifang.setOnClickListener(this);
        rela_addcar_mali = (RelativeLayout) findViewById(R.id.rela_addcar_mali);
        rela_addcar_mali.setOnClickListener(this);
        gv_addcar_city = (GridView) findViewById(R.id.gv_addcar_city);
        gv_addcar_city.setOnItemClickListener(this);
        if ("T".equals(KaKuApplication.flag_car)) {
            title.setText("添加车辆");
            GetFaDongJiInfo(id_data);
        } else if ("B".equals(KaKuApplication.flag_car)) {
            title.setText("编辑爱车");
            right = (TextView) findViewById(R.id.tv_right);
            right.setVisibility(View.VISIBLE);
            right.setText("删除");
            right.setOnClickListener(this);
            iv_addcar_moren.setVisibility(View.VISIBLE);
            ModifyFaDongJiInfo();
        } else {
            title.setText("编辑爱车");
            right = (TextView) findViewById(R.id.tv_right);
            right.setVisibility(View.VISIBLE);
            right.setText("删除");
            right.setOnClickListener(this);
            iv_addcar_moren.setVisibility(View.VISIBLE);
            GetFaDongJiInfo(id_data);
        }

        AddCarCityAdapter adapter = new AddCarCityAdapter(context, list_city);
        gv_addcar_city.setAdapter(adapter);
        final Calendar c = Calendar.getInstance();
        currentTime = c.getTimeInMillis();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        window = new PopupWindow();
        setDateTime();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            KaKuApplication.addcar_type = "";
            finish();
        } else if (R.id.tv_addcar_carnum == id) {
            rela_addcar_grid.setVisibility(View.VISIBLE);
        } else if (R.id.rela_addcar_time == id) {
            Message msg = new Message();
            msg.what = AddCarActivity.SHOW_DATAPICK;
            AddCarActivity.this.dateandtimeHandler.sendMessage(msg);
        } else if (R.id.rela_addcar_fadongji == id) {
            KaKuApplication.flag_car = "B";
            if ("B".equals(KaKuApplication.flag_car)) {
                KaKuApplication.addcar_type = "B";
                KaKuApplication.addcar_image = image_no;
                KaKuApplication.addcar_chepaizi = tv_addcar_car.getText().toString().trim();
                KaKuApplication.addcar_chepaihao = et_addcar_carnum.getText().toString().trim();
                KaKuApplication.addcar_gongli = et_addcar_licheng.getText().toString().trim();
                KaKuApplication.addcar_shijian = tv_addcar_time.getText().toString().trim();
                startActivity(new Intent(this, FaDongJiInfoActivity.class));
            } else {
                finish();
            }
            KaKuApplication.flag_car = "M";
        } else if (R.id.rela_addcar_paifang == id) {
            KaKuApplication.flag_car = "B";
            if ("B".equals(KaKuApplication.flag_car)) {
                KaKuApplication.addcar_type = "B";
                KaKuApplication.addcar_image = image_no;
                KaKuApplication.addcar_chepaizi = tv_addcar_car.getText().toString().trim();
                KaKuApplication.addcar_chepaihao = et_addcar_carnum.getText().toString().trim();
                KaKuApplication.addcar_gongli = et_addcar_licheng.getText().toString().trim();
                KaKuApplication.addcar_shijian = tv_addcar_time.getText().toString().trim();
                startActivity(new Intent(this, FaDongJiInfoActivity.class));
            } else {
                finish();
            }
            KaKuApplication.flag_car = "M";
        } else if (R.id.rela_addcar_mali == id) {
            KaKuApplication.flag_car = "B";
            if ("B".equals(KaKuApplication.flag_car)) {
                KaKuApplication.addcar_type = "B";
                KaKuApplication.addcar_image = image_no;
                KaKuApplication.addcar_chepaizi = tv_addcar_car.getText().toString().trim();
                KaKuApplication.addcar_chepaihao = et_addcar_carnum.getText().toString().trim();
                KaKuApplication.addcar_gongli = et_addcar_licheng.getText().toString().trim();
                KaKuApplication.addcar_shijian = tv_addcar_time.getText().toString().trim();
                startActivity(new Intent(this, FaDongJiInfoActivity.class));
            } else {
                finish();
            }
            KaKuApplication.flag_car = "M";
        } else if (R.id.btn_addcar_save == id) {
            KaKuApplication.addcar_type = "";
            if (et_addcar_carnum.getText().toString().trim().length() != 6) {
                LogUtil.showShortToast(context, "请完善您的车辆信息");
                return;
            }
            if (KaKuApplication.ImageXingShiZheng2 != null) {
                QiNiuYunToken();
            } else {
                Upload();
            }
        } else if (R.id.iv_addcar_xingshizheng == id) {
            showPopWindow(v);
        } else if (R.id.tv_takephoto == id) {
            KaKuApplication.flag_image = "xingshizheng2";
            startCapture();
            window.dismiss();
        } else if (R.id.tv_myphoto == id) {
            KaKuApplication.flag_image = "xingshizheng2";
            startAlbum();
            window.dismiss();
        } else if (R.id.tv_exitphoto == id) {
            window.dismiss();
        } else if (R.id.tv_right == id) {
            Delete();
        } else if (R.id.iv_addcar_moren == id) {
            if ("Y".equals(flag_car)) {
                flag_car = "N";
                iv_addcar_moren.setImageResource(R.drawable.car_feimoren);
            } else {
                flag_car = "Y";
                iv_addcar_moren.setImageResource(R.drawable.car_moren);
            }
        }
    }

    public void GetFaDongJiInfo(String id_data) {
        showProgressDialog();
        FaDongJiInfoReq req = new FaDongJiInfoReq();
        req.code = "20018";
        req.id_data = id_data;
        req.id_brand = KaKuApplication.id_brand;
        KaKuApiProvider.FaDongJiInfo(req, new KakuResponseListener<FaDongJiInfoResp>(context, FaDongJiInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getfadongjiinfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });

    }

    public void ModifyFaDongJiInfo() {
        showProgressDialog();
        ModifyFaDongJiReq req = new ModifyFaDongJiReq();
        req.code = "20021";
        req.id_driver_car = KaKuApplication.id_driver_car;
        KaKuApiProvider.ModifyCar(req, new KakuResponseListener<ModifyFaDongJiInfoResp>(context, ModifyFaDongJiInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getfadongjiinfo2 res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText2(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });

    }

    public void SetText(FaDongJiInfoResp t) {
        name_brand = t.name_brand;
        image_brand = t.image_brand;
        chexi_list = t.series;
        tv_addcar_car.setText(t.name_brand);
        tv_addcar_fadongji.setText(t.engine.getName_engine() + t.engine.getSeries_engine());
        tv_addcar_paifang.setText(t.engine.getEmissions());
        tv_addcar_mali.setText(t.engine.getPower());
        BitmapUtil.getInstance(context).download(iv_addcar_car, KaKuApplication.qian_zhui + t.image_brand);
        if ("B".equals(KaKuApplication.addcar_type)) {
            tv_addcar_car.setText(KaKuApplication.addcar_chepaizi);
            tv_addcar_time.setText(KaKuApplication.addcar_shijian);
            et_addcar_carnum.setText(KaKuApplication.addcar_chepaihao);
            et_addcar_licheng.setText(KaKuApplication.addcar_gongli);
            BitmapUtil.getInstance(context).download(iv_addcar_xingshizheng, KaKuApplication.addcar_image);
        }
    }

    public void SetText2(ModifyFaDongJiInfoResp t) {
        name_brand = t.car.getName_brand();
        image_brand = t.car.getImage_brand();
        chexi_list = t.series;
        KaKuApplication.id_brand = t.car.getId_brand();
        KaKuApplication.car_code = t.car.getCar_code();
        if ("B".equals(KaKuApplication.addcar_type)) {
            tv_addcar_car.setText(KaKuApplication.addcar_chepaizi);
            tv_addcar_time.setText(KaKuApplication.addcar_shijian);
            et_addcar_carnum.setText(KaKuApplication.addcar_chepaihao);
            et_addcar_licheng.setText(KaKuApplication.addcar_gongli);
            BitmapUtil.getInstance(context).download(iv_addcar_xingshizheng, KaKuApplication.addcar_image);
        } else {
            tv_addcar_car.setText(t.car.getName_brand());
            et_addcar_carnum.setText(t.car.getCar_no());
            tv_addcar_time.setText(t.car.getTime_road());
            et_addcar_licheng.setText(t.car.getTravel_mileage());
        }
        tv_addcar_car.setText(t.car.getName_brand());
        et_addcar_carnum.setText(t.car.getCar_no());
        et_addcar_licheng.setText(t.car.getTravel_mileage());
        tv_addcar_carnum.setText(t.car.getCar_no_name());
        iv_addcar_moren.setVisibility(View.VISIBLE);
        if ("Y".equals(t.car.getFlag_default())) {
            flag_car = "Y";
            iv_addcar_moren.setImageResource(R.drawable.car_moren);
            iv_addcar_moren.setEnabled(false);
        } else {
            flag_car = "N";
            iv_addcar_moren.setImageResource(R.drawable.car_feimoren);
        }
        tv_addcar_fadongji.setText(t.car.getName_engine() + t.car.getSeries_engine());
        tv_addcar_paifang.setText(t.car.getEmissions());
        tv_addcar_mali.setText(t.car.getPower());
        key1 = t.car.getImage_no();
        id_data2 = t.car.getId_data();
        id_car_series = t.car.getId_car_series();
        image_no = t.car.getImage_no();
        BitmapUtil.getInstance(context).download(iv_addcar_car, KaKuApplication.qian_zhui + t.car.getImage_brand());
        BitmapUtil.getInstance(context).download(iv_addcar_xingshizheng, t.car.getImage_no());
    }

    /**
     * 通过照相获得图片
     *
     * @param
     */
    private void startCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if ("xingshizheng2".equals(KaKuApplication.flag_image)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), TMP_PATH11)));
        }
        startActivityForResult(intent, 11);
    }

    private void startAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, START_ALBUM_REQUESTCODE);
    }

    public void QiNiuYunToken() {
        Utils.NoNet(context);
        showProgressDialog();
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
                        token1 = t.token;
                        key1 = t.key;
                        uploadImg(token1, key1);

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

    private void uploadImg(final String token, final String key) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String file = path1;

                UploadManager uploadManager = new UploadManager();
                uploadManager.put(file, key, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String arg0, ResponseInfo info, JSONObject response) {
                                // TODO Auto-generated method stub
                                if (info.isOK()) {
                                    Upload();
                                }
                            }
                        }, null);
            }
        }).start();
    }

    public void Upload() {
        Utils.NoNet(context);

        SaveCarReq req = new SaveCarReq();
        req.code = "20019";
        req.car_code = KaKuApplication.car_code;
        req.no_car = tv_addcar_carnum.getText().toString().trim() + et_addcar_carnum.getText().toString().trim();
        req.travel_mileage = et_addcar_licheng.getText().toString().trim();
        if ("B".equals(KaKuApplication.flag_car)) {
            req.id_data = id_data2;
            req.id_car_series = id_car_series;
            req.id_driver_car = KaKuApplication.id_driver_car;
        } else {
            req.id_data = id_data;
            req.id_car_series = id_chexi;
            req.id_driver_car = "";
        }
        req.image_no = key1;
        req.time_road = tv_addcar_time.getText().toString().trim();
        req.id_brand = KaKuApplication.id_brand;
        req.flag_car = flag_car;
        KaKuApiProvider.SaveCar(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("uploadimage res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("huanjiyou".equals(KaKuApplication.flag_addcar)) {
                            Intent intent = new Intent(context, BaoYangActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else if ("dahuangyou".equals(KaKuApplication.flag_addcar)) {
                            Intent intent = new Intent(context, YellowOilActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            startActivity(new Intent(context, MyCarActivity.class));
                            finish();
                        }

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
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
     * 设置日期
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDateDisplay();
    }

    /**
     * 更新日期显示
     */
    private void updateDateDisplay() {
        tv_addcar_time.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog dp = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                dp.getDatePicker().setMaxDate(currentTime);
                return dp;

        }

        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KaKuApplication.ImageXingShiZheng2 = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 13:
                id_chexi = data.getExtras().getString("id");
                String name = data.getExtras().getString("name");
                break;
            case 12:
                if ("xingshizheng2".equals(KaKuApplication.flag_image)) {
                    path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
                    photo1 = BitmapFactory.decodeFile(path1);
                    KaKuApplication.ImageXingShiZheng2 = photo1;
                    iv_addcar_xingshizheng.setImageBitmap(photo1);
                }
                break;
            case 11:
                if ("xingshizheng2".equals(KaKuApplication.flag_image)) {
                    startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH11);
                }
                break;
            case START_ALBUM_REQUESTCODE:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            default:
                break;
        }
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        ClipImageActivity.startActivity(this, path, 12);
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

    public void Delete() {
        Utils.NoNet(context);
        showProgressDialog();
        DeleteMyCarReq req = new DeleteMyCarReq();
        req.code = "2007";
        req.id_driver_car = KaKuApplication.id_driver_car;
        KaKuApiProvider.DeleteMyCar(req, new KakuResponseListener<DeleteMyCarResp>(this, DeleteMyCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deletecar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        startActivity(new Intent(context, MyCarActivity.class));
                        finish();
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (R.id.gv_addcar_city == parent.getId()) {
            tv_addcar_carnum.setText(list_city.get(position));
            rela_addcar_grid.setVisibility(View.GONE);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            KaKuApplication.addcar_type = "";
        }
        return false;
    }
}
