package com.example.tomermai.ex02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FamilyListFragment extends Fragment {
    View familyViewFragment;

    public FamilyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        familyViewFragment = inflater.inflate(R.layout.fragment_family, container, false);

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        final StarkRecyclerFragment starksListPage = new StarkRecyclerFragment();
        final LannisterRecyclerFragment lannisterListPage = new LannisterRecyclerFragment();

        int familyID = getActivity().getIntent().getIntExtra(MainActivityFragment.EXTRA_FAMILY, 1);

        if (familyID == MainActivityFragment.LANNISTER_ID) {
            transaction.replace(R.id.family_fragment_container, lannisterListPage);
            transaction.commit();
        } else {
            transaction.replace(R.id.family_fragment_container, starksListPage);
            transaction.commit();
        }

        return familyViewFragment;
    }
}
