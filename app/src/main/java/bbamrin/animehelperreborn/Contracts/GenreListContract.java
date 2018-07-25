package bbamrin.animehelperreborn.Contracts;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;

import bbamrin.animehelperreborn.BasePresenter;
import bbamrin.animehelperreborn.BaseView;
import bbamrin.animehelperreborn.Model.internalModel.Genre;

public interface GenreListContract {

    public interface View extends BaseView<Presenter>{

        public void onClearButtonClick(ArrayList<Genre> genres);
        public void onGenreClick(android.view.View view,int position);
        public ArrayList<Genre> getGenres();
        public String getClassificationName();
        public void refreshGenresList();

    }

    public interface Presenter extends BasePresenter {

        public void addGenresToClassification();
        public void clearGenres();

    }
}
