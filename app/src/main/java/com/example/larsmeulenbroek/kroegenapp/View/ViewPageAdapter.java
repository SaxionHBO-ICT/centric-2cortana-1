package com.example.larsmeulenbroek.kroegenapp.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.larsmeulenbroek.kroegenapp.Fragments.BarListFragment;
import com.example.larsmeulenbroek.kroegenapp.Fragments.LoginActivityFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BarListFragment();
            case 1:
                return new LoginActivityFragment();
            default: return new BarListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;           // As there are only 2 Tabs
    }

}
