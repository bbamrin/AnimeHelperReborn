package bbamrin.animehelperreborn.Contracts;

import android.app.Application;

import java.util.ArrayList;


import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;

public abstract class AnimeRepositoryModel extends Application {
    static AnimeRepositoryModel model;

    public abstract void downloadNewAnimes(ArrayList<Genre> genres, String page, String limit);
    public abstract ArrayList<AnimeModel> getAnimeList(ArrayList<Genre> genres);
    public abstract AnimeModel getFullAnime(AnimeModel animeModel);
    public abstract ArrayList<AnimeScreenshot> getScreenshots(AnimeModel animeModel);
    public abstract ArrayList<Related> getRelatedList(AnimeModel animeModel);

    static AnimeRepositoryModel getInstance(){
        return model;
    }
    public abstract void setInnerAnimePresenter(InnerAnimeContract.Presenter t);
    public abstract void setResultPresenter(ResultContract.Presenter t);
    public abstract void downloadAnime(AnimeModel animeModel);
    public abstract void downloadImages(AnimeModel animeModel);
    public abstract void downloadRelated(AnimeModel animeModel);

    public abstract ArrayList<Genre> getGenresList();
    public abstract void stopObserving();
    public abstract int getLastPage(ArrayList<Genre> genres);
    public abstract void onPresenterDestroy( );
}
