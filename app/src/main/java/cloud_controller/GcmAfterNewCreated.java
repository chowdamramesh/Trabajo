package cloud_controller;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gcm.backend.messaging.Messaging;

/**
 * Created by chandra on 3/6/16.
 */
public class GcmAfterNewCreated extends AsyncTask<List<String>, Void, Void> {
    private static Messaging msgg = null;
    @Override
    protected Void doInBackground(List<String>... params) {
        if(msgg == null) {  // Only do this once
            Messaging.Builder builder = new Messaging.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://3-dot-esoteric-source-131610.appspot.com/_ah/api/")
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            msgg = builder.build();
        }

//        context = params[0].first;
        List<String> from = new ArrayList<>();
        from = params[0];
        String usr = from.get(1).toString();
        String msg = from.get(0).toString();
//        String newProject = "New Project Created  "+params[0].toString();
        try {
            msgg.messagingEndpoint().sendMessageAccToUsrname(msg,usr).execute();
//            msgg.messagingEndpoint().sendMessage(newProject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
