package cloud_controller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import gcm.backend.registration.Registration;
import gcm.backend.registration.model.UserDetialsGcm;

/**
 * Created by chandra on 6/6/16.
 */
public class Gcm_SignUpAsyncTask extends AsyncTask<Pair<Context,ArrayList<String>>, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "894853080214";

//    public Gcm_SignUpAsyncTask(Context context) {
//        this.context = context;
//    }

    @Override
    protected String doInBackground(Pair<Context,ArrayList<String>>... params) {
//        System.out.println("GCM_SignUp doInBackgroud");
        ArrayList<String> obbj = new ArrayList<>();
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://3-dot-esoteric-source-131610.appspot.com/_ah/api/");
            regService = builder.build();
        }
        context = params[0].first;
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            obbj.add(regId);
            obbj.addAll(params[0].second);
//            regService.register(regId).execute();
            UserDetialsGcm aa = new UserDetialsGcm();
            aa.setRegId(obbj.get(0));
            aa.setName(obbj.get(1));
            aa.setUsername(obbj.get(2));
            aa.setPassword(obbj.get(3));
            aa.setMobileNum(obbj.get(4));
            aa.setEmail(obbj.get(5));

            Date aaa = new Date();
            DateTime  dateObj = new DateTime(aaa);
            aa.setMemberSince(dateObj);

            msg = regService.newUserDetials(aa).execute().getUsername();

//            regService.newUserDetials()

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }

        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
//        System.out.println("GCM_SignUp onPostExecution");
//        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}