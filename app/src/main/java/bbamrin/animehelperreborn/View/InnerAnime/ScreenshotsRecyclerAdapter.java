package bbamrin.animehelperreborn.View.InnerAnime;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.AnimeScreenshot;
import bbamrin.animehelperreborn.R;

public class ScreenshotsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ScreenshotsRecyclerAdapter(ArrayList<AnimeScreenshot> mScreens, Context mCtx) {
        this.mScreens = mScreens;
        this.mCtx = mCtx;
    }

    private ArrayList<AnimeScreenshot> mScreens;
    private Context mCtx;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerScreenshotsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerScreenshotsViewHolder){
            Glide.with(mCtx).load(Uri.parse(StaticVars.BASE_SHIKIMORI_URL + mScreens.get(position).getOriginal())).into(((InnerScreenshotsViewHolder) holder).animeScreen);
        }
    }

    @Override
    public int getItemCount() {
        return mScreens.size();
    }



    public class InnerScreenshotsViewHolder extends RecyclerView.ViewHolder{
        ImageView animeScreen;
        public InnerScreenshotsViewHolder(View itemView) {
            super(itemView);
            animeScreen = (ImageView)itemView.findViewById(R.id.animeScreenId);
        }
    }
}
