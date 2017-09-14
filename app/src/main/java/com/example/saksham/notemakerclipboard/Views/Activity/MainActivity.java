package com.example.saksham.notemakerclipboard.Views.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Fragment.ClipboardFragment;
import com.example.saksham.notemakerclipboard.Views.Fragment.NotesFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.initialise(); // initialisation for VIEWS

    }

    public void initialise() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotesFragment(), "NOTES");
        adapter.addFragment(new ClipboardFragment(), "CLIPBOARD");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public static final String TAG = "ViewPagerAdapter";
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: ");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: ");
            return mFragmentList.size();
        }

        @Override
        public String getPageTitle(int position) {


            Log.d(TAG, "getPageTitle: ");
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {

            Log.d(TAG, "addFragment: ");
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }
    }

}
