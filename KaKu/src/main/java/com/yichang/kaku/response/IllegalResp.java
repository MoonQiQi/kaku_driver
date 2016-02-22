package com.yichang.kaku.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by xiaosu on 2015/11/10.
 */
public class IllegalResp implements Parcelable {

    public int Code;
    public int res;
    public String Msg;
    /**
     * Cities : [{"CarCodeLen":0,"CarEngineLen":99,"CarNumberPrefix":"京","CarOwnerLen":0,"CityId":11,"CityName":"北京","Name":"北京"}]
     * ProvinceID : 11
     * ProvinceName : 北京
     * ProvincePrefix : 京
     */
    public ArrayList<Province> Data;

    public static class Province implements Parcelable {
        public int ProvinceID;
        public String ProvinceName;
        public String ProvincePrefix;
        public ArrayList<Cities> Cities;

        @Override
        public String toString() {
            return "Province{" +
                    "ProvinceID=" + ProvinceID +
                    ", ProvinceName='" + ProvinceName + '\'' +
                    ", ProvincePrefix='" + ProvincePrefix + '\'' +
                    ", Cities=" + Cities +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.ProvinceID);
            dest.writeString(this.ProvinceName);
            dest.writeString(this.ProvincePrefix);
            dest.writeTypedList(Cities);
        }

        public Province() {
        }

        protected Province(Parcel in) {
            this.ProvinceID = in.readInt();
            this.ProvinceName = in.readString();
            this.ProvincePrefix = in.readString();
            this.Cities = in.createTypedArrayList(IllegalResp.Cities.CREATOR);
        }

        public static final Creator<Province> CREATOR = new Creator<Province>() {
            public Province createFromParcel(Parcel source) {
                return new Province(source);
            }

            public Province[] newArray(int size) {
                return new Province[size];
            }
        };
    }

    public static class Cities implements Parcelable {
        public int CarCodeLen;
        public int CarEngineLen;
        public String CarNumberPrefix;
        public int CarOwnerLen;
        public int CityId;
        public String CityName;
        public String Name;

        @Override
        public String toString() {
            return "Cities{" +
                    "CarCodeLen=" + CarCodeLen +
                    ", CarEngineLen=" + CarEngineLen +
                    ", CarNumberPrefix='" + CarNumberPrefix + '\'' +
                    ", CarOwnerLen=" + CarOwnerLen +
                    ", CityId=" + CityId +
                    ", CityName='" + CityName + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.CarCodeLen);
            dest.writeInt(this.CarEngineLen);
            dest.writeString(this.CarNumberPrefix);
            dest.writeInt(this.CarOwnerLen);
            dest.writeInt(this.CityId);
            dest.writeString(this.CityName);
            dest.writeString(this.Name);
        }

        public Cities() {
        }

        protected Cities(Parcel in) {
            this.CarCodeLen = in.readInt();
            this.CarEngineLen = in.readInt();
            this.CarNumberPrefix = in.readString();
            this.CarOwnerLen = in.readInt();
            this.CityId = in.readInt();
            this.CityName = in.readString();
            this.Name = in.readString();
        }

        public static final Creator<Cities> CREATOR = new Creator<Cities>() {
            public Cities createFromParcel(Parcel source) {
                return new Cities(source);
            }

            public Cities[] newArray(int size) {
                return new Cities[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Code);
        dest.writeString(this.Msg);
        dest.writeTypedList(Data);
    }

    public IllegalResp() {
    }

    protected IllegalResp(Parcel in) {
        this.Code = in.readInt();
        this.Msg = in.readString();
        this.Data = in.createTypedArrayList(Province.CREATOR);
    }

    public static final Parcelable.Creator<IllegalResp> CREATOR = new Parcelable.Creator<IllegalResp>() {
        public IllegalResp createFromParcel(Parcel source) {
            return new IllegalResp(source);
        }

        public IllegalResp[] newArray(int size) {
            return new IllegalResp[size];
        }
    };
}
