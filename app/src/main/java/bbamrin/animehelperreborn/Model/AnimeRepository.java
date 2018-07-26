package bbamrin.animehelperreborn.Model;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private Map<ArrayList<Genre>,Integer> mLastPagesMap;
    private Disposable disposable;
    private Map<ArrayList<Genre>,ArrayList<AnimeModel>> mAnimeMap;
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
        mAnimeMap = new HashMap<>();
        mLastPagesMap = new HashMap<>();
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
    public void downloadNewAnimes(final ArrayList<Genre> genres, final String page, String limit) {
        animeModelsList = new ArrayList<>();
        if (!mAnimeMap.containsKey(genres)){
            mAnimeMap.put(genres,new ArrayList<AnimeModel>());
        }

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
                        mAnimeMap.put(genres,Utils.addOnlyNewData(mAnimeMap.get(genres),animeModels));
                        mLastPagesMap.put(genres,Integer.parseInt(page));
                        if (mResultPresenter != null) {
                            mResultPresenter.notifyAnimesReceived(mAnimeMap.get(genres));
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
    public ArrayList<AnimeModel> getAnimeList(ArrayList<Genre> genres) {
        return mAnimeMap.get(genres);
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
    public int getLastPage(ArrayList<Genre> genres) {
        return mLastPagesMap.get(genres);
    }


    @Override
    public void onPresenterDestroy() {
        this.mResultPresenter = null;
        stopObserving();
    }
}

