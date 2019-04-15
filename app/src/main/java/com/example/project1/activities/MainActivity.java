package com.example.project1.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.project1.fragments.ContactUs;
import com.example.project1.fragments.Explore;
import com.example.project1.fragments.Home;
import com.example.project1.fragments.ImagesFragment;
import com.example.project1.fragments.Map;
import com.example.project1.fragments.Mapplace;
import com.example.project1.fragments.Multimedia;
import com.example.project1.R;
import com.example.project1.fragments.Setting;
import com.example.project1.fragments.TripPlanner;

public class MainActivity extends AppCompatActivity implements TripPlanner.OnFragmentInteractionListener ,
        ImagesFragment.OnFragmentInteractionListener{
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerlayout =  findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);

         nvDrawer = findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flcontent,new Home());
        ft.commit();

    }

    public void selectItemDrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = Home.class;
                break;
            case R.id.explore:
                fragmentClass = Explore.class;
                break;
            case R.id.Tripplanner:
                fragmentClass = TripPlanner.class;
                break;
            case R.id.Media:
                fragmentClass = Multimedia.class;
                break;
            case R.id.map:
                fragmentClass = Map.class;
                break;
            case R.id.cus:
                fragmentClass = ContactUs.class;
                break;
            case R.id.settings:
                fragmentClass = Setting.class;
                break;
            default:
                fragmentClass = Home.class;

        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        assert myFragment != null;
        fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
