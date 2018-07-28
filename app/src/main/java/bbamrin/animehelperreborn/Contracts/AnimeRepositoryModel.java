package bbamrin.animehelperreborn.Contracts;

import android.app.Application;

import java.util.ArrayList;


import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;

public abstract class AnimeRepositoryModel extends Application {
    static AnimeRepositoryModel model;

    public abstract void downloadNewAnimes(ArrayList<Genre> genres, String page, String limit);
    public abstract ArrayList<AnimeModel> getAnimeList(ArrayList<Genre> genres);
    static AnimeRepositoryModel getInstance(){
        return model;
    }
    public abstract void setPresenter(ResultContract.Presenter t);
    public abstract ArrayList<Genre> getGenresList();
    public abstract void stopObserving();
    public abstract int getLastPage(ArrayList<Genre> genres);
    public abstract void onPresenterDestroy( );
}
