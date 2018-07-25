package bbamrin.animehelperreborn.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.ClassificationViewContract;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Classification;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.View.GenreFragment;
import bbamrin.animehelperreborn.View.ResultFragment;
import bbamrin.animehelperreborn.utils.FragmentUtils;

public class ClassificationViewPresenter implements ClassificationViewContract.Presenter {

    ClassificationViewContract.View mView;

    public ClassificationViewPresenter(ClassificationViewContract.View view) {
        this.mView = view;
        mView.setPresenter(this);

    }


    @Override
    public void editGenres(int position, View itemView) {
        GenreFragment fragment = GenreFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(StaticVars.GENRES_LIST, mView.getGenres(position));
        bundle.putString(StaticVars.CLASSIFICATION_NAME, mView.getClassificationName(position));
        fragment.setArguments(bundle);
        fragment.setTargetFragment((Fragment) mView, StaticVars.CLASSIFICATION_REQUEST_CODE);
        FragmentUtils.replaceSupportFragment(mView.getViewSupportFragmentManager(), fragment, R.id.mainActivityId);

    }

    @Override
    public void onGenresViewResult(ArrayList<Genre> genres, String classificationName) {
        ArrayList<Classification> classifications = mView.getClassifications();
        for (int i = 0; i < classifications.size(); ++i) {
            if (classifications.get(i).getClassificationName().equals(classificationName)) {
                classifications.get(i).setGenreList(genres);
            }
        }


    }


    @Override
    public void clearGenres() {
        for (Classification c : mView.getClassifications()){
            for (Genre g : c.getGenreList()){
                g.setChosen(false);
            }
        }
        mView.refreshClassificationList();
    }

    @Override
    public void findAnime() {
        ArrayList<Genre> genres = new ArrayList<>();
        for (Classification c: mView.getClassifications()){
            for(Genre g: c.getGenreList()){
                if (g.isChosen()){
                    genres.add(g);
                }
            }

        }
        ResultFragment fragment = ResultFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(StaticVars.GENRES_LIST, genres);
        Log.d(StaticVars.LOG_TAG,genres.toString());
        fragment.setArguments(bundle);
        FragmentUtils.replaceSupportFragment(mView.getViewSupportFragmentManager(), fragment, R.id.mainActivityId);
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
