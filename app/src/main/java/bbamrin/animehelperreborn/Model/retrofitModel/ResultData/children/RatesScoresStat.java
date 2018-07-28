package bbamrin.animehelperreborn.Model.retrofitModel.ResultData.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RatesScoresStat implements Parcelable {

    @SerializedName("name")
    @Expose
    private Integer name;
    @SerializedName("value")
    @Expose
    private Integer value;

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }



    protected RatesScoresStat(Parcel in) {
        name = in.readByte() == 0x00 ? null : in.readInt();
        value = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (name == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(name);
        }
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RatesScoresStat> CREATOR = new Parcelable.Creator<RatesScoresStat>() {
        @Override
        public RatesScoresStat createFromParcel(Parcel in) {
            return new RatesScoresStat(in);
        }

        @Override
        public RatesScoresStat[] newArray(int size) {
            return new RatesScoresStat[size];
        }
    };
}