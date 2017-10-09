package com.example.saksham.notemakerclipboard.Views.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.utils.SliderPrefManager;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager vpSlider;
    Button btnNext, btnSkip;
    SliderPrefManager prefManager;
    private int[] layouts;
    MyViewPagerAdapter myViewPagerAdapter;
    private TextView[] dots;
    LinearLayout llDotLayout, llSlider;
    boolean isHelperMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO check
        setContentView(R.layout.activity_welcome);

        this.initialise();

        //checking the intent
        Intent i = getIntent();
        if (i != null && i.getBooleanExtra("key", false)) {
            isHelperMode = true;
            //show this activity for help
        } else {
            this.showSlider();
        }
        this.changeNotificationBarColor();


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        //adding listener for buttons
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSplash();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    vpSlider.setCurrentItem(current);
                } else {
                    launchSplash();
                }
            }
        });

    }

    private void initialise() {

        vpSlider = (ViewPager) findViewById(R.id.vpSlider);
        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnNext = (Button) findViewById(R.id.btnNext);
        prefManager = new SliderPrefManager(this);
        llDotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        llSlider = (LinearLayout) findViewById(R.id.llSlider);

        // layouts of all welcome sliders
        // add few more layouts if you want

        layouts = new int[]{
                R.layout.slider_activity_one,
                R.layout.slider_activity_two,
                R.layout.slider_activity_three};

        myViewPagerAdapter = new MyViewPagerAdapter();
        vpSlider.setAdapter(myViewPagerAdapter);
        vpSlider.addOnPageChangeListener(viewPagerPageChangeListener);
        this.addBottomDots(0);

    }

    public void showSlider() {


        if (prefManager.isFirstTimeLaunch()) {

            prefManager.setFirstTimeLaunch();

        } else {

            this.launchSplash();
        }

    }

    private void launchSplash() {

        if (!isHelperMode) {
            startActivity(new Intent(
                    this, SplashActivity.class
            ));

        } else {
            isHelperMode = false;
            startActivity(new Intent(
                    this, MainActivity.class
            ));
        }
        finish();
    }

    private void addBottomDots(int currentPage) {

        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        llDotLayout.removeAllViews();
        llSlider.setBackgroundColor(colorsActive[currentPage]);

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            llDotLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.WHITE);
    }

    private int getItem(int i) {
        return vpSlider.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeNotificationBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * View pager adapter
     */

    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}