package com.example.larsmeulenbroek.kroegenapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.larsmeulenbroek.kroegenapp.Fragments.BarListFragment;
import com.example.larsmeulenbroek.kroegenapp.R;
import com.example.larsmeulenbroek.kroegenapp.Tasks.LoadBarDetailActivityTask;
import com.example.larsmeulenbroek.kroegenapp.View.ViewPageAdapter;


public class MainActivity extends AppCompatActivity implements BarListFragment.CallBacks {

    //Declaring All The Variables Needed
    public static String BAR_POSITION = "BAR_POSITION";
    public static String BAR_POSITION_BUNDLE = "BAR_POSITION_BUNDLE";
    public static int BAR_DETAIL_REQUEST = 1111;

    public static String ROUTE_POSITION = "ROUTE_POSITION";
    public static String ROUTE_POSITION_BUNDLE = "ROUTE_POSITION_BUNDLE";
    public static int ROUTE_DETAIL_REQUEST = 2222;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;

    public static boolean userIsLoggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Assigning view variables to thier respective view in xml
        by findViewByID method
         */

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        /*
        Creating Adapter and setting that adapter to the viewPager
        setSupportActionBar method takes the toolbar and sets it as
        the default action bar thus making the toolbar work like a normal
        action bar.
         */
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        setSupportActionBar(toolbar);

        /*
        TabLayout.newTab() method creates a tab view, Now a Tab view is not the view
        which is below the tabs, its the tab itself.
         */

        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab profile = tabLayout.newTab();

        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(home, 0);
        tabLayout.addTab(profile, 1);


        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.

        2nd We add the onPageChangeListener to change the icon when the page changes in the view Pager
         */

        home.setIcon(R.drawable.home_icon_white);
        profile.setIcon(R.drawable.profile_icon_grey);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        /*
                        setting Home as White and rest grey
                        and like wise for all other positions
                         */
                        home.setIcon(R.drawable.home_icon_white);
                        profile.setIcon(R.drawable.profile_icon_grey);
                        break;
                    case 1:
                        home.setIcon(R.drawable.home_icon_grey);
                        profile.setIcon(R.drawable.profile_icon_white);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up btn_GoToWeb, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBarItemClick(int position) {
        LoadBarDetailActivityTask lbd = new LoadBarDetailActivityTask(getApplicationContext());
        lbd.execute(position);
    }
}