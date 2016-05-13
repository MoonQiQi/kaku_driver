package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.faxian.ReMenAdapter;
import com.yichang.kaku.home.text.ClearEditText;
import com.yichang.kaku.home.text.MyGridView;
import com.yichang.kaku.obj.PinPaiXuanZeObj;
import com.yichang.kaku.request.PinPaiXuanZeReq;
import com.yichang.kaku.response.PinPaiXuanZeResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PinPaiXuanZeActivity extends BaseActivity implements OnClickListener, SectionIndexer {

    private TextView left, right, title1;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortGroupMemberAdapter adapter;
    private ClearEditText mClearEditText;
    private TextView title;
    private TextView tvNofriends;
    private LinearLayout titleLayout;
    private ImageView image;
    List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> SourceDateList = new ArrayList<GroupMemberBean>();
    private List<PinPaiXuanZeObj> customList = new ArrayList<PinPaiXuanZeObj>();
    private String[] str;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private MyGridView gv_remen;
    private List<PinPaiXuanZeObj> list_remen = new ArrayList<PinPaiXuanZeObj>();
    private ReMenAdapter adapter_remen;
    private String id_brand = "";
    private String id_series = "";
    private String id_fuel = "";
    private String id_model = "";
    private String id_actuate = "";
    private String id_car;
    private String name_brand;
    private String img_url;
    private boolean flag_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinpaixuanze);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        flag_dialog = true;
        PinPaiXuanZe();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title1 = (TextView) findViewById(R.id.tv_mid);
        title1.setText("品牌选择");
        gv_remen = (MyGridView) findViewById(R.id.gv_remen);
        gv_remen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.NoNet(PinPaiXuanZeActivity.this);
                KaKuApplication.id_brand = list_remen.get(position).getId_brand();
                startActivity(new Intent(context, FaDongJiInfoActivity.class));
            }
        });
        image = (ImageView) this.findViewById(R.id.image);
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("输码识车");
        right.setOnClickListener(this);
        KaKuApplication.car_code = "";

        tvNofriends = (TextView) this.findViewById(R.id.title_layout_no_friends);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        dialog = (TextView) findViewById(R.id.dialog);

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setFocusable(false);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Utils.NoNet(PinPaiXuanZeActivity.this);
                if (TextUtils.isEmpty(mClearEditText.getText().toString())) {
                    KaKuApplication.id_brand = SourceDateList.get(position).getId();
                } else {
                    KaKuApplication.id_brand = filterDateList.get(position).getId();
                }
                startActivity(new Intent(context, FaDongJiInfoActivity.class));
            }
        });

        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<GroupMemberBean> filledData(List<PinPaiXuanZeObj> list) {
        String[] date = new String[list.size()];
        for (int i = 0; i < date.length; i++) {
            date[i] = list.get(i).getName_brand();
        }
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            sortModel.setId(list.get(i).getId_brand());
            sortModel.setImage(list.get(i).getImage_brand());
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        if (TextUtils.isEmpty(filterStr)) {
            //filterDateList = SourceDateList;
            flag_dialog = false;
            tvNofriends.setVisibility(View.GONE);
            PinPaiXuanZe();
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
            tvNofriends.setVisibility(View.GONE);
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            tvNofriends.setVisibility(View.VISIBLE);
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
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, HandCodeActivity.class));
        }
    }

    public void PinPaiXuanZe() {
        if (flag_dialog) {
            showProgressDialog();
        }
        PinPaiXuanZeReq req = new PinPaiXuanZeReq();
        req.code = "2008";
        KaKuApiProvider.PinPaiXuanZe(req, new KakuResponseListener<PinPaiXuanZeResp>(this, PinPaiXuanZeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("pinpaixuanze res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.brands.size() == 0) {
                            return;
                        }
                        customList = t.brands;
                        list_remen = t.brands_hot;
                        adapter_remen = new ReMenAdapter(context, list_remen);
                        gv_remen.setAdapter(adapter_remen);
                        SourceDateList = filledData(customList);
                        Collections.sort(SourceDateList, pinyinComparator);
                        adapter = new SortGroupMemberAdapter(PinPaiXuanZeActivity.this, SourceDateList);
                        sortListView.setAdapter(adapter);
                        Utils.setListViewHeightBasedOnChildren(sortListView);
                        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem,
                                                 int visibleItemCount, int totalItemCount) {

                                int section = getSectionForPosition(firstVisibleItem);
                                int nextSection = getSectionForPosition(firstVisibleItem);
                                int nextSecPosition = getPositionForSection(+nextSection);
                                if (nextSecPosition == firstVisibleItem + 1) {
                                    View childView = view.getChildAt(0);
                                }
                                lastFirstVisibleItem = firstVisibleItem;
                            }
                        });
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    /**
     * 根据分类的首字母的Charascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Charascii值ֵ
     */
    public int getSectionForPosition(int position) {
        return SourceDateList.get(position).getSortLetters().charAt(0);
    }

    @Override
    public Object[] getSections() {
        return null;
    }

}
