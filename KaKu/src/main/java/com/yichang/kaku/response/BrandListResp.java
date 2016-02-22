package com.yichang.kaku.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.yichang.kaku.tools.PinyinUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小苏 on 2015-08-22 11：44.
 * Description：
 */
public class BrandListResp extends BaseResp implements Parcelable {

    /**
     * msg : 获取成功
     * res : 0
     * item : [{"id_brand":"1001","image_brand":"brand/1437113892.png","name_brand":"大运汽车"},{"id_brand":"1018","image_brand":"brand/1437114183.png","name_brand":"中国重汽"}]
     */
    public List<ItemEntity> list;

    public static class ItemEntity implements Comparable<ItemEntity>, Parcelable {
        /**
         * id_brand : 1001
         * image_brand : brand/1437113892.png
         * name_brand : 大运汽车
         */
        public String id_brand;
        public String image_brand;
        public String name_brand;

        public String pinyin;

        /**
         * 获取名称的拼音
         *
         * @return
         */
        public String getPinyin() {
            return PinyinUtil.getPinyin(name_brand);
        }

        @Override
        public boolean equals(Object obj) {
            ItemEntity another = null;
            if (obj instanceof ItemEntity) {
                another = (ItemEntity) obj;
                return TextUtils.equals(this.id_brand, another.id_brand);
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(ItemEntity another) {
            return this.getPinyin().compareToIgnoreCase(another.getPinyin());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id_brand);
            dest.writeString(this.image_brand);
            dest.writeString(this.name_brand);
            dest.writeString(this.pinyin);
        }

        public ItemEntity() {
        }

        protected ItemEntity(Parcel in) {
            this.id_brand = in.readString();
            this.image_brand = in.readString();
            this.name_brand = in.readString();
            this.pinyin = in.readString();
        }

        public static final Creator<ItemEntity> CREATOR = new Creator<ItemEntity>() {
            public ItemEntity createFromParcel(Parcel source) {
                return new ItemEntity(source);
            }

            public ItemEntity[] newArray(int size) {
                return new ItemEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.res);
        dest.writeString(this.msg);
        dest.writeList(this.list);
    }

    public BrandListResp() {
    }

    protected BrandListResp(Parcel in) {
        this.res = in.readString();
        this.msg = in.readString();
        this.list = new ArrayList<ItemEntity>();
        in.readList(this.list, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<BrandListResp> CREATOR = new Parcelable.Creator<BrandListResp>() {
        public BrandListResp createFromParcel(Parcel source) {
            return new BrandListResp(source);
        }

        public BrandListResp[] newArray(int size) {
            return new BrandListResp[size];
        }
    };
}
