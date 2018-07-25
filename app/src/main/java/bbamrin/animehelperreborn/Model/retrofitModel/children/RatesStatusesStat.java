package bbamrin.animehelperreborn.Model.retrofitModel.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RatesStatusesStat implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }



    protected RatesStatusesStat(Parcel in) {
        name = in.readString();
        value = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (value == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(value);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RatesStatusesStat> CREATOR = new Parcelable.Creator<RatesStatusesStat>() {
        @Override
        public RatesStatusesStat createFromParcel(Parcel in) {
            return new RatesStatusesStat(in);
        }

        @Override
        public RatesStatusesStat[] newArray(int size) {
            return new RatesStatusesStat[size];
        }
    };
}