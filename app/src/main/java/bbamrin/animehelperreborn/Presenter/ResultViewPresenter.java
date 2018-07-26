package bbamrin.animehelperreborn.Presenter;

import android.util.Log;

import java.util.ArrayList;


import bbamrin.animehelperreborn.Contracts.AnimeRepositoryModel;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.AnimeRepository;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import io.reactivex.disposables.Disposable;

public class ResultViewPresenter implements ResultContract.Presenter {
    private ResultContract.View mView;
    private AnimeRepositoryModel mRepository;
    private ArrayList<AnimeModel> mAnimeList;
    public ResultViewPresenter(ResultContract.View mView) {
        this.mView = mView;
        mRepository = AnimeRepository.getInstance();
        mRepository.setPresenter(this);
        if (mRepository.getAnimeList(mView.getGenres()) == null){
            mRepository.downloadNewAnimes(mView.getGenres(),"1","50");
            Log.d(StaticVars.LOG_TAG," mRepository animeList is null");
        } else {
            Log.d(StaticVars.LOG_TAG," mRepository animeList is not null");
            mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()));
        }

    }

    @Override
    public void onGenresReceived(ArrayList<Genre> genres) {
        //in the future page and limit will be added with variables
        //mAnimeList = mRepository.getNewAnimeList(genres,"1","50");

    }

    @Override
    public void loadMoreAnimes() {
        mRepository.downloadNewAnimes(mView.getGenres(),(mRepository.getLastPage(mView.getGenres())+1)+"","50");
    }

    @Override
    public void notifyAnimesReceived(ArrayList<AnimeModel> animeModels) {
        mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()));
    }


    @Override
    public void start() {

    }

    @Override
    public void onDestroyView() {
        mRepository = null;
        mAnimeList = null;
        mView = null;
        mRepository.onPresenterDestroy();
    }

    @Override
    public void onDetachView() {

    }
}
