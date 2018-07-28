
package bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;

public class Related {

    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("relation_russian")
    @Expose
    private String relationRussian;
    @SerializedName("anime")
    @Expose
    private AnimeModel anime;
    @SerializedName("manga")
    @Expose
    private Object manga;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelationRussian() {
        return relationRussian;
    }

    public void setRelationRussian(String relationRussian) {
        this.relationRussian = relationRussian;
    }

    public AnimeModel getAnime() {
        return anime;
    }

    public void setAnime(AnimeModel anime) {
        this.anime = anime;
    }

    public Object getManga() {
        return manga;
    }

    public void setManga(Object manga) {
        this.manga = manga;
    }

}
