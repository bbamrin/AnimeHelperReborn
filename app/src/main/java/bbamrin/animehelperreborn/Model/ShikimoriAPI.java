package bbamrin.animehelperreborn.Model;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShikimoriAPI {
    @GET("api/animes")
    public Observable<ArrayList<AnimeModel>> getAnimeListWithGenres(@Query("genre") String genres, @Query("limit") String limit, @Query("page") String page);

}
