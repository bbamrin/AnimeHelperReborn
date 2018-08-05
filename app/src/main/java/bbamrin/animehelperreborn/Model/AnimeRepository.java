package bbamrin.animehelperreborn.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import bbamrin.animehelperreborn.Contracts.AnimeRepositoryModel;
import bbamrin.animehelperreborn.Contracts.InnerAnimeContract;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.internalModel.Anime;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.children.Image;
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
    private InnerAnimeContract.Presenter mInnerAnimePresenter;
    private Map<ArrayList<Genre>,Integer> mLastPagesMap;
    private Disposable disposable;
    private Map<AnimeModel,ArrayList<AnimeScreenshot>> animeToAnimeScreenshotsMatch;
    private Map<AnimeModel,ArrayList<Related>> animeToRelatedMatch;
    private Map<AnimeModel,AnimeModel> animeModelToFullAnimeModelMatching;
    private Map<ArrayList<Genre>,ArrayList<AnimeModel>> mAnimeMap;
    private ArrayList<AnimeModel> animeModelsList;
    private Observable<ArrayList<AnimeModel>> receivedAnimes;


    public static AnimeRepository getInstance() {
        return singleton;
    }

    @Override
    public void setInnerAnimePresenter(InnerAnimeContract.Presenter t) {
        mInnerAnimePresenter = t;
    }

    @Override
    public void setResultPresenter(ResultContract.Presenter o) {
        this.mResultPresenter = (ResultContract.Presenter) o;
    }

    @Override
    public void downloadAnime(final AnimeModel anime ) {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(StaticVars.BASE_SHIKIMORI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        API = retrofit.create(ShikimoriAPI.class);
        //will handle the  disposable later
        API.getAnime(anime.getId().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<AnimeModel>() {
                    @Override
                    public void onNext(AnimeModel animeModel) {
                        animeModelToFullAnimeModelMatching.put(anime,animeModel);
                        if (mInnerAnimePresenter!=null){
                            mInnerAnimePresenter.notifyAnimeDownloaded(animeModel);
                            Log.d(StaticVars.LOG_TAG,"full anime downloaded, presenter is notified");
                        }
                        Log.d(StaticVars.LOG_TAG,"full anime downloaded");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void downloadImages(final AnimeModel anime) {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(StaticVars.BASE_SHIKIMORI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        API = retrofit.create(ShikimoriAPI.class);
        //will handle the  disposable later
        API.getAnimeScreenshots(anime.getId().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ArrayList<AnimeScreenshot>>() {
            @Override
            public void onNext(ArrayList<AnimeScreenshot> animeScreenshots) {
                Log.d(StaticVars.LOG_TAG,"screens size: " + animeScreenshots.size());
                if (animeToAnimeScreenshotsMatch.size()>=0){
                    animeToAnimeScreenshotsMatch.put(anime,animeScreenshots);
                    if (mInnerAnimePresenter!=null){
                        Log.d(StaticVars.LOG_TAG,"presenter not null");
                        mInnerAnimePresenter.notifyImagesDownloaded(animeScreenshots);
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d(StaticVars.LOG_TAG,"some troubles with screen downloading");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void downloadRelated(final AnimeModel anime) {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(StaticVars.BASE_SHIKIMORI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        API = retrofit.create(ShikimoriAPI.class);
        //will handle the  disposable later
        API.getRelatedAnimes(anime.getId().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<ArrayList<Related>>() {
            @Override
            public void onNext(ArrayList<Related> relateds) {
                animeToRelatedMatch.put(anime,relateds);
                if (mInnerAnimePresenter!=null){
                    Log.d(StaticVars.LOG_TAG,"presenter not null rel");
                    mInnerAnimePresenter.notifyRelatedDownloaded(relateds);
                }
                Log.d(StaticVars.LOG_TAG,"related downloaded");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
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
        animeToAnimeScreenshotsMatch = new HashMap<>();
        animeToRelatedMatch = new HashMap<>();
        animeModelToFullAnimeModelMatching= new HashMap<>();
        singleton = this;
        Log.d(StaticVars.LOG_TAG, "application onCreate");
    }


    @Override
    public void downloadNewAnimes(final ArrayList<Genre> genres, final String page, String limit) {
        animeModelsList = new ArrayList<>();
        if (!mAnimeMap.containsKey(genres)){
            mAnimeMap.put(genres,new ArrayList<AnimeModel>());
        }

        retrofit = new Retrofit
                .Builder()
                .baseUrl(StaticVars.BASE_SHIKIMORI_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        API = retrofit.create(ShikimoriAPI.class);

        Log.d(StaticVars.LOG_TAG, "getAnimeList");
        Log.d(StaticVars.LOG_TAG, Utils.generateGenresQueryForShikimori(genres));
        receivedAnimes = API.getAnimeListWithGenres(Utils.generateGenresQueryForShikimori(genres), limit, page);
        disposable = receivedAnimes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<AnimeModel>>() {
                    @Override
                    public void onNext(ArrayList<AnimeModel> animeModels) {
                        if (animeModels.size()!=0){
                            mAnimeMap.put(genres,Utils.addOnlyNewData(mAnimeMap.get(genres),animeModels));
                            mLastPagesMap.put(genres,Integer.parseInt(page));
                            if (mResultPresenter != null) {
                                if (animeModels.size()==0){
                                    mResultPresenter.nothingMore();
                                } else {
                                    mResultPresenter.notifyAnimesReceived(mAnimeMap.get(genres));
                                }

                            }
                        } else {
                            mResultPresenter.nothingMore();
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(StaticVars.LOG_TAG, "onError: ");
                        e.printStackTrace();

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

    @Override
    public AnimeModel getFullAnime(AnimeModel animeModel) {
        return animeModelToFullAnimeModelMatching.get(animeModel);
    }

    @Override
    public ArrayList<AnimeScreenshot> getScreenshots(AnimeModel animeModel) {
        return animeToAnimeScreenshotsMatch.get(animeModel);
    }

    @Override
    public ArrayList<Related> getRelatedList(AnimeModel animeModel) {
        return animeToRelatedMatch.get(animeModel);
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
        if (mLastPagesMap.get(genres)!=null){
            return mLastPagesMap.get(genres);
        } return 1;
    }


    @Override
    public void onPresenterDestroy() {
        this.mResultPresenter = null;
        stopObserving();
    }
}

