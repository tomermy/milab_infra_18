package com.example.tomermai.androidUtils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tomermai.androidUtils.fragments.FamilyListFragment;

public class FamilyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);

        // Create a new Fragment to be placed in the activity layout
        FamilyListFragment familyListFragment = new FamilyListFragment();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        familyListFragment.setArguments(intent.getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.family_fragment_container, familyListFragment).commit();
    }
}
