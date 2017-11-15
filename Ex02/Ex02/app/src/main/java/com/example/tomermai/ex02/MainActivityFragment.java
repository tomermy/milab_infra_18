package com.example.tomermai.ex02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static final int STARKS_ID = 1;
    public static final int LANNISTER_ID = 2;
    public static final String EXTRA_FAMILY = "com.example.tomermai.ex2.FAMILY";
    View mainViewFragment;

    public MainActivityFragment() {
    }

    /** Called when the user taps the families button */
    public void startFiguresActivity(View view, int familyID) {
        Intent intent = new Intent(this.getActivity(), FamilyListActivity.class);
        intent.putExtra(EXTRA_FAMILY, familyID);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainViewFragment = inflater.inflate(R.layout.fragment_main, container, false);

        Button showTextButton = mainViewFragment.findViewById(R.id.show_main_text_b);
        Button lannisterListButton = mainViewFragment.findViewById(R.id.lannister_button);
        Button starkListButton = mainViewFragment.findViewById(R.id.stark_button);

        final TextView promptTextView = mainViewFragment.findViewById(R.id.text_prompt_home);

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
                startFiguresActivity(v, STARKS_ID);
            }
        });

        lannisterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFiguresActivity(v, LANNISTER_ID);
            }
        });

        return mainViewFragment;
    }
}
