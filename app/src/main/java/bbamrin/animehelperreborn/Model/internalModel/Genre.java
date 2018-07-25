package bbamrin.animehelperreborn.Model.internalModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
    private String textGenre;
    private boolean isChosen = false;
    private String shikimoriNumber;

    public Genre(String textGenre, boolean isChosen, String shikimoriNumber) {

        this.textGenre = textGenre;
        this.isChosen = isChosen;
        this.shikimoriNumber = shikimoriNumber;
    }

    public Genre(String textGenre) {
        this.textGenre = textGenre;
    }

    public Genre() {
    }

    protected Genre(Parcel in) {
        textGenre = in.readString();
        isChosen = in.readByte() != 0x00;
        shikimoriNumber = in.readString();
    }

    public String getShikimoriNumber() {
        return shikimoriNumber;
    }

    public void setShikimoriNumber(String shikimoriNumber) {
        this.shikimoriNumber = shikimoriNumber;
    }

    public String getTextGenre() {
        return textGenre;
    }

    public void setTextGenre(String textGenre) {
        this.textGenre = textGenre;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public void toggleChoose() {
        isChosen = !isChosen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textGenre);
        dest.writeByte((byte) (isChosen ? 0x01 : 0x00));
        dest.writeString(shikimoriNumber);
    }
}