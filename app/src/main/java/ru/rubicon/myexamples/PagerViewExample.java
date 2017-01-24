package ru.rubicon.myexamples;

/**
 * Created by Витя on 22.09.2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import ru.rubicon.myexamples.fragments.PagerViewFragment1;
import ru.rubicon.myexamples.fragments.PagerViewFragment2;

public class PagerViewExample extends FragmentActivity {

    private MyAdapter mAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pager_view_main);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(1); // выводим второй экран
        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View v, float pos) {
                final float invt = Math.abs(Math.abs(pos) - 1);
                v.setAlpha(invt);
            }
        });
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PagerViewFragment1();
                case 1:
                    return new PagerViewFragment2();
                case 2:
                    return new PagerViewFragment2();

                default:
                    return null;
            }
        }
    }
}