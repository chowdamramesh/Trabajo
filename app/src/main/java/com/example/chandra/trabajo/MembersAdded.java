package com.example.chandra.trabajo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cloud_controller.ReteiveMemAccToUser;

import application.backend.myApi.MyApi;
import application.backend.myApi.model.ProjectDetails;
import application.backend.myApi.model.TaskDetails;
import application.backend.myApi.model.UserDetialsGcm;

public class MembersAdded extends AppCompatActivity implements View.OnClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public List<UserDetialsGcm> fromDatastoreMemss;
    public String usrnameee;

//    UserLocalStore store;
//    TextView txtView;
//    public String usrnameee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            fromDatastoreMemss = new ReteiveMemAccToUser().execute(getIntent().getStringExtra("username").toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_members_added);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the four
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//        Toast.makeText(MembersAdded.this, "Mem added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        usrnameee = getIntent().getStringExtra("username").toString();
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0)
                return new WallFragment().newInstance(position + 1);

            else
                return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Members In PlayArea";

            }
            return null;
        }
    }

    public class WallFragment extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public  List<UserDetialsGcm> fromDatastoreMems = fromDatastoreMemss;
        ArrayAdapter<String> projectAdapter;

        private final String ARG_SECTION_NUMBER = "section_number";

        public WallFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public  WallFragment newInstance(int sectionNumber) {
            WallFragment fragment = new WallFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            java.util.List<String> rammi = new ArrayList<>();

//            rammi.add("purna");
//            rammi.add("bharath");

            if( fromDatastoreMems == null|| fromDatastoreMems.isEmpty()) {

            } else {
                for (UserDetialsGcm a:fromDatastoreMems) {
                    rammi.add(a.getUsername());
                }
            }


            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
            projectAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_viewfragment, // The name of the layout ID.
                            R.id.list_item_textview, // The ID of the textview to populate.
                            rammi);


            View rootView = inflater.inflate(R.layout.fragment_list, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.list);
            listView.setAdapter(projectAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = (String) parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),NotifyMsgToSelelcted.class);
                    i.putExtra("username",usrnameee);
                    i.putExtra("toUser",selected);
                    startActivity(i);
                }
            });



            return rootView;
        }

    }
}
