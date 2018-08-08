package bbamrin.animehelperreborn.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.UserAnimeListContract;
import bbamrin.animehelperreborn.Model.retrofitModel.AnimeModel;
import bbamrin.animehelperreborn.R;

public class UserAnimeListFragment extends Fragment implements UserAnimeListContract.View {
    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_fragment_layout, container, false);

        return root;
    }

    @Override
    public void setPresenter(Object presenter) {

    }


    class UserAnimeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        ArrayList<AnimeModel> mAnimeList;

        public UserAnimeListAdapter(ArrayList<AnimeModel> mAnimeList) {
            this.mAnimeList = mAnimeList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            AnimeModel animeModel = mAnimeList.get(position);
            UserAnimeListVH vh = (UserAnimeListVH)holder;
            //vh.animeName.setText(animeModel.get);
        }

        @Override
        public int getItemCount() {
            return mAnimeList.size();
        }

        class UserAnimeListVH extends RecyclerView.ViewHolder{

            TextView animeName;
            TextView type;
            TextView releaseDate;
            ImageView animeImage;
            LinearLayout animeLayout;

            public UserAnimeListVH(View itemView) {
                super(itemView);
                animeImage = (ImageView) itemView.findViewById(R.id.animeImageId);
                type = (TextView) itemView.findViewById(R.id.animeTypeId);
                releaseDate = (TextView) itemView.findViewById(R.id.animeReleaseDateId);
                animeName = (TextView) itemView.findViewById(R.id.animeNameId);
                animeLayout = (LinearLayout) itemView.findViewById(R.id.animeCardLinearLayout);
            }
        }

    }


}
