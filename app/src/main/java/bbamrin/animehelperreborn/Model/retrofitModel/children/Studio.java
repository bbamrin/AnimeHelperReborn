package bbamrin.animehelperreborn.Model.retrofitModel.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Studio implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("filtered_name")
    @Expose
    private String filteredName;
    @SerializedName("real")
    @Expose
    private String real;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilteredName() {
        return filteredName;
    }

    public void setFilteredName(String filteredName) {
        this.filteredName = filteredName;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    protected Studio(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        filteredName = in.readString();
        real = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(filteredName);
        dest.writeString(real);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Studio> CREATOR = new Parcelable.Creator<Studio>() {
        @Override
        public Studio createFromParcel(Parcel in) {
            return new Studio(in);
        }

        @Override
        public Studio[] newArray(int size) {
            return new Studio[size];
        }
    };
}