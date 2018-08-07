package bbamrin.animehelperreborn.Presenter;

import android.util.Log;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.InnerAnimeContract;
import bbamrin.animehelperreborn.Model.AnimeRepository;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.View.InnerAnime.InnerAnimeFragment;
import bbamrin.animehelperreborn.View.ResultFragment;
import bbamrin.animehelperreborn.utils.FragmentUtils;

public class InnerAnimePresenter implements InnerAnimeContract.Presenter {
    InnerAnimeContract.View mView;
    AnimeRepository mRepository;
    AnimeModel mAnimeModel;

    public InnerAnimePresenter(InnerAnimeContract.View mView) {
        this.mView = mView;
        mAnimeModel = mView.getAnimeModel();
        this.mRepository = AnimeRepository.getInstance();
        mRepository.setInnerAnimePresenter(this);
        if (mRepository.getFullAnime(mAnimeModel) == null){
            mRepository.downloadAnime(mAnimeModel);
        } else {
            mView.setMainInfo(mRepository.getFullAnime(mAnimeModel));
        }
        if (mRepository.getRelatedList(mAnimeModel) == null){
            mRepository.downloadRelated(mAnimeModel);
        }
        else {
            mView.setRelated(mRepository.getRelatedList(mAnimeModel));
        }
        if (mRepository.getScreenshots(mAnimeModel) == null){
            mRepository.downloadImages(mAnimeModel);
        }
        else {
            mView.setScreenshots(mRepository.getScreenshots(mAnimeModel));
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDetachView() {

    }



    @Override
    public void notifyAnimeDownloaded(AnimeModel animeModel) {
        mView.setMainInfo(animeModel);
    }

    @Override
    public void notifyImagesDownloaded(ArrayList<AnimeScreenshot> screens) {
        mView.setScreenshots(screens);
    }

    @Override
    public void notifyRelatedDownloaded(ArrayList<Related> related) {
        mView.setRelated(related);
    }

    @Override
    public void onRelatedItemClick(AnimeModel animeModel) {
        InnerAnimeFragment fragment = InnerAnimeFragment.newInstance(animeModel,StaticVars.ANIME_MODEL);
        FragmentUtils.replaceSupportFragment(((InnerAnimeFragment)mView).getFragmentManager(),fragment, R.id.mainActivityId);
    }
}
