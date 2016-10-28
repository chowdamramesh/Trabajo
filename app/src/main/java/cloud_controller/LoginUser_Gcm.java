package cloud_controller;

import android.os.AsyncTask;

import com.example.chandra.trabajo.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;

import application.backend.myApi.MyApi;

/**
 * Created by chandra on 7/6/16.
 */
public class LoginUser_Gcm extends AsyncTask<ArrayList<String>,Void,User> {
    private static MyApi myApiService = null;
    User usrObj;
    @Override
    protected User doInBackground(ArrayList<String>... params) {
        ArrayList<String> got = params[0];
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

        try {
            if(myApiService.loginUsergcm(got.get(0).toString(),got.get(1).toString()).execute() != null) {
                usrObj = new User(got.get(0).toString(),got.get(1).toString());
                return usrObj;
            } else {
                return null;
            }
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(User aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
