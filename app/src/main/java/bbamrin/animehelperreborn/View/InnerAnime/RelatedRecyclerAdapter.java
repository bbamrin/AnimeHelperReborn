package bbamrin.animehelperreborn.View.InnerAnime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.InnerAnimeContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.Presenter.InnerAnimePresenter;
import bbamrin.animehelperreborn.R;

public class RelatedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Related> mRelatedList;
    private InnerAnimePresenter mOnClickListener;

    public void setmOnClickListener(InnerAnimePresenter c){
        mOnClickListener =c;

    }

    public RelatedRecyclerAdapter(ArrayList<Related> mRelatedList, InnerAnimePresenter mOnClickListener) {
        this.mRelatedList = mRelatedList;
        this.mOnClickListener = mOnClickListener;
    }


    public RelatedRecyclerAdapter(ArrayList<Related> related) {
        this.mRelatedList = related;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RelatedRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RelatedRecyclerViewHolder) holder).relatedItemName.setText(mRelatedList.get(position).getAnime().getRussian());
        ((RelatedRecyclerViewHolder) holder).relatedItemType.setText(mRelatedList.get(position).getRelationRussian());
    }

    @Override
    public int getItemCount() {
        return mRelatedList.size();
    }


    public class RelatedRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView relatedItemName, relatedItemType;

        public RelatedRecyclerViewHolder(View itemView) {
            super(itemView);
            relatedItemType = (TextView) itemView.findViewById(R.id.relatedItemType);
            relatedItemName = (TextView) itemView.findViewById(R.id.relatedItemName);
            itemView.findViewById(R.id.relatedItemCardId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(StaticVars.LOG_TAG,"clicked related state: " +mRelatedList.get(getAdapterPosition()).getAnime());
                    mOnClickListener.onRelatedItemClick(mRelatedList.get(getAdapterPosition()).getAnime());
                }
            });
        }
    }

}
