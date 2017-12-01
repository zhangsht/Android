package com.example.zhang.integration;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.zhang.fragment.HomeFragment;
import com.example.zhang.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment mHomeFragment;
    private SettingFragment mSettingFragment;
    private int mFragmentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        onTabSelected(mFragmentPosition);
    }

    private void bindView() {
        bottomNavigationBar = findViewById(R.id.btmNVB);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_black_24dp, "Home").setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.ic_settings_black_24dp, "Setting").setActiveColor(R.color.turquoise))
                .addItem(new BottomNavigationItem(R.mipmap.ic_more_horiz_black_24dp, "More").setActiveColor(R.color.blue))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mFragmentPosition = savedInstanceState.getInt("fragmentPosition");
        onTabSelected(mFragmentPosition);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("fragmentPosition", mFragmentPosition);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onTabSelected(int position) {
        mFragmentPosition = position;
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        for (android.support.v4.app.Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragmentTransaction.hide(fragment);
        }
        switch (position) {
            case 0:
                setTitle(R.string.fg_Home);

                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance(getString(R.string.fg_Home));
                    fragmentTransaction.add(R.id.fg_container, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case 1:
                setTitle(R.string.fg_Setting);

                if (mSettingFragment == null) {
                    mSettingFragment = SettingFragment.newInstance();
                    fragmentTransaction.add(R.id.fg_container, mSettingFragment);
                } else {
                    fragmentTransaction.show(mSettingFragment);
                }
                break;
            case 2:
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
