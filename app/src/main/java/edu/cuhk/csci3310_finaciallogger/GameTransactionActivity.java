package edu.cuhk.csci3310_finaciallogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GameTransactionActivity extends AppCompatActivity {
    private static final String SPINNING_WHEEL_FRAGMENT_TAG = "spinning_wheel_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_transaction);

        //Setting up a back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Setting up the navigation buttons
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.spinning_wheel_nav_button) {
                    displaySpinningWheelFragment();
                    return true;
                }
                if (item.getItemId() == R.id.buy_wheels_nav_button) {
                    displayBuyWheelsFragment();
                    return true;
                }
                return false;
            }
        });

        //Setting up the fragments
        SpinningWheelFragment spinningWheelFragment = SpinningWheelFragment.newInstance("", "");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.transaction_frame_layout, spinningWheelFragment, SPINNING_WHEEL_FRAGMENT_TAG);
        fragmentTransaction.commitNow();

        bottomNavigation.setSelectedItemId(R.id.spinning_wheel_nav_button);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    private void displayBuyWheelsFragment() {
        //Hiding the spinning wheel fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(fragmentManager.findFragmentByTag(SPINNING_WHEEL_FRAGMENT_TAG));
        fragmentTransaction.commit();
    }

    private void displaySpinningWheelFragment() {
        //Showing the spinning wheel fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragmentManager.findFragmentByTag(SPINNING_WHEEL_FRAGMENT_TAG));
        fragmentTransaction.commit();
    }

}