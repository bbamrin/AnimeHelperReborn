package bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnimeScreenshot {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("preview")
    @Expose
    private String preview;

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

}
