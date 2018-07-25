package bbamrin.animehelperreborn.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import bbamrin.animehelperreborn.Presenter.ResultViewPresenter;
import bbamrin.animehelperreborn.R;

public class ResultFragment extends Fragment implements ResultContract.View {

    ResultContract.Presenter mPresenter;
    RecyclerView mRecyclerView;
    ResultListAdapter mAdapter;
    ArrayList<AnimeModel> mAnimes;
    ArrayList<Genre> mGenres;
    //these names are so simple because we use this variables only to disable buttons
    Button button1;
    Button button2;

    public static ResultFragment newInstance() {

        Bundle args = new Bundle();

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showAnimeList(ArrayList<AnimeModel> animes) {
        this.mAnimes.addAll(animes);
        Log.d(StaticVars.LOG_TAG, "onShowAnimeList, mAnimes size: " + mAnimes.size());
        Log.d(StaticVars.LOG_TAG, "adapter state: " + mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoadMoreClick() {

    }

    @Override
    public void refreshList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorNotification() {

    }

    @Override
    public void showNothingMoreNotification() {

    }

    @Override
    public ArrayList<Genre> getGenres() {
        return this.mGenres;
    }

    @Override
    public ArrayList<AnimeModel> getAnimes() {
        return mAnimes;
    }

    @Override
    public void setPresenter(ResultContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_fragment_layout, container, false);

        if (getArguments().getParcelableArrayList(StaticVars.GENRES_LIST) != null) {
            mGenres = getArguments().getParcelableArrayList(StaticVars.GENRES_LIST);
            Log.d(StaticVars.LOG_TAG, mGenres.toString());
        }
        //temporary solution, it will work bad if screen turns, in future i want to save this list in savedInstanceState
        /*if (savedInstanceState == null) {
            mAnimes = new ArrayList<>();
        } else {
            mAnimes = savedInstanceState.getParcelableArrayList(StaticVars.ANIME_LIST);
        }*/
        if (mAnimes == null){
            mAnimes  =new ArrayList<>();
        }
        Log.d(StaticVars.LOG_TAG,"anime list of view size: "  + mAnimes.size());
        mAdapter = new ResultListAdapter(mAnimes,getContext());
        mRecyclerView = (RecyclerView) root.findViewById(R.id.animeRecycler);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        button1 = (Button) root.findViewById(R.id.clearAllButton);
        button2 = (Button) root.findViewById(R.id.clearOrSearchButton);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        mPresenter = new ResultViewPresenter(this);

        return root;
    }


    class ResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
        ArrayList<AnimeModel> mAnimeModels;
        Context mCtx;

        public ResultListAdapter(ArrayList<AnimeModel> mAnimeModels, Context ctx) {
            this.mCtx = ctx;
            this.mAnimeModels = mAnimeModels;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == StaticVars.FOOTER_CODE){
                return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
            } else if (viewType == StaticVars.ITEM_CODE){
                return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_card, parent, false));
            } else return null;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder  holder, int position) {

            if (holder instanceof ResultViewHolder){
                if (mAnimeModels.size()!=0 && position!= mAnimeModels.size()){
                    AnimeModel animeModel = mAnimeModels.get(position);
                    ((ResultViewHolder)holder).type.setText(animeModel.getKind());
                    ((ResultViewHolder)holder).animeName.setText(animeModel.getRussian());
                    Log.d(StaticVars.LOG_TAG, animeModel + " onBindViewHolder");
                    ((ResultViewHolder)holder).releaseDate.setText(animeModel.getAiredOn());
                    Glide.with(mCtx).load(Uri.parse(StaticVars.BASE_SHIKIMORI_URL + animeModel.getImage().getOriginal())).into(((ResultViewHolder)holder).animeImage);

                    Log.d(StaticVars.LOG_TAG,"length: " + mAnimeModels.size());
                    Log.d(StaticVars.LOG_TAG,"position: " + position );
                }

            } else if (holder instanceof FooterViewHolder){

            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mAnimeModels.size()){
                return StaticVars.FOOTER_CODE;
            } else {
                return StaticVars.ITEM_CODE;
            }
        }

        @Override
        public int getItemCount() {
            //return mAnimeModels.size();
            //stub
            Log.d(StaticVars.LOG_TAG,"adapter list size: " + mAnimeModels.size());
            return mAnimeModels.size()+1;
        }

        public class ResultViewHolder extends RecyclerView.ViewHolder {

            TextView animeName;
            TextView type;
            TextView releaseDate;
            ImageView animeImage;

            public ResultViewHolder(View itemView) {
                super(itemView);
                animeImage = (ImageView) itemView.findViewById(R.id.animeImageId);
                type = (TextView) itemView.findViewById(R.id.animeTypeId);
                releaseDate = (TextView) itemView.findViewById(R.id.animeReleaseDateId);
                animeName = (TextView) itemView.findViewById(R.id.animeNameId);
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder{
            TextView textViewFooter;
            public FooterViewHolder(View itemView) {
                super(itemView);
                textViewFooter  = (TextView)itemView.findViewById(R.id.footerText);
            }
        }

    }

}
