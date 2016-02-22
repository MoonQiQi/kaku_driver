package com.yichang.kaku.home.question;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.QuestionAnswerItemObj;
import com.yichang.kaku.obj.QuestionAnswerObj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.KakuMultiLineTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 所在位置适配器
 *
 * @author yxx
 */
public class QuestionDetailAdapter extends BaseAdapter {

    private Context context;
    private List<QuestionAnswerObj> list;
    private ViewHolder holder;
    private String mId_driver;
    private String mId_child;
    private boolean mIsAccept;

    public void setmIsAccept(boolean mIsAccept) {
        this.mIsAccept = mIsAccept;
    }

    private boolean mIsDirverSelf;

    /**
     * @param id_child     被采纳问题id
     * @param id_driver    提问者id
     * @param isAccept     是否有采纳答案
     * @param isDirverSelf 是否是其他人查看
     *                     初始化时传入参数
     */
    public QuestionDetailAdapter(Context context, List<QuestionAnswerObj> list, String id_child, String id_driver, boolean isAccept, boolean isDirverSelf) {
        this.context = context;
        this.list = list;
        this.mId_child = id_child;
        this.mId_driver = id_driver;
        this.mIsAccept = isAccept;
        this.mIsDirverSelf = isDirverSelf;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int location) {
        return list.get(location);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final QuestionAnswerObj obj = list.get(position);
        //final String question_id = obj.getId_question();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question_detail, null);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iv_renzheng = (ImageView) convertView.findViewById(R.id.iv_renzheng);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);

            holder.tv_answer = (KakuMultiLineTextView) convertView.findViewById(R.id.tv_answer);

            holder.rela_total_answer = (RelativeLayout) convertView.findViewById(R.id.rela_total_answer);
            holder.tv_total_answer = (TextView) convertView.findViewById(R.id.tv_total_answer);
            holder.view_total_answer_top = convertView.findViewById(R.id.view_total_answer_top);
            holder.tv_answer_time = (TextView) convertView.findViewById(R.id.tv_answer_time);

            holder.iv_jxzx = (ImageView) convertView.findViewById(R.id.iv_jxzx);
            holder.iv_cnhd = (ImageView) convertView.findViewById(R.id.iv_cnhd);

            holder.lv_comment = (ListView) convertView.findViewById(R.id.lv_comment);

            holder.iv_accept = (ImageView) convertView.findViewById(R.id.iv_accept);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //加载用户头像
        BitmapUtil.getInstance(context).download(holder.iv_icon, KaKuApplication.qian_zhui + obj.getHead_user());
        holder.tv_name.setText(obj.getName_user());
        //todo 认证信息 holder.iv_renzheng;
        //todo 认证信息 holder.tv_title;
        //问题详情
        holder.tv_answer.setText(obj.getContent());

        if (Integer.parseInt(obj.getCount()) > 2) {
            holder.rela_total_answer.setVisibility(View.VISIBLE);
            holder.view_total_answer_top.setVisibility(View.VISIBLE);
            /*holder.rela_total_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*todo 跳转到对话页面*//*
                    LogUtil.showShortToast(context, "跳转到对话页面");
                }
            });*/
            holder.tv_total_answer.setText("总共有" + obj.getCount() + "条对话");
        } else {
            holder.rela_total_answer.setVisibility(View.GONE);
            holder.view_total_answer_top.setVisibility(View.GONE);
        }

        holder.tv_answer_time.setText(obj.getTime_create());

        if (mIsDirverSelf) {

            if (mIsAccept) {
                holder.iv_cnhd.setVisibility(View.GONE);
                holder.iv_jxzx.setVisibility(View.GONE);
            } else {
                holder.iv_jxzx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()){
                            return;
                        }
                        gotoTalkActivity(obj);
                    }
                });

                holder.iv_cnhd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.Many()){
                            return;
                        }
                        //LogUtil.showShortToast(context, "采纳回答");
                        mId_child = obj.getId_question();
                        transaction.acceptAnswer(obj.getId_question());
                    }
                });
            }


        } else {
            holder.iv_jxzx.setVisibility(View.GONE);
            holder.iv_cnhd.setVisibility(View.GONE);
        }

        QuestionListAdapter adapter = new QuestionListAdapter(obj.getQuestion_list());
        holder.lv_comment.setOverScrollMode(View.OVER_SCROLL_NEVER);
        holder.lv_comment.setAdapter(adapter);
        holder.lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoTalkActivity(obj);

            }
        });
        setListViewHeightBasedOnChildren(holder.lv_comment);
        //判断是否为采纳答案
        if (mIsAccept) {
            if (obj.getId_question().equals(mId_child)) {
                holder.iv_accept.setVisibility(View.VISIBLE);
            } else {
                holder.iv_accept.setVisibility(View.GONE);
            }
        } else {
            holder.iv_accept.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void gotoTalkActivity(QuestionAnswerObj obj) {
        Intent intent = new Intent(context, TalkDetailActivity.class);

        intent.putExtra("sub_id_question", obj.getId_question());
        intent.putExtra("header_user", obj.getHead_user());
        intent.putExtra("name_user", obj.getName_user());
        intent.putExtra("secondItem", obj);

        context.startActivity(intent);
    }

    public interface ILogicTransaction {

        void acceptAnswer(String id_question2);
    }

    private ILogicTransaction transaction;

    public void setLogicTransaction(ILogicTransaction transaction) {
        this.transaction = transaction;
    }

    public class ViewHolder {

        public ImageView iv_icon;//头像
        public TextView tv_name;// 名称
        public ImageView iv_renzheng;//认证
        public TextView tv_title;// 头衔

        public KakuMultiLineTextView tv_answer;

        public RelativeLayout rela_total_answer;//回答条数
        public TextView tv_total_answer;
        public View view_total_answer_top;

        public TextView tv_answer_time;//回答时间
        public ImageView iv_jxzx, iv_cnhd;


        public ListView lv_comment;

        public ImageView iv_accept;

    }


    class QuestionListAdapter extends BaseAdapter {

        private List<QuestionAnswerItemObj> questionItemList = new ArrayList<>();
        private ItemViewHolder itemHolder = new ItemViewHolder();

        public QuestionListAdapter(List<QuestionAnswerItemObj> questionItemList) {
            if (questionItemList != null) {
                //只加载两条数据
                if (questionItemList.size() <= 2) {
                    this.questionItemList = questionItemList;
                } else if (questionItemList.size() > 2) {
                    this.questionItemList.add(questionItemList.get(0));
                    this.questionItemList.add(questionItemList.get(1));
                }
            } else {
                /*todo 无数据处理*/
            }

        }

        @Override
        public int getCount() {
            return questionItemList.size();
        }

        @Override
        public Object getItem(int location) {
            return questionItemList.get(location);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            QuestionAnswerItemObj obj = questionItemList.get(position);

            if (convertView == null) {
                itemHolder = new ItemViewHolder();
                if (obj.getId_driver().equals(mId_driver)) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_question_detail_question, null);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_question_detail_answer, null);
                }

                itemHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_item_title);
                itemHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            itemHolder.tv_content.setText(obj.getContent());
            return convertView;
        }


    }

    public class ItemViewHolder {


        public TextView tv_title;// 名称

        public TextView tv_content;// 头衔


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
        //params.height += 5;

        listView.setLayoutParams(params);
    }
}
