package bbamrin.animehelperreborn.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import bbamrin.animehelperreborn.Contracts.ResultContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Presenter.ResultViewPresenter;
import bbamrin.animehelperreborn.R;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class ResultFragment extends Fragment implements ResultContract.View {

    ResultContract.Presenter mPresenter;
    RecyclerView mRecyclerView;
    ResultListAdapter mAdapter;
    ArrayList<AnimeModel> mAnimes;
    ArrayList<Genre> mGenres;
    Disposable disposable;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar mProgressBar;

    public static ResultFragment newInstance() {

        Bundle args = new Bundle();

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showAnimeList(ArrayList<AnimeModel> animes) {
        this.mAnimes.clear();
        mAdapter.notifyDataSetChanged();
        this.mAnimes.addAll(animes);
        Log.d(StaticVars.LOG_TAG, "onShowAnimeList, mAnimes size: " + mAnimes.size());
        Log.d(StaticVars.LOG_TAG, "adapter state: " + mAdapter);
        mAdapter.notifyDataSetChanged();
        showRecycler();
        stopDownloadAnimation();
    }

    @Override
    public void onLoadMoreClick(final android.view.View view, int position) {
        mPresenter.loadMoreAnimes();
        view.setEnabled(false);
        StaticVars.UNBLOCK_FOOTER = false;
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(1000));

                emitter.onComplete();
            }
        });
        disposable = completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                StaticVars.UNBLOCK_FOOTER = true;
                if (mAdapter != null) {
                    mAdapter.notifyItemChanged(mAnimes.size());
                }
            }

            @Override
            public void onError(Throwable e) {
                Thread.currentThread().interrupt();
            }
        });

    }

    @Override
    public void onAnimeCardClick(android.view.View view, int position) {
        mPresenter.onAnimeClick(view,position);
    }

    @Override
    public void refreshList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecycler() {
        if (mRecyclerView!=null&&mSwipeRefreshLayout!=null){
            mRecyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            Log.d(StaticVars.LOG_TAG,"recycler state: " + mRecyclerView + ",\nrefreshLayout state  : " + mSwipeRefreshLayout);
        }

    }

    @Override
    public void hideRecycler() {
        if (mRecyclerView!=null&&mSwipeRefreshLayout!=null){
            mRecyclerView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }

    }


    public SwipeRefreshLayout getRefreshLayout(){
        return mSwipeRefreshLayout;
    }

    @Override
    public void stopRefreshingAnimation() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDownloadAnimation() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopDownloadAnimation() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorNotification() {
        Toast.makeText(getContext(),"there is no internet connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNothingMoreNotification() {
        Toast.makeText(getContext(),"больше аниме нет",Toast.LENGTH_SHORT).show();
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
        if (disposable!= null){
            disposable.dispose();
        }
        super.onDestroy();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pull_to_refresh_layout, container, false);
        StaticVars.UNBLOCK_FOOTER = true;
        if (getArguments().getParcelableArrayList(StaticVars.GENRES_LIST) != null) {
            mGenres = getArguments().getParcelableArrayList(StaticVars.GENRES_LIST);
            Log.d(StaticVars.LOG_TAG, mGenres.toString());
        }
        mProgressBar = (ProgressBar)root.findViewById(R.id.resultProgressBar);
        if (mAnimes == null) {
            mAnimes = new ArrayList<>();
        }
        Log.d(StaticVars.LOG_TAG, "anime list of view size: " + mAnimes.size());
        mAdapter = new ResultListAdapter(mAnimes, getContext(), this);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.animePullToRefreshRecycler);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.pullToRefreshRecyclerFragmentId);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onViewRefresh();
            }
        });
        mPresenter = new ResultViewPresenter(this);

        return root;
    }


    class ResultListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<AnimeModel> mAnimeModels;
        Context mCtx;
        ResultContract.View mOnClickListener;

        public ResultListAdapter(ArrayList<AnimeModel> mAnimeModels, Context ctx, ResultContract.View mOnClickListener) {
            this.mCtx = ctx;
            this.mAnimeModels = mAnimeModels;
            this.mOnClickListener = mOnClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == StaticVars.FOOTER_CODE) {
                return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false));
            } else
                return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_card, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof ResultViewHolder && holder != null) {
                if (mAnimeModels.size() != 0 && position != mAnimeModels.size()) {
                    AnimeModel animeModel = mAnimeModels.get(position);
                    ((ResultViewHolder) holder).type.setText(animeModel.getKind());
                    ((ResultViewHolder) holder).animeName.setText(animeModel.getRussian());
                    ((ResultViewHolder) holder).releaseDate.setText(animeModel.getAiredOn());
                    Glide.with(mCtx).load(Uri.parse(StaticVars.BASE_SHIKIMORI_URL + animeModel.getImage().getOriginal())).into(((ResultViewHolder) holder).animeImage);

                }

            } else if (holder instanceof FooterViewHolder && holder != null) {
                if (StaticVars.UNBLOCK_FOOTER) {
                    ((FooterViewHolder) holder).footerButton.setEnabled(true);
                } else {
                    ((FooterViewHolder) holder).footerButton.setEnabled(false);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mAnimeModels.size() && mAnimeModels.size() != 0) {
                return StaticVars.FOOTER_CODE;
            } else {
                return StaticVars.ITEM_CODE;
            }
        }

        @Override
        public int getItemCount() {

            return mAnimeModels.size() + 1;
        }

        public class ResultViewHolder extends RecyclerView.ViewHolder {

            TextView animeName;
            TextView type;
            TextView releaseDate;
            ImageView animeImage;

            public ResultViewHolder(final View itemView) {
                super(itemView);
                animeImage = (ImageView) itemView.findViewById(R.id.animeImageId);
                type = (TextView) itemView.findViewById(R.id.animeTypeId);
                releaseDate = (TextView) itemView.findViewById(R.id.animeReleaseDateId);
                animeName = (TextView) itemView.findViewById(R.id.animeNameId);
                itemView.findViewById(R.id.animeCardId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onAnimeCardClick(itemView,getAdapterPosition());
                    }
                });
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {
            Button footerButton;

            public FooterViewHolder(final View itemView) {
                super(itemView);
                footerButton = (Button) itemView.findViewById(R.id.footerText);
                itemView.findViewById(R.id.footerCard).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onLoadMoreClick(itemView, getAdapterPosition());
                        Log.d(StaticVars.LOG_TAG, "footer clicked");
                    }
                });
                footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(StaticVars.LOG_TAG, "footer clicked");
                        mOnClickListener.onLoadMoreClick(itemView, getAdapterPosition());
                    }
                });
            }
        }

    }

}
