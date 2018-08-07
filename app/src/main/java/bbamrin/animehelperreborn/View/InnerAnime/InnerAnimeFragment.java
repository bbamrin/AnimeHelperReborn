package bbamrin.animehelperreborn.View.InnerAnime;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.InnerAnimeContract;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Presenter.InnerAnimePresenter;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.utils.Utils;

public class InnerAnimeFragment extends Fragment implements InnerAnimeContract.View {

    private InnerAnimeContract.Presenter mPresenter;
    private ArrayList<Related> mRelated;
    private ArrayList<AnimeScreenshot> mScreenshots;
    private AnimeModel mFullAnimeModel;
    private InnerAnimeRecyclerAdapter mAdapter;
    private String mAnimeId;

    public static InnerAnimeFragment newInstance(Parcelable parcelable, String key) {
        Bundle args = new Bundle();
        args.putParcelable(key, parcelable);
        InnerAnimeFragment fragment = new InnerAnimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static InnerAnimeFragment newInstance(String animeId, String key) {

        Bundle args = new Bundle();
        args.putString(key, animeId);
        InnerAnimeFragment fragment = new InnerAnimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inner_anime_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.animeInnerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRelated = new ArrayList<>();
        mScreenshots = new ArrayList<>();
        //just because i don`t want this to be null, it anyway will  be a FullAnimeModel when full anime model will be downloaded
        if (getArguments().getParcelable(StaticVars.ANIME_MODEL) != null) {
            mFullAnimeModel = getArguments().getParcelable(StaticVars.ANIME_MODEL);
            mAdapter = new InnerAnimeRecyclerAdapter(mFullAnimeModel, mRelated, mScreenshots, getContext());
        } else if (getArguments().getString(StaticVars.ANIME_MODEL) != null) {
            mAnimeId = getArguments().getString(StaticVars.ANIME_MODEL);
            mAdapter = new InnerAnimeRecyclerAdapter(null, mRelated, mScreenshots, getContext());
        }
        recyclerView.setAdapter(mAdapter);
        mPresenter = new InnerAnimePresenter(this);
        mAdapter.setmPresenter((InnerAnimePresenter) mPresenter);
        return root;
    }

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
    }

    @Override
    public void setMainInfo(AnimeModel animeModel) {
        mAdapter.setmAnimeModel(animeModel);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void setScreenshots(ArrayList<AnimeScreenshot> screenshots) {
        mAdapter.setmAnimeScreenshots(screenshots);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRelated(ArrayList<Related> relatedList) {
        mAdapter.setmRelatedAnimes(Utils.addOnlyWithExistingAnime(relatedList));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public AnimeModel getAnimeModel() {
        return getArguments().getParcelable(StaticVars.ANIME_MODEL);
    }
}
