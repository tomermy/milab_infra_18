package com.example.tomermai.ex02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    View mainViewFragment;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewFragment = inflater.inflate(R.layout.fragment_main, container, false);

        Button showTextButton = mainViewFragment.findViewById(R.id.show_main_text_b);
        Button lannisterListButton = mainViewFragment.findViewById(R.id.lannister_button);
        Button starkListButton = mainViewFragment.findViewById(R.id.stark_button);

        final TextView promptTextView = mainViewFragment.findViewById(R.id.text_prompt_home);
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        // todo: add Lannister listPage fragment - DONE!
        final StarkRecyclerFragment starksListPage = new StarkRecyclerFragment();
        final LannisterRecyclerFragment lannisterListPage = new LannisterRecyclerFragment();

        showTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String promptText = promptTextView.getText().toString();
                Toast.makeText(view.getContext(), promptText, Toast.LENGTH_SHORT).show();
            }
        });

        starkListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, starksListPage);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //  todo: Add Lannister listener function - DONE!
        lannisterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.fragment_container, lannisterListPage);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return mainViewFragment;
    }
}
