package bbamrin.animehelperreborn.View.InnerAnime;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Presenter.InnerAnimePresenter;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.utils.Utils;

import static bbamrin.animehelperreborn.Model.StaticVars.HEADER_VIEW_TYPE;
import static bbamrin.animehelperreborn.utils.Utils.addOnlyWithExistingAnime;

public class InnerAnimeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int itemCount = 2;
    private AnimeModel mAnimeModel;
    private ArrayList<Related> mRelatedAnimes;
    private ArrayList<AnimeScreenshot> mAnimeScreenshots;
    private Context mCtx;
    private InnerAnimePresenter mPresenter;

    public InnerAnimeRecyclerAdapter(AnimeModel mAnimeModel, ArrayList<Related> mRelatedAnimes, ArrayList<AnimeScreenshot> mAnimeScreenshots, Context mCtx) {
        this.mAnimeModel = mAnimeModel;
        this.mRelatedAnimes = addOnlyWithExistingAnime(mRelatedAnimes);
        this.mAnimeScreenshots = mAnimeScreenshots;
        this.mCtx = mCtx;

    }

    public void setmPresenter(InnerAnimePresenter presenter) {
        this.mPresenter = presenter;

    }

    public void setmAnimeModel(AnimeModel mAnimeModel) {
        this.mAnimeModel = mAnimeModel;
    }

    public void setmRelatedAnimes(ArrayList<Related> mRelatedAnimes) {
        this.mRelatedAnimes.clear();
        this.mRelatedAnimes.addAll(mRelatedAnimes);
        if (itemCount < 4 && mRelatedAnimes.size() > 0) {
            itemCount += 1;
        }
    }

    public void setmAnimeScreenshots(ArrayList<AnimeScreenshot> mAnimeScreenshots) {
        this.mAnimeScreenshots.clear();
        this.mAnimeScreenshots.addAll(mAnimeScreenshots);

        if (itemCount < 4 && this.mAnimeScreenshots.size() > 0) {
            itemCount += 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_header, parent, false));
        } else if (viewType == StaticVars.DESCRIPTION_VIEW_TYPE) {
            return new DescriptionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_description, parent, false));
        } else if (viewType == StaticVars.RELATED_VIEW_TYPE) {
            return new RelatedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_related, parent, false));
        } else if (viewType == StaticVars.SCREENSHOT_VIEW_TYPE) {
            return new ScreenshotsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_screens_recycler, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder != null) {
            if (holder instanceof HeaderViewHolder) {
                if (mAnimeModel != null) {
                    ((HeaderViewHolder) holder).headerAnimeName.setText(mAnimeModel.getName());
                    Glide.with(mCtx).load(Uri.parse(StaticVars.BASE_SHIKIMORI_URL + mAnimeModel.getImage().getOriginal())).into(((HeaderViewHolder) holder).headerImage);
                    ((HeaderViewHolder) holder).innerGenres.setText(mAnimeModel.getGenres() == null ? "null" : Utils.generateReceivedGenresStrig(mAnimeModel.getGenres()));
                    ((HeaderViewHolder) holder).innerStatus.setText(mAnimeModel.getReleasedOn() == null ? "null" : mAnimeModel.getReleasedOn());
                    ((HeaderViewHolder) holder).innerStudio.setText("here will be studios soon");
                    ((HeaderViewHolder) holder).innerTypeText.setText(mAnimeModel.getKind() == null ? "null" : mAnimeModel.getKind());
                }

            } else if (holder instanceof DescriptionViewHolder) {
                if (mAnimeModel != null) {
                    ((DescriptionViewHolder) holder).innerAnimeDescriptionHeader.setText("Описание:");
                    ((DescriptionViewHolder) holder).innerAnimeDescription.setText(mAnimeModel.getDescription());
                }
            } else if (holder instanceof RelatedViewHolder) {
                ((RelatedViewHolder) holder).setRelatedOnClickListener(mPresenter);
                ((RelatedViewHolder) holder).refreshList();

            } else if (holder instanceof ScreenshotsViewHolder) {
                ((ScreenshotsViewHolder) holder).refreshList();
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW_TYPE;
        } else if (position == 1) {
            return StaticVars.DESCRIPTION_VIEW_TYPE;
        } else if (position == 2) {
            if (mRelatedAnimes.size() == 0) {
                return StaticVars.SCREENSHOT_VIEW_TYPE;
            } else return StaticVars.RELATED_VIEW_TYPE;
        } else if (position == 3) {
            return StaticVars.SCREENSHOT_VIEW_TYPE;
        }
        return StaticVars.FAIL_CODE;

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerAnimeName, innerTypeText, innerStudio, innerStatus, innerGenres;
        ImageView headerImage;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerAnimeName = (TextView) itemView.findViewById(R.id.headerAnimeNameId);
            innerTypeText = (TextView) itemView.findViewById(R.id.innerTypeTextId);
            innerStudio = (TextView) itemView.findViewById(R.id.innerStudioId);
            innerStatus = (TextView) itemView.findViewById(R.id.innerStatusId);
            innerGenres = (TextView) itemView.findViewById(R.id.innerGenresId);
            headerImage = (ImageView) itemView.findViewById(R.id.innerAnimeImageId);
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {

        TextView innerAnimeDescriptionHeader, innerAnimeDescription;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            innerAnimeDescription = (TextView) itemView.findViewById(R.id.innerAnimeDescription);
            innerAnimeDescriptionHeader = (TextView) itemView.findViewById(R.id.innerAnimeDescriptionHeader);
        }
    }

    public class RelatedViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView relatedRecycler;
        private RelatedRecyclerAdapter mRelatedAdapter;
        private ArrayList<Related> mRelatedVHRelatedList;

        public RelatedViewHolder(View itemView) {
            super(itemView);
            mRelatedVHRelatedList = new ArrayList<>();
            mRelatedAdapter = new RelatedRecyclerAdapter(mRelatedAnimes, mPresenter);
            relatedRecycler = (RecyclerView) itemView.findViewById(R.id.innerAnimeRelatedRecycler);
            relatedRecycler.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
            relatedRecycler.setAdapter(mRelatedAdapter);
        }

        public void setRelatedVHRelatedList(ArrayList<Related> mRelatedVHRelatedList) {
            this.mRelatedVHRelatedList.clear();
            this.mRelatedVHRelatedList.addAll(mRelatedVHRelatedList);
        }

        public void setRelatedOnClickListener(InnerAnimePresenter presenter) {
            mRelatedAdapter.setmOnClickListener(mPresenter);
        }

        public void refreshList() {
            mRelatedAdapter.notifyDataSetChanged();
        }
    }

    public class ScreenshotsViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView screensRecycler;
        private ScreenshotsRecyclerAdapter mAdapter;

        public ScreenshotsViewHolder(View itemView) {
            super(itemView);
            this.mAdapter = new ScreenshotsRecyclerAdapter(mAnimeScreenshots, mCtx);
            screensRecycler = (RecyclerView) itemView.findViewById(R.id.innerAnimeScreensRecycler);
            screensRecycler.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
            screensRecycler.setAdapter(mAdapter);
        }

        public void refreshList() {
            this.mAdapter.notifyDataSetChanged();
        }

    }

}
