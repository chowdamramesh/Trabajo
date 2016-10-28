package cloud_controller;

import android.os.AsyncTask;

import com.example.chandra.trabajo.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.backend.myApi.MyApi;
import application.backend.myApi.model.TaskDetails;
import application.backend.myApi.model.UserDetialsGcm;
//import gcm.backend.registration.model.UserDetialsGcm;

/**
 * Created by chandra on 10/6/16.
 */
public class ProMembersRetrive extends AsyncTask<String,Void,List<UserDetialsGcm>> {
    private static MyApi myApiService = null;
    User usrObj;
    @Override
    protected List<UserDetialsGcm> doInBackground(String... params) {
        String got = params[0].toString();
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
        List<UserDetialsGcm> allGot = new ArrayList<>();

        try {
            allGot = myApiService.proMembers(got).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allGot;
    }
}
