package com.example.ingredient;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VPAdapter extends FragmentPagerAdapter {

    public VPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // position에 해당하는 Fragment 반환
        switch (position) {
            case 0:
                return new fridgeFragment();
            case 1:
                return new freezerFragment();
            case 2:
                return new temperatureFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // ViewPager에 표시할 Fragment 개수 반환
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // position에 해당하는 탭 제목 반환
        switch (position) {
            case 0:
                return "냉장실";
            case 1:
                return "냉동실";
            case 2:
                return "실온";
            default:
                return null;
        }
    }
}
