package com.example.tomermai.androidUtils.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomermai.androidUtils.adapters.FiguresRecyclerListAdapter;
import com.example.tomermai.androidUtils.models.GameOfThronesFigure;
import com.example.tomermai.androidUtils.R;

public class LannisterRecyclerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private GameOfThronesFigure[] mFiguresList;

    public LannisterRecyclerFragment() {
        this.mFiguresList = new GameOfThronesFigure[6];
        mFiguresList[0] = new GameOfThronesFigure("Tywin", R.mipmap.tywin);
        mFiguresList[1] = new GameOfThronesFigure("Cersei", R.mipmap.cersei);
        mFiguresList[2] = new GameOfThronesFigure("Jaime", R.mipmap.jaime);
        mFiguresList[3] = new GameOfThronesFigure("Tyrion", R.mipmap.tyrion);
        mFiguresList[4] = new GameOfThronesFigure("Joffrey", R.mipmap.joffrey);
        mFiguresList[5] = new GameOfThronesFigure("Myrcella", R.mipmap.myrcella);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.lannister_list_layout, container, false);
        mRecyclerView = mView.findViewById(R.id.lannister_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FiguresRecyclerListAdapter(mFiguresList);
        mRecyclerView.setAdapter(mAdapter);
        return mView;
    }
}
