package com.example.tomermai.ex02;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        FloatingActionButton showTextButton = mainViewFragment.findViewById(R.id.show_main_text_b);
        final TextView promptTextView = mainViewFragment.findViewById(R.id.text_prompt_home);

        showTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String promptText = promptTextView.getText().toString();
                Toast.makeText(view.getContext(), promptText, Toast.LENGTH_SHORT).show();
            }
        });

        return mainViewFragment;
    }
}
