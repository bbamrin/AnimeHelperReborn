package bbamrin.animehelperreborn.View.InnerAnime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Model.retrofitModel.InnerAnimeData.Related;
import bbamrin.animehelperreborn.R;

public class RelatedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Related> mRelatedList;

    public RelatedRecyclerAdapter(ArrayList<Related> mRelatedList) {
        this.mRelatedList = mRelatedList;
    }
    public RelatedRecyclerAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RelatedRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.related_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RelatedRecyclerViewHolder)holder).relatedItemName.setText("stub");
        ((RelatedRecyclerViewHolder)holder).relatedItemName.setText("stub");
    }

    @Override
    public int getItemCount() {
        return mRelatedList.size();
    }


    public class RelatedRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView relatedItemName,relatedItemType;
        public RelatedRecyclerViewHolder(View itemView) {
            super(itemView);
            relatedItemType = (TextView)itemView.findViewById(R.id.relatedItemType);
            relatedItemName = (TextView)itemView.findViewById(R.id.relatedItemName);
        }
    }

}
