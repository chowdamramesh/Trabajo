package cloud_controller;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.backend.myApi.MyApi;
import application.backend.myApi.model.ProjectDetails;

/**
 * Created by chandra on 10/6/16.
 */
public class ProjectRetrieveAsync extends AsyncTask<List<String>,Void,ProjectDetails> {
    private static MyApi myApiService = null;

    @Override
    protected ProjectDetails doInBackground(List<String>... params) {
        List<String> got = params[0];
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
        List<String> usrMsg = new ArrayList<>();
        ProjectDetails projLoad = new ProjectDetails();
//        String result = null;
        try {
            projLoad = myApiService.projectRetrieve(got.get(0).toString(), got.get(1).toString()).execute().clone();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projLoad;
    }

    @Override
    protected void onPostExecute(ProjectDetails strings) {
        super.onPostExecute(strings);
    }
}
