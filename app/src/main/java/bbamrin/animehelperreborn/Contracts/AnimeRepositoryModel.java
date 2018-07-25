package bbamrin.animehelperreborn.Contracts;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.Model.AnimeRepository;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import io.reactivex.Observable;

public abstract class AnimeRepositoryModel extends Application {
    static AnimeRepositoryModel model;

    public abstract void downloadNewAnimes(ArrayList<Genre> genres, String page, String limit);
    public abstract ArrayList<AnimeModel> getAnimeList();
    static AnimeRepositoryModel getInstance(){
        return model;
    }
    public abstract void setPresenter(ResultContract.Presenter t);
    public abstract ArrayList<Genre> getGenresList();
    public abstract void stopObserving();
    public abstract void onPresenterDestroy( );
}
