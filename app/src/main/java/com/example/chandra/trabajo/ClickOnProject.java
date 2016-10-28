package com.example.chandra.trabajo;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import application.backend.myApi.model.ProjectDetails;
import application.backend.myApi.model.TaskDetails;
import application.backend.myApi.model.UserDetialsGcm;
import cloud_controller.ProMembersRetrive;
import cloud_controller.ProTaskRetrieve;
import cloud_controller.ProjectRetrieveAsync;

public class ClickOnProject extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public java.util.List<UserDetialsGcm> userFromDataStoree;
    public ProjectDetails projFromDatastoree;
    public java.util.List<TaskDetails> taskFromDatastoree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_on_project);

//        setContentView(R.layout.activity_members_added);
        List<String> in = new ArrayList<>();
        in.add(getIntent().getStringExtra("username"));
        in.add(getIntent().getStringExtra("val"));

        try {
            projFromDatastoree = new ProjectRetrieveAsync().execute(in).get();
            userFromDataStoree = new ProMembersRetrive().execute(getIntent().getStringExtra("val")).get();
            taskFromDatastoree = new ProTaskRetrieve().execute(getIntent().getStringExtra("val")).get();
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


    @Override
    protected void onStart() {
        super.onStart();
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
            if(position == 1)
                return new WallFragment1().newInstance(position + 1);
            if(position == 2)
                return new WallFragment2().newInstance(position + 1);

            else
                return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Project";
                case 1:
                    return "Tasks";
                case 2:
                    return "Members";

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
        public ProjectDetails projFromDatastore = projFromDatastoree;
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


            if( projFromDatastore == null) {

            } else {
                    rammi.add(projFromDatastore.getProjName());
            }


            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
//            projectAdapter =
//                    new ArrayAdapter<String>(
//                            getActivity(), // The current context (this activity)
//                            R.layout.list_viewfragment0, // The name of the layout ID.
//                            R.id.list_item_textview0, // The ID of the textview to populate.
//                            rammi);


            View rootView = inflater.inflate(R.layout.fragment_list0, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
//            ListView listView = (ListView) rootView.findViewById(R.id.list0);
//            listView.setAdapter(projectAdapter);

            TextView projName = (TextView) rootView.findViewById(R.id.projName);
            TextView desc = (TextView) rootView.findViewById(R.id.desc);
            TextView startDate = (TextView) rootView.findViewById(R.id.startDate);
            TextView endDate = (TextView) rootView.findViewById(R.id.endDate);

            projName.setText(projFromDatastore.getProjName());
            desc.setText(projFromDatastore.getDesc());
            startDate.setText(projFromDatastore.getStartDate().toString());
            endDate.setText(projFromDatastore.getEndDate().toString());
            return rootView;
        }

    }

    public class WallFragment1 extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public List<TaskDetails> taskFromDatastore = taskFromDatastoree;
        ArrayAdapter<String> projectAdapter;

        private final String ARG_SECTION_NUMBER = "section_number";

        public WallFragment1() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public  WallFragment1 newInstance(int sectionNumber) {
            WallFragment1 fragment = new WallFragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            java.util.List<String> ramm = new ArrayList<>();


            if( taskFromDatastore == null|| taskFromDatastore.isEmpty()) {

            } else {
                for (TaskDetails a:taskFromDatastore) {
                    ramm.add(a.getTaskName());
                }
            }


            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
            projectAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_viewfragment1, // The name of the layout ID.
                            R.id.list_item_textview1, // The ID of the textview to populate.
                            ramm);


            View rootView = inflater.inflate(R.layout.fragment_list1, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.list1);
            listView.setAdapter(projectAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = (String) parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),ClickOnTask.class);
                    i.putExtra("val",selected);
                    startActivity(i);
                }
            });

            return rootView;
        }

    }

    public class WallFragment2 extends Fragment {


        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        List<UserDetialsGcm>  userFromDataStore = userFromDataStoree;
        ArrayAdapter<String> projectAdapter;

        private final String ARG_SECTION_NUMBER = "section_number";

        public WallFragment2() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public  WallFragment2 newInstance(int sectionNumber) {
            WallFragment2 fragment = new WallFragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            java.util.List<String> ram = new ArrayList<>();

            if( userFromDataStore == null|| userFromDataStore.isEmpty()) {

            } else {
                for (UserDetialsGcm a:userFromDataStore) {
                    ram.add(a.getUsername());
                }
            }


            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
            projectAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_viewfragment2, // The name of the layout ID.
                            R.id.list_item_textview2, // The ID of the textview to populate.
                            ram);


            View rootView = inflater.inflate(R.layout.fragment_list2, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.list2);
            listView.setAdapter(projectAdapter);


            return rootView;
        }

    }

}
