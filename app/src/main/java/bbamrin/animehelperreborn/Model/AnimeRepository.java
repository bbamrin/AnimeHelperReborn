package bbamrin.animehelperreborn.Model;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;


import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.Contracts.AnimeRepositoryModel;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import bbamrin.animehelperreborn.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeRepository extends AnimeRepositoryModel {
    private static AnimeRepository singleton;
    private Retrofit retrofit;
    private ShikimoriAPI API;
    private Disposable disposable;
    private ArrayList<AnimeModel> animeModelsList;
    private Observable<ArrayList<AnimeModel>> receivedAnimes;


    public static AnimeRepository getInstance() {
        return singleton;
    }

    @Override
    public void setPresenter(ResultContract.Presenter o) {
        this.mResultPresenter = (ResultContract.Presenter) o;
    }

    ResultContract.Presenter mResultPresenter;

    public Observable<ArrayList<AnimeModel>> getReceivedAnimes() {
        return receivedAnimes;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit
                .Builder()
                .baseUrl(StaticVars.BASE_SHIKIMORI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        API = retrofit.create(ShikimoriAPI.class);
        singleton = this;
        Log.d(StaticVars.LOG_TAG, "application onCreate");
    }


    @Override
    public void downloadNewAnimes(ArrayList<Genre> genres, String page, String limit) {
        animeModelsList = new ArrayList<>();

        Log.d(StaticVars.LOG_TAG, "getAnimeList");
        Log.d(StaticVars.LOG_TAG, Utils.generateGenresQueryForShikimori(genres));
        receivedAnimes = API.getAnimeListWithGenres(Utils.generateGenresQueryForShikimori(genres), limit, page);
        disposable = receivedAnimes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<AnimeModel>>() {
                    @Override
                    public void onNext(ArrayList<AnimeModel> animeModels) {
                        Log.d(StaticVars.LOG_TAG,"network request ended");
                        animeModelsList = Utils.addOnlyNewData(animeModelsList,animeModels);
                        if (mResultPresenter != null) {
                            mResultPresenter.notifyAnimesReceived(animeModels);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(StaticVars.LOG_TAG, "onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public ArrayList<AnimeModel> getAnimeList() {
        return animeModelsList;
    }


    //not implemented yet
    @Override
    public ArrayList<Genre> getGenresList() {
        return null;
    }

    @Override
    public void stopObserving() {
        if (disposable != null) {
            disposable.dispose();
        }
    }



    @Override
    public void onPresenterDestroy() {
        this.mResultPresenter = null;
        stopObserving();
    }
}

