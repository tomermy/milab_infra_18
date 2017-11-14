package com.example.tomermai.ex02;


import android.os.Bundle;
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
        this.mFiguresList = new GameOfThronesFigure[7];
        mFiguresList[0] = new GameOfThronesFigure("Eddard");
        mFiguresList[1] = new GameOfThronesFigure("Arya");
        mFiguresList[2] = new GameOfThronesFigure("Sansa");
        mFiguresList[3] = new GameOfThronesFigure("Robb");
        mFiguresList[4] = new GameOfThronesFigure("Bran");
        mFiguresList[5] = new GameOfThronesFigure("Rickon");
        mFiguresList[6] = new GameOfThronesFigure("Jon (Snow)");
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
