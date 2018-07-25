package bbamrin.animehelperreborn.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bbamrin.animehelperreborn.Contracts.ClassificationViewContract;
import bbamrin.animehelperreborn.Model.StaticVars;
import bbamrin.animehelperreborn.Model.internalModel.Classification;
import bbamrin.animehelperreborn.Model.internalModel.Genre;
import bbamrin.animehelperreborn.Presenter.ClassificationViewPresenter;
import bbamrin.animehelperreborn.R;
import bbamrin.animehelperreborn.utils.Utils;

public class ClassificationFragment extends Fragment implements ClassificationViewContract.View {

    ClassificationViewContract.Presenter mPresenter;
    ArrayList<Classification> mClassificationArrayList;
    ClassificationRecyclerAdapter adapter;

    public static ClassificationFragment newInstance() {
        Bundle args = new Bundle();
        ClassificationFragment fragment = new ClassificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mClassificationArrayList != null) {
            outState.putParcelableArrayList(StaticVars.CLASSIFICATION_LIST, this.mClassificationArrayList);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelableArrayList(StaticVars.CLASSIFICATION_LIST) != null) {
                this.mClassificationArrayList = savedInstanceState.getParcelableArrayList(StaticVars.CLASSIFICATION_LIST);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ClassificationViewPresenter(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            if (requestCode == StaticVars.CLASSIFICATION_REQUEST_CODE) {
                mPresenter.onGenresViewResult(
                        data.<Genre>getParcelableArrayListExtra(StaticVars.GENRES_LIST),
                        data.getStringExtra(StaticVars.CLASSIFICATION_NAME)
                );
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.recycler_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.animeRecycler);

        if (mClassificationArrayList == null) {
            mClassificationArrayList = Utils.getClassificationList();
        }

        if (savedInstanceState != null) {
            adapter = new ClassificationRecyclerAdapter(savedInstanceState.<Classification>getParcelableArrayList(StaticVars.CLASSIFICATION_LIST), this);
        } else {
            adapter = new ClassificationRecyclerAdapter(this.mClassificationArrayList, this);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        Button clearAllButton = ((Button) root.findViewById(R.id.clearAllButton));
        clearAllButton.setText(getResources().getText(R.string.clearAll));
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clearGenres();
            }
        });
        Button clearOrSearchButton = ((Button) root.findViewById(R.id.clearOrSearchButton));
        clearOrSearchButton.setText(getResources().getText(R.string.find));
        clearOrSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.findAnime();
            }
        });
        return root;
    }


    @Override
    public void onClassificationClick(int position, View itemView) {
        mPresenter.editGenres(position, itemView);
        Log.d(StaticVars.LOG_TAG, mPresenter + "");
        System.out.println("click");
    }

    @Override
    public ArrayList<Genre> getGenres(int position) {
        return mClassificationArrayList.get(position).getGenreList();
    }

    @Override
    public ArrayList<Genre> getGenres(String name) {
        for (Classification c : mClassificationArrayList) {
            if (c.getClassificationName().equals(name)) {
                return c.getGenreList();
            }
        }
        return null;
    }

    @Override
    public FragmentManager getViewSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    @Override
    public String getClassificationName(int position) {
        return mClassificationArrayList.get(position).getClassificationName();
    }

    @Override
    public void refreshClassificationList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public ArrayList<Classification> getClassifications() {
        return this.mClassificationArrayList;
    }


    @Override
    public void setPresenter(ClassificationViewContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    public static class ClassificationRecyclerAdapter extends RecyclerView.Adapter<ClassificationRecyclerAdapter.ClassificationViewHolder> {
        ArrayList<Classification> mClassificationsList;
        ClassificationViewContract.View mOnClickListener;

        public ClassificationRecyclerAdapter(ArrayList<Classification> list, ClassificationViewContract.View onClickListener) {
            this.mOnClickListener = onClickListener;
            this.mClassificationsList = list;
        }

        @NonNull
        @Override
        public ClassificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ClassificationViewHolder holder = new ClassificationViewHolder(inflater.inflate(R.layout.classification_card, parent, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ClassificationViewHolder holder, int position) {
            Classification classification = mClassificationsList.get(position);
            holder.classificationText.setText(classification.getClassificationName());
            holder.chosenGenresText.setText(classification.getChosenGenreText());
            //image will be added later
        }

        @Override
        public int getItemCount() {
            return mClassificationsList.size();
        }

        public class ClassificationViewHolder extends RecyclerView.ViewHolder {
            TextView classificationText;
            TextView chosenGenresText;
            ImageView classificationImage;

            public ClassificationViewHolder(final View itemView) {
                super(itemView);
                itemView.findViewById(R.id.classificationCardId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnClickListener.onClassificationClick(getAdapterPosition(), itemView);
                    }
                });
                classificationText = (TextView) itemView.findViewById(R.id.classificationId);
                chosenGenresText = (TextView) itemView.findViewById(R.id.textAnimeChosenId);
                classificationImage = (ImageView) itemView.findViewById(R.id.classificationImageId);
            }
        }


    }


}
