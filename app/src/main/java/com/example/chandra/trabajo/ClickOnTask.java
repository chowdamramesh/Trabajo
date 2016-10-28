package com.example.chandra.trabajo;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import application.backend.myApi.model.ProjectDetails;
import application.backend.myApi.model.TaskDetails;
import application.backend.myApi.model.UserDetialsGcm;
import cloud_controller.ProjectRetrieveAsync;
import cloud_controller.TaskRetriveAsync;

public class ClickOnTask extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public TaskDetails taskFromDatastoree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_click_on_task);

        try {
            taskFromDatastoree = new TaskRetriveAsync().execute(getIntent().getStringExtra("val").toString()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Task";


            }
            return null;
        }
    }


    public class WallFragment extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        ArrayAdapter<String> projectAdapter;
        public TaskDetails taskFromDatastore = taskFromDatastoree;
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


            if( taskFromDatastore == null) {

            } else {
                rammi.add(taskFromDatastore.getTaskName());
            }



            View rootView = inflater.inflate(R.layout.fragment_list00, container, false);



            TextView projName = (TextView) rootView.findViewById(R.id.taskName);
            TextView desc = (TextView) rootView.findViewById(R.id.desc);
            TextView startDate = (TextView) rootView.findViewById(R.id.startDate);
            TextView endDate = (TextView) rootView.findViewById(R.id.endDate);

            projName.setText(taskFromDatastore.getTaskName());
            desc.setText(taskFromDatastore.getTaskDesc());
            startDate.setText(taskFromDatastore.getStartDate().toString());
            endDate.setText(taskFromDatastore.getEndDate().toString());
            return rootView;
        }

    }

}
