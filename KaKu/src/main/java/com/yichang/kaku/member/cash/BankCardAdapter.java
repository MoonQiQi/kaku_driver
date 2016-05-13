package com.yichang.kaku.member.cash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.BankCardObj;
import com.yichang.kaku.request.AddrMorenReq;
import com.yichang.kaku.response.AddrMorenResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

public class BankCardAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BankCardObj> list;
    private Context mContext;

    public BankCardAdapter(Context context, List<BankCardObj> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.mContext = context;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list != null && !list.isEmpty() ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final BankCardObj obj = list.get(position);
        if (obj == null) {
            return convertView;
        }
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_bank_card, null);
            holder.tv_bank_name = (TextView) convertView.findViewById(R.id.tv_bank_name);
            holder.tv_bank_default = (TextView) convertView.findViewById(R.id.tv_bank_default);
            holder.tv_bank_card_no = (TextView) convertView.findViewById(R.id.tv_bank_card_no);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_bank_name.setText(obj.getName_bank());

        holder.tv_bank_card_no.setText(obj.getCard_bank());
        if ("Y".equals(obj.getFlag_default())) {
            holder.tv_bank_default.setText("默认");
        } else {
            holder.tv_bank_default.setText("");
        }


        return convertView;
    }

    /*private String formatBankCardNo(String card_bank) {

        String str="";
        char[] bankCardNo = card_bank.toCharArray();
        List<Character> newCardNo = new ArrayList<>();

        for (int i = 0; i < bankCardNo.length; i++) {

            if (i < 4) {
                newCardNo.add(bankCardNo[i]);
            } else if (bankCardNo.length - i <= 4) {
                newCardNo.add(bankCardNo[i]);
            } else {
                newCardNo.add('*');
            }

            if ((i+1)% 4 == 0) {
                newCardNo.add(' ');
            }


        }

        for (Character c : newCardNo) {
            str += String.valueOf(c);

        }

        return str;
    }
*/
    class ViewHolder {


        TextView tv_bank_name;
        TextView tv_bank_default;
        TextView tv_bank_card_no;


    }


    private ShowProgress showProgress;

    public void setShowProgress(ShowProgress showProgress) {
        this.showProgress = showProgress;

    }

    public interface ShowProgress {

        public abstract void showDialog();

        public abstract void stopDialog();
    }

    ;


    public void MoRen(String id_addr, final int position) {

        Utils.NoNet(mContext);
        AddrMorenReq req = new AddrMorenReq();
        req.code = "10017";
        req.id_addr = id_addr;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.MorenAddr(req, new KakuResponseListener<AddrMorenResp>(mContext, AddrMorenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morenaddr res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setFlag_default("N");
                        }
                        list.get(position).setFlag_default("Y");
                        notifyDataSetChanged();
                    }
                    LogUtil.showShortToast(mContext, t.msg);
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }
}