package bbamrin.animehelperreborn.View.InnerAnime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.utils.Utils;

public class InnerAnimeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AnimeModel mAnimeModel;
    private ArrayList<Related> mRelatedAnimes;

    public void setmAnimeModel(AnimeModel mAnimeModel) {
        this.mAnimeModel = mAnimeModel;
    }

    private ArrayList<AnimeScreenshot> mAnimeScreenshots;
    private Context mCtx;

    public InnerAnimeRecyclerAdapter(AnimeModel mAnimeModel, ArrayList<Related> mRelatedAnimes, ArrayList<AnimeScreenshot> mAnimeScreenshots, Context mCtx) {
        this.mAnimeModel = mAnimeModel;
        this.mRelatedAnimes = Utils.addOnlyWithExistingAnime(mRelatedAnimes);
        this.mAnimeScreenshots = mAnimeScreenshots;
        this.mCtx = mCtx;
        Log.d(StaticVars.LOG_TAG,"InnerAnimeRecyclerAdapter constructor");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == StaticVars.HEADER_VIEW_TYPE) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_header, parent, false));
        } else if (viewType == StaticVars.DESCRIPTION_VIEW_TYPE) {
            return new DescriptionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_description, parent, false));
        } else if (viewType == StaticVars.RELATED_VIEW_TYPE) {
            return new RelatedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_related, parent, false));
        } else if (viewType == StaticVars.SCREENSHOT_VIEW_TYPE) {
            return new ScreenshotsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_screenshot, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            Log.d(StaticVars.LOG_TAG,"headerViewHolder");
            //now i`m using toString method just to have at least some string
            Log.d(StaticVars.LOG_TAG,"HeaderViewHolder is binding");
            ((HeaderViewHolder) holder).headerAnimeName.setText(mAnimeModel.getName());
            ((HeaderViewHolder) holder).innerGenres.setText(mAnimeModel.getGenres() == null ?"null": mAnimeModel.getGenres().toString());
            ((HeaderViewHolder) holder).innerStatus.setText(mAnimeModel.getReleasedOn() == null ?"null": mAnimeModel.getReleasedOn().toString());
            ((HeaderViewHolder) holder).innerStudio.setText(mAnimeModel.getStudios() == null ?"null": mAnimeModel.getStudios().toString());
            ((HeaderViewHolder) holder).innerTypeText.setText(mAnimeModel.getKind() == null ?"null": mAnimeModel.getKind().toString());
        } else if (holder instanceof DescriptionViewHolder) {
            ((DescriptionViewHolder) holder).innerAnimeDescriptionHeader.setText("Description");
            Log.d(StaticVars.LOG_TAG,"animeID : " + mAnimeModel.getId());
            Log.d(StaticVars.LOG_TAG,"full anime description : " + mAnimeModel.getDescription());

            ((DescriptionViewHolder) holder).innerAnimeDescription.setText(mAnimeModel.getDescription());
        } else if (holder instanceof RelatedViewHolder) {
            ((RelatedViewHolder) holder).setRelatedVHRelatedList(mRelatedAnimes);
            ((RelatedViewHolder) holder).refreshList();

        } else if (holder instanceof ScreenshotsViewHolder) {

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return StaticVars.HEADER_VIEW_TYPE;
        } else if (position == 1) {
            return StaticVars.DESCRIPTION_VIEW_TYPE;
        } else if (position == 2) {
            return StaticVars.RELATED_VIEW_TYPE;
            //return StaticVars.RELATED_VIEW_TYPE;

        } return StaticVars.HEADER_VIEW_TYPE;
        //return StaticVars.SCREENSHOT_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return 4;
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
        private RelatedRecyclerAdapter mAdapter;
        private ArrayList<Related> mRelatedVHRelatedList;

        public void setRelatedVHRelatedList(ArrayList<Related> mRelatedVHRelatedList) {
            this.mRelatedVHRelatedList.clear();
            this.mRelatedVHRelatedList.addAll(mRelatedVHRelatedList);
        }
        public void refreshList(){
            this.mAdapter.notifyDataSetChanged();
        }



        public RelatedViewHolder(View itemView) {
            super(itemView);
            mRelatedVHRelatedList = new ArrayList<>();
            this.mAdapter = new RelatedRecyclerAdapter(mRelatedAnimes);
            relatedRecycler = (RecyclerView) itemView.findViewById(R.id.innerAnimeRelatedRecycler);
            relatedRecycler.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
            relatedRecycler.setAdapter(mAdapter);
        }
    }

    public class ScreenshotsViewHolder extends RecyclerView.ViewHolder {

        public ScreenshotsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
