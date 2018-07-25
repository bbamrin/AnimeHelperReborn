package bbamrin.animehelperreborn.Model.retrofitModel.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Image implements Parcelable {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("x96")
    @Expose
    private String x96;
    @SerializedName("x48")
    @Expose
    private String x48;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getX96() {
        return x96;
    }

    public void setX96(String x96) {
        this.x96 = x96;
    }

    public String getX48() {
        return x48;
    }

    public void setX48(String x48) {
        this.x48 = x48;
    }



    protected Image(Parcel in) {
        original = in.readString();
        preview = in.readString();
        x96 = in.readString();
        x48 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original);
        dest.writeString(preview);
        dest.writeString(x96);
        dest.writeString(x48);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}