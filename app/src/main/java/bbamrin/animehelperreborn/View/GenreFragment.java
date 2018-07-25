package bbamrin.animehelperreborn.View;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.GenreListContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Presenter.GenresViewPresenter;
import bbamrin.animehelperreborn.R;

public class GenreFragment extends Fragment implements GenreListContract.View {

    GenreListContract.Presenter mPresenter;
    GenreRecyclerAdapter mAdapter;
    ArrayList<Genre> mGenreArrayList;
    String mClassification;
    RecyclerView mRecyclerView;
    Button clearGenresButton;
    Button clearOrfindButton;

    public static GenreFragment newInstance() {
        Bundle args = new Bundle();
        GenreFragment fragment = new GenreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mGenreArrayList != null) {
            outState.putParcelableArrayList(StaticVars.GENRES_LIST, this.mGenreArrayList);

        }
        if (this.mClassification != null) {
            outState.putString(StaticVars.CLASSIFICATION_NAME, mClassification);
        }
    }



    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList(StaticVars.GENRES_LIST) != null) {
                this.mGenreArrayList = savedInstanceState.getParcelableArrayList(StaticVars.GENRES_LIST);
            }
            if (savedInstanceState.getString(StaticVars.CLASSIFICATION_NAME) != null) {
                this.mClassification = savedInstanceState.getString(StaticVars.CLASSIFICATION_NAME);
            }
        }
    }

    @Override
    public void setPresenter(GenreListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClearButtonClick(ArrayList<Genre> genres) {
        mPresenter.clearGenres();
    }

    @Override
    public void onGenreClick(View view, int position) {
        mGenreArrayList.get(position).setChosen(!mGenreArrayList.get(position).isChosen());
        ((CheckBox) view.findViewById(R.id.simpleGenreCheckBoxId)).setChecked(mGenreArrayList.get(position).isChosen());
    }

    @Override
    public ArrayList<Genre> getGenres() {
        return mGenreArrayList;
    }

    @Override
    public String getClassificationName() {
        return mClassification;
    }

    @Override
    public void refreshGenresList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        mPresenter.addGenresToClassification();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycler_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.animeRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (mGenreArrayList == null) {
            mGenreArrayList = getArguments().getParcelableArrayList(StaticVars.GENRES_LIST);
        }
        mAdapter = new GenreRecyclerAdapter(mGenreArrayList, this);
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter == null) {
            setPresenter(new GenresViewPresenter(this));
        }
        clearGenresButton = (Button) root.findViewById(R.id.clearOrSearchButton);
        clearGenresButton.setText(getResources().getText(R.string.clear));
        clearGenresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clearGenres();
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 7;
        clearGenresButton.setLayoutParams(params);
        clearOrfindButton = (Button) root.findViewById(R.id.clearAllButton);
        clearOrfindButton.setVisibility(View.GONE);
        return root;
    }


    public class GenreRecyclerAdapter extends RecyclerView.Adapter<GenreRecyclerAdapter.GenreViewHolder> {

        ArrayList<Genre> genreArrayList;
        GenreListContract.View mOnClickListener;

        public GenreRecyclerAdapter(ArrayList<Genre> genreArrayList, GenreListContract.View mOnClickListener) {
            this.genreArrayList = genreArrayList;
            this.mOnClickListener = mOnClickListener;
        }

        @NonNull
        @Override
        public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.simple_genre_layout, parent, false);
            GenreViewHolder holder = new GenreViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
            Genre genre = genreArrayList.get(position);
            holder.isChecked.setChecked(genre.isChosen());
            holder.genreName.setText(genre.getTextGenre());
        }

        @Override
        public int getItemCount() {
            return genreArrayList.size();
        }

        public class GenreViewHolder extends RecyclerView.ViewHolder {

            TextView genreName;
            CheckBox isChecked;

            public GenreViewHolder(View itemView) {
                super(itemView);
                genreName = (TextView) itemView.findViewById(R.id.simpleGenreTextId);
                isChecked = (CheckBox) itemView.findViewById(R.id.simpleGenreCheckBoxId);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onGenreClick(v, getAdapterPosition());
                    }
                });
                itemView.findViewById(R.id.simpleGenreCheckBoxId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onGenreClick(v, getAdapterPosition());
                    }
                });

            }
        }


    }


}
