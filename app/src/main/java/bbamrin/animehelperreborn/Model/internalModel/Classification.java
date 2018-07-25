package bbamrin.animehelperreborn.Model.internalModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import bbamrin.animehelperreborn.utils.Utils;

public class Classification implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Classification> CREATOR = new Parcelable.Creator<Classification>() {
        @Override
        public Classification createFromParcel(Parcel in) {
            return new Classification(in);
        }

        @Override
        public Classification[] newArray(int size) {
            return new Classification[size];
        }
    };
    private String classificationName;
    private String imageAddress;
    private ArrayList<Genre> genreList;

    public Classification(String classificationName, String imageAddress, @NonNull ArrayList<Genre> genreList) {
        this.classificationName = classificationName;
        this.imageAddress = imageAddress;
        this.genreList = genreList;
    }
    public Classification(String classificationName, @NonNull ArrayList<Genre> genreList) {
        this.classificationName = classificationName;
        this.genreList = genreList;
    }

    protected Classification(Parcel in) {
        classificationName = in.readString();
        imageAddress = in.readString();
        if (in.readByte() == 0x01) {
            genreList = new ArrayList<Genre>();
            in.readList(genreList, Genre.class.getClassLoader());
        } else {
            genreList = null;
        }
    }

    public ArrayList<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<Genre> genreList) {
        this.genreList = genreList;
    }

    public String getChosenGenreText() {
        return Utils.generateChosenGenresString(this.genreList);
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String text) {
        this.classificationName = text;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(classificationName);
        dest.writeString(imageAddress);
        if (genreList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genreList);
        }
    }
}