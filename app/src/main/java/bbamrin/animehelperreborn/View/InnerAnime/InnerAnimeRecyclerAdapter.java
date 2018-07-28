package bbamrin.animehelperreborn.View.InnerAnime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeMap;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.AnimeModel;
import bbamrin.animehelperreborn.Model.retrofitModel.ResultData.children.Image;
import bbamrin.animehelperreborn.R;

public class InnerAnimeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AnimeModel mAnimeModel;
    private ArrayList<Related>  mRelatedAnimes;
    private ArrayList<AnimeScreenshot> mAnimeScreenShots;

    public InnerAnimeRecyclerAdapter(AnimeModel mAnimeModel, ArrayList<Related> mRelatedAnimes, ArrayList<AnimeScreenshot> mAnimeScreenShots) {
        this.mAnimeModel = mAnimeModel;
        this.mRelatedAnimes = mRelatedAnimes;
        this.mAnimeScreenShots = mAnimeScreenShots;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == StaticVars.HEADER_VIEW_TYPE){
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_header, parent, false));
        } else if (viewType == StaticVars.DESCRIPTION_VIEW_TYPE){
            return new DescriptionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_description, parent, false));
        } else if (viewType == StaticVars.RELATED_VIEW_TYPE){
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_anime_screenshot, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return StaticVars.HEADER_VIEW_TYPE;
        } else if (position == 1){
            return StaticVars.DESCRIPTION_VIEW_TYPE;
        }  else if(position == 2){
            return StaticVars.RELATED_VIEW_TYPE;
        } else return StaticVars.SCREENSHOT_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView headerAnimeName, innerTypeText, innerStudio, innerStatus,innerGenres;
        ImageView headerImage;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerAnimeName = (TextView)itemView.findViewById(R.id.headerAnimeNameId);
            innerTypeText = (TextView)itemView.findViewById(R.id.innerTypeTextId);
            innerStudio = (TextView)itemView.findViewById(R.id.innerStudioId);
            innerStatus = (TextView)itemView.findViewById(R.id.innerStatusId);
            innerGenres = (TextView)itemView.findViewById(R.id.innerGenresId);
            headerImage = (ImageView)itemView.findViewById(R.id.innerAnimeImageId);
        }
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder{

        TextView innerAnimeDescriptionHeader,innerAnimeDescription;
        public DescriptionViewHolder(View itemView) {
            super(itemView);
            innerAnimeDescription = (TextView)itemView.findViewById(R.id.innerAnimeDescription);
            innerAnimeDescriptionHeader = (TextView)itemView.findViewById(R.id.innerAnimeDescriptionHeader);
        }
    }

    public class RelatedViewHolder extends RecyclerView.ViewHolder{


        public RelatedViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ScreenshotsViewHolder extends RecyclerView.ViewHolder{

        public ScreenshotsViewHolder(View itemView) {
            super(itemView);
        }
    }

}
