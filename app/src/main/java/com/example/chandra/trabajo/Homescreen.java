package com.example.chandra.trabajo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
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
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import application.backend.myApi.MyApi;
import application.backend.myApi.model.ProjectDetails;
import application.backend.myApi.model.StoreToDatastore;
import application.backend.myApi.model.TaskDetails;

import static com.example.chandra.trabajo.Homescreen.WallFragment.*;

public class Homescreen extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    Button timLogout;
//    ImageButton addProj;
    UserLocalStore store;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public String usrnameee;

    private List<ProjectDetails> fromDatastore;

    private List<TaskDetails> fromDatastoreTasks;

    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        store = new UserLocalStore(this);
        User user = store.getLoggedInUser();
        usrnameee = user.username;

        try {
            fromDatastore = new AsyncTaskOne().execute(usrnameee).get();
            fromDatastoreTasks = new AsyncTaskTwo().execute(usrnameee).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_homescreen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        navigationView.setNavigationItemSelectedListener(this);

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
        if(authenticate()==true){
            displayUserDetails();
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private  boolean authenticate(){
        return store.getUserLoggedIn();
    }

    private void displayUserDetails(){
        User user = store.getLoggedInUser();
        txtView.setText(user.username);
//        usrnameee = user.username;
//        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.addNewProject:
////                System.out.println(usrnameee);
//                Intent i = new Intent(this,Sidebar.class);
////                System.out.println(usrnameee + "////////////");
//                i.putExtra("username",usrnameee);
//                startActivity(i);
//                break;
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_createProject) {
            Intent i = new Intent(this,Sidebar.class);
            i.putExtra("username",usrnameee);
            startActivity(i);
            // Handle the camera action
//            Intent intent_nav_camera = new Intent(this,cameraActivity.class);
//            startActivity(intent_nav_camera);
        } else  if(id==R.id.nav_createTask){
            Intent i = new Intent(this,TasksActivity.class);
            i.putExtra("username",usrnameee);
            startActivity(i);
        } else if (id == R.id.nav_addMem) {
            Intent i = new Intent(this,Members.class);
            i.putExtra("username",usrnameee);
            startActivity(i);
        } else if(id == R.id.nav_showMem) {
            Intent i = new Intent(this,MembersAdded.class);
            i.putExtra("username",usrnameee);
            startActivity(i);
        } else if (id == R.id.nav_Share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, " Create your projects and track the work done by the members assigned @ Trabajo ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if(id == R.id.nav_logout) {
            store.clearUserData();
            store.setUserLoggedIn(false);
            startActivity(new Intent(this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            if(position==1)
                return new ChatFragment().newInstance(position+1);
            else
                return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Projects";
                case 1:
                    return "Task";

            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class WallFragment extends Fragment {

         ProgressDialog progressDialog;

        public  List<ProjectDetails> allFromDataStore = fromDatastore;



        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
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
            System.out.println("In ON CreateView");

            List<String> rammi = new ArrayList<>();

            if( allFromDataStore == null|| allFromDataStore.isEmpty()) {

            } else {
                for (ProjectDetails a:allFromDataStore) {
                    rammi.add(a.getProjName());
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
                    Intent i = new Intent(getApplicationContext(),ClickOnProject.class);
                    i.putExtra("username",usrnameee);
                    i.putExtra("val",selected);
                    startActivity(i);
                }
            });

            return rootView;
        }

    }

    public class AsyncTaskOne extends AsyncTask<String,Void,List<ProjectDetails>> {
        private  MyApi myApiService = null;
            private String name="aa";

        @Override
        protected List<ProjectDetails> doInBackground(String... params) {
            System.out.println("in doINbackgroud");
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("https://2-dot-esoteric-source-131610.appspot.com/_ah/api/")
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

//                context = params[0].first;
            name = params[0];

            try {
                return myApiService.retriveAccToUser(name).execute().getItems();
            }  catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ProjectDetails> storeToDatastores) {
            System.out.println("IN post execution");
            if(storeToDatastores == null ||storeToDatastores.isEmpty()) {

            }else {
                for (ProjectDetails q : storeToDatastores) {
                    System.out.println(q.getProjName());
                }
            }
        }
    }

    public class AsyncTaskTwo extends AsyncTask<String,Void,List<TaskDetails>> {
        private  MyApi myApiServicee = null;
        private String name="aa";

        @Override
        protected List<TaskDetails> doInBackground(String... params) {
            System.out.println("in doINbackgroud");
            if(myApiServicee == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("https://2-dot-esoteric-source-131610.appspot.com/_ah/api/")
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiServicee = builder.build();
            }

//                context = params[0].first;
            name = params[0];

            try {
                return myApiServicee.retriveTaskAccToUser(name).execute().getItems();
            }  catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<TaskDetails> storeToDatastores) {
            System.out.println("IN post execution");
            if(storeToDatastores == null ||storeToDatastores.isEmpty()) {

            }else {
                for (TaskDetails q : storeToDatastores) {
                    System.out.println(q.getTaskName());
                }
            }
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public class ChatFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public  List<TaskDetails> allTasksFromDataStore = fromDatastoreTasks;

        ArrayAdapter<String> TaskAdapter;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ChatFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public ChatFragment newInstance(int sectionNumber) {
            ChatFragment fragment = new ChatFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            List<String> rammiTasks = new ArrayList<>();
//            allFromDataStore = new ArrayList<>();

//            if(!(allFromDataStore.isEmpty())) {
            if( allTasksFromDataStore == null|| allTasksFromDataStore.isEmpty()) {

            } else {
                for (TaskDetails a:allTasksFromDataStore) {
                    rammiTasks.add(a.getTaskName());
//                    System.out.print(a.getRegId());
                }
            }
            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
            TaskAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_viewfragment, // The name of the layout ID.
                            R.id.list_item_textview, // The ID of the textview to populate.
                            rammiTasks);

            View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.listView2);
            listView.setAdapter(TaskAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = (String) parent.getItemAtPosition(position);
                    Intent i = new Intent(getApplicationContext(),ClickOnTask.class);
                    i.putExtra("username",usrnameee);
                    i.putExtra("val",selected);
                    startActivity(i);
                }
            });
            return rootView;
        }

    }
}
