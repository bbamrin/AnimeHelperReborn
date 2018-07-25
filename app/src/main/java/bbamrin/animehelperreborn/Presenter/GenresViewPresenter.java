package bbamrin.animehelperreborn.Presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.GenreListContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;

public class GenresViewPresenter implements GenreListContract.Presenter {
    GenreListContract.View mView;
    @Override
    public void addGenresToClassification() {
        Intent intent  = new Intent();
        intent.putExtra(StaticVars.CHOSEN_GENRES,mView.getGenres());
        intent.putExtra(StaticVars.CLASSIFICATION_NAME,mView.getClassificationName());
        ((Fragment)mView).getTargetFragment().onActivityResult(StaticVars.CLASSIFICATION_REQUEST_CODE, Activity.RESULT_OK,intent);
    }

    public GenresViewPresenter(GenreListContract.View view) {
        this.mView = view;
    }

    @Override
    public void clearGenres() {
        for (Genre g: mView.getGenres()){
            g.setChosen(false);
        }
        mView.refreshGenresList();
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
}
