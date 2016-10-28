package cloud_controller;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.util.DateTime;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import application.backend.myApi.MyApi;

/**
 * Created by chandra on 8/6/16.
 */

public class CreateNewProjectAsync extends AsyncTask<ArrayList<String>,Void,ArrayList<String>> {
    private static MyApi myApiService = null;

    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... params) {
        ArrayList<String> got = params[0];
        if (myApiService == null) {  // Only do this once
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

//        System.out.println(got);
//        return "checking";
//        2015-06-09T00:00:00.000Z
//        Date newAa = new Date((got.get(3)+"T00:00:00.000Z").toString());
        DateTime nnA = new DateTime((got.get(3) + "T00:00:00.000Z").toString());

//        Date newAb = new Date((got.get(4)+"T00:00:00.000Z").toString());
        DateTime nnB = new DateTime((got.get(4) + "T00:00:00.000Z").toString());

        ArrayList<String> usrMsg = new ArrayList<>();


//        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-ss");
//        DateTime dt = formatter.parseDateTime(got.get(3).toString());

            String result = null;
            try {
                result = myApiService.createProject(got.get(0).toString(), got.get(1).toString(), got.get(2).toString(), nnA, nnB).execute().getProjName();
            } catch (IOException e) {
                e.printStackTrace();
            }
            usrMsg.add(got.get(0).toString());
            usrMsg.add("new project " + result + " created");
            return usrMsg;

    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
//        System.out.println("new project " + s + " created");
        super.onPostExecute(s);
        if(!s.isEmpty())
            new GcmAfterNewCreated().execute(s);

    }
}
