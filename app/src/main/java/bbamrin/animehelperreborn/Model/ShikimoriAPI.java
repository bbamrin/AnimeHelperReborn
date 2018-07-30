package bbamrin.animehelperreborn.Model;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShikimoriAPI {
    @GET("api/animes")
    public Observable<ArrayList<AnimeModel>> getAnimeListWithGenres(@Query("genre") String genres, @Query("limit") String limit, @Query("page") String page);

    @GET("api/animes/{animeId}/related")
    public Observable<ArrayList<Related>> getRelatedAnimes(@Path("animeId") String id);


    @GET("api/animes/{animeId}")
    public Observable<AnimeModel> getAnime(@Path("animeId") String id);

    @GET("api/animes/{animeId}/screenshots")
    public Observable<ArrayList<AnimeScreenshot>> getAnimeScreenshots(@Path("animeId") String id);
}
