package bbamrin.animehelperreborn.Contracts;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.BaseView;
import bbamrin.animehelperreborn.Model.internalModel.Classification;
import bbamrin.animehelperreborn.Model.internalModel.Genre;

public interface ClassificationViewContract {

    public interface View extends BaseView<Presenter> {


        public void onClassificationClick(int position, android.view.View itemView);

        public ArrayList<Genre> getGenres(int position);

        public ArrayList<Genre> getGenres(String name);

        public FragmentManager getViewSupportFragmentManager();

        public String getClassificationName(int position);

        public void refreshClassificationList();

        public ArrayList<Classification> getClassifications();

    }

    public interface Presenter extends BasePresenter {
        public void editGenres(int position, android.view.View itemView);

        public void onGenresViewResult(ArrayList<Genre> genres, String classificationName);

        public void clearGenres();

        public void findAnime();
    }
}
