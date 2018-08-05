package bbamrin.animehelperreborn.Presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.AnimeRepositoryModel;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.AnimeRepository;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.View.InnerAnime.InnerAnimeFragment;
import bbamrin.animehelperreborn.View.ResultFragment;
import bbamrin.animehelperreborn.utils.FragmentUtils;

public class ResultViewPresenter implements ResultContract.Presenter {
    private ResultContract.View mView;
    private AnimeRepositoryModel mRepository;
    private ArrayList<AnimeModel> mAnimeList;

    public ResultViewPresenter(ResultContract.View mView) {
        this.mView = mView;
        mRepository = AnimeRepository.getInstance();
        mRepository.setResultPresenter(this);
        ConnectivityManager cm =
                (ConnectivityManager) ((ResultFragment) mView).getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        mView.showDownloadAnimation();
        mView.hideRecycler();
        Log.d(StaticVars.LOG_TAG,"mView rLayout state" + ((ResultFragment)mView).getRefreshLayout());
        if (activeNetwork != null) {
            if (activeNetwork.isAvailable()) {
                if (mRepository.getAnimeList(mView.getGenres()) == null) {
                    mRepository.downloadNewAnimes(mView.getGenres(), "1", "50");
                    Log.d(StaticVars.LOG_TAG, "mRepository.getAnimeList(mView.getGenres()) == null)");
                } else {
                    mView.stopDownloadAnimation();
                    mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()));
                    mView.showRecycler();
                }
            } else {
                if (mRepository.getAnimeList(mView.getGenres()) != null) {
                    mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()) );
                } else {
                    mView.showRecycler();
                    mView.stopDownloadAnimation();
                    //maybe later it will be expanded by passing the type of an error, but now it assuming that there is only a network connectivity error
                    mView.showErrorNotification();
                }
            }
        } else {
            if (mRepository.getAnimeList(mView.getGenres()) != null) {
                mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()) );
            } else {
                mView.showRecycler();
                mView.stopDownloadAnimation();
                mView.showErrorNotification();
            }
        }


    }

    @Override
    public void onGenresReceived(ArrayList<Genre> genres) {
        //in the future page and limit will be added with variables
        //mAnimeList = mRepository.getNewAnimeList(genres,"1","50");

    }

    @Override
    public void nothingMore() {
        mView.showNothingMoreNotification();
    }

    @Override
    public void loadMoreAnimes() {

        ConnectivityManager cm =
                (ConnectivityManager) ((ResultFragment) mView).getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.isAvailable()) {
                mRepository.downloadNewAnimes(mView.getGenres(), (mRepository.getLastPage(mView.getGenres()) + 1) + "", "50");
            } else {
                mView.showErrorNotification();
            }
        } else {
            mView.showErrorNotification();
        }

    }

    @Override
    public void onViewRefresh() {
        ConnectivityManager cm =
                (ConnectivityManager) ((ResultFragment) mView).getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.isAvailable()) {
                if (mRepository.getAnimeList(mView.getGenres())!=null){
                    mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()));
                    mView.stopRefreshingAnimation();
                } else {
                    mRepository.downloadNewAnimes(mView.getGenres(), (mRepository.getLastPage(mView.getGenres()))+ "", "50");
                }

            } else {
                mView.stopRefreshingAnimation();
                mView.showErrorNotification();
            }
        } else {
            mView.stopRefreshingAnimation();
            mView.showErrorNotification();
        }
    }

    @Override
    public void onAnimeClick(View view, int position) {
        if (mView.getAnimes().size()!=0){
            AnimeModel animeModel =  mView.getAnimes().get(position);
            Log.d(StaticVars.LOG_TAG,"anime description in resultPresenter: " + animeModel.getDescription());
            InnerAnimeFragment fragment = InnerAnimeFragment.newInstance(animeModel,StaticVars.ANIME_MODEL);
            FragmentUtils.replaceSupportFragment(((ResultFragment)mView).getFragmentManager(),fragment, R.id.mainActivityId);
        }


    }

    @Override
    public void notifyAnimesReceived(ArrayList<AnimeModel> animeModels) {
        mView.showAnimeList(mRepository.getAnimeList(mView.getGenres()));
        mView.stopRefreshingAnimation();
        mView.showRecycler();
        Log.d(StaticVars.LOG_TAG," notifyAnimesReceived");
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
