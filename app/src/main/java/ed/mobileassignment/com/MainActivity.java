package ed.mobileassignment.com;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    CardView cvMap;
    CardView cvList;
    GlobalVar Var;
    ProgressDialog progWaitPOI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Var=((GlobalVar)getApplicationContext());

        //Setta il layout dell'activity main
        SetUI();
        progWaitPOI = ProgressDialog.show(this, "Updating data",
                "Updating data. Please wait", true);
        UpdateListPoi();
        UpdateUI(false);

    }

    private void SetUI() {
        cvMap = findViewById(R.id.cvMap);
        cvList = findViewById(R.id.cvList);

        cvMap.setCardBackgroundColor(0xffffab41);
        cvList.setCardBackgroundColor(0xFF23334d);
    }

    //Sblocca i 2 tasti una volta elaborata la lista dei POI
    private void UpdateUI(boolean enabled) {
        cvMap.setEnabled(enabled);
        cvList.setEnabled(enabled);
    }

    private void UpdateListPoi() {
       //Inzializzo il client e eseguo la chiamata
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("auth", "application/json; charset=utf-8")
                .url("http://www.skylabs.it/mobileassignment/assignment1.json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                DownloadFinish(false);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    DownloadFinish(false);
                    throw new IOException("Unexpected code " + response);

                } else {
                    List lstPOI = Var.getListPOI();

                    //Estraggo i dati dal json {/res: /data:}
                    try {
                        POIBuilder pb = new POIBuilder();
                        JSONObject jObj = new JSONObject(response.body().string());
                        JSONArray data = jObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jPOI = data.getJSONObject(i);
                            pb.name(jPOI.getString("name"));
                            pb.lat(jPOI.getDouble("lat"));
                            pb.lng(jPOI.getDouble("lng"));
                            pb.address(jPOI.getString("address"));
                            pb.imagePath(jPOI.getString("imagePath"));

                            HashMap<String, String[]> businessHours= new HashMap<String, String []>();
                            JSONArray jHour = jPOI.getJSONArray("businessHours");
                            //Formatto gli orari
                            for (int j = 0; j<jHour.length(); j++) {
                                JSONObject jday = jHour.getJSONObject(j);
                                String day = jday.getString("day");
                                String turni[] = new String[] {"",""};
                                if (!(jday.isNull("schedules"))) {
                                    JSONArray jschedules = jday.getJSONArray("schedules");
                                    turni[0] = jschedules.getString(0);
                                    turni[1] = jschedules.getString(1);
                                }
                                businessHours.put(day,turni);
                            }
                            pb.businessHours(businessHours);
                            lstPOI.add(pb.build());
                        }
                        // Se ho ottenuto un risultato sblocco l'UI.
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    DownloadFinish(lstPOI.size() > 0);
                }
            }
        });
    }

    private void DownloadFinish (final boolean success) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UpdateUI(success);
                progWaitPOI.dismiss();
            }
        });
    }

}
