package com.example.tomermai.ex02;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StarkRecyclerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private GameOfThronesFigure[] mFiguresList;

    public StarkRecyclerFragment() {
        this.mFiguresList = new GameOfThronesFigure[5];
        mFiguresList[0] = new GameOfThronesFigure("Ned Stark");
        mFiguresList[1] = new GameOfThronesFigure("Arya Stark");
        mFiguresList[2] = new GameOfThronesFigure("Sansa Stark");
        mFiguresList[3] = new GameOfThronesFigure("Rob Stark");
        mFiguresList[4] = new GameOfThronesFigure("Bran Stark");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.stark_list_layout, container, false);
        mRecyclerView = mView.findViewById(R.id.stark_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        // todo: Add adapter - DONE
        mAdapter = new FiguresRecyclerListAdapter(mFiguresList);
        mRecyclerView.setAdapter(mAdapter);
        return mView;
    }
}
