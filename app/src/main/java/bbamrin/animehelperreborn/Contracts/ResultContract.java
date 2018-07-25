package bbamrin.animehelperreborn.Contracts;

import java.util.ArrayList;

import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.BaseView;
import bbamrin.animehelperreborn.Model.internalModel.Anime;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;

public interface ResultContract {
    public interface View extends BaseView<Presenter> {
        void showAnimeList(ArrayList<AnimeModel> animes);
        void onLoadMoreClick();
        void refreshList();
        void showErrorNotification();
        void showNothingMoreNotification();
        ArrayList<Genre> getGenres();
        ArrayList<AnimeModel> getAnimes();
    }

    public interface Presenter extends BasePresenter {
        void onGenresReceived(ArrayList<Genre> genres);
        void notifyAnimesReceived(ArrayList<AnimeModel> animeModels);

    }
}
