package bbamrin.animehelperreborn.Contracts;

import java.util.ArrayList;

import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.BaseView;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.children.Image;

public interface InnerAnimeContract  {
    public interface View extends BaseView<ResultContract.Presenter> {
        public void setMainInfo(AnimeModel animeModel);
        public void setScreenshots(ArrayList<AnimeScreenshot> screenshots);
        public void setRelated(ArrayList<Related> relatedList);
        public AnimeModel getAnimeModel();

    }

    public interface Presenter extends BasePresenter {
        public void notifyAnimeDownloaded(AnimeModel animeModel);
        public void notifyImagesDownloaded(ArrayList<AnimeScreenshot> screens);
        public void notifyRelatedDownloaded(ArrayList<Related> related);

    }

}
