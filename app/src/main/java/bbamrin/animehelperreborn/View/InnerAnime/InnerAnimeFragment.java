package bbamrin.animehelperreborn.View.InnerAnime;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.InnerAnimeContract;
import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Anime;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inner_anime_layout,container,false);
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.animeInnerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRelated = new ArrayList<>();
        mScreenshots = new ArrayList<>();
        //just because i don`t want this to be null, it anyway will  be a FullAnimeModel when full anime model will be downloaded
        mFullAnimeModel = getArguments().getParcelable(StaticVars.ANIME_MODEL);
        mAdapter = new InnerAnimeRecyclerAdapter(mFullAnimeModel,mRelated,mScreenshots,getContext());
        recyclerView.setAdapter(mAdapter);
        mPresenter = new InnerAnimePresenter(this);
        return root;
    }

    public static InnerAnimeFragment newInstance(Parcelable parcelable, String key  ) {

        Bundle args = new Bundle();
        args.putParcelable(key,parcelable);
        InnerAnimeFragment fragment = new InnerAnimeFragment();
        fragment.setArguments(args);
        return fragment;
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
//        Log.d(StaticVars.LOG_TAG,"original+++++++++++++++++++++++++: "+ screenshots.get(0).getOriginal());
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
