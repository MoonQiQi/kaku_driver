package com.yichang.kaku.response;

import com.yichang.kaku.obj.IllegalInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiaosu on 2015/11/10.
 */
public class IllegalQueryResp extends BaseResp implements Serializable {

    public String degree;
    public String count;
    public String data_count;
    public ArrayList<IllegalInfo> data;

    /*public static class IllegalInfo implements Parcelable {
        public String time;// 时间
        public String street;// 地点
        public String reason;// 原因
        public String degree;// 分数
        public String count;// 金钱

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.time);
            dest.writeString(this.street);
            dest.writeString(this.reason);
            dest.writeString(this.degree);
            dest.writeString(this.count);
        }

        public IllegalInfo() {
        }

        protected IllegalInfo(Parcel in) {
            this.time = in.readString();
            this.street = in.readString();
            this.reason = in.readString();
            this.degree = in.readString();
            this.count = in.readString();
        }

        public static final Creator<IllegalInfo> CREATOR = new Creator<IllegalInfo>() {
            public IllegalInfo createFromParcel(Parcel source) {
                return new IllegalInfo(source);
            }

            public IllegalInfo[] newArray(int size) {
                return new IllegalInfo[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.degree);
        dest.writeString(this.res);
        dest.writeString(this.msg);
        dest.writeString(this.count);
        dest.writeString(this.data_count);
        dest.writeTypedList(data);
    }

    public IllegalQueryResp() {
    }

    protected IllegalQueryResp(Parcel in) {
        this.degree = in.readString();
        this.res = in.readString();
        this.msg = in.readString();
        this.count = in.readString();
        this.data_count = in.readString();
        this.data = in.createTypedArrayList(IllegalInfo.CREATOR);
    }

    public static final Parcelable.Creator<IllegalQueryResp> CREATOR = new Parcelable.Creator<IllegalQueryResp>() {
        public IllegalQueryResp createFromParcel(Parcel source) {
            return new IllegalQueryResp(source);
        }

        public IllegalQueryResp[] newArray(int size) {
            return new IllegalQueryResp[size];
        }
    };*/
}
