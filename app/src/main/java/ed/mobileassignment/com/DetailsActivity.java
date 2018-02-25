package ed.mobileassignment.com;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final POI poi = Parcels.unwrap(getIntent().getParcelableExtra("POI"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DetailsActivity.this, MapsActivity.class);
                myIntent.putExtra("POI",Parcels.wrap(poi));
                startActivity(myIntent);
            }
        });

        SetUI(poi);
    }

    private void SetUI(final POI poi) {

        ImageView imgPlace = findViewById(R.id.imgPlace);
        CollapsingToolbarLayout toolbar = findViewById(R.id.toolbar_layout);
        TextView txtDesc = findViewById(R.id.txtDescription);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtHour = findViewById(R.id.txtBusiness);
        SpannableStringBuilder sp = new SpannableStringBuilder();

        //Rendo invisibile il titolo
        toolbar.setTitle(poi.getName());

        //Carico sull'immagine
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimgplace);
        requestOptions.error(R.drawable.noimgplace);
        Glide.with(this ).applyDefaultRequestOptions(requestOptions).load(poi.getImagePath()).into(imgPlace);

        //Inserisco titolo e address
        Spannable title = new SpannableString(poi.getName());
        title.setSpan(new StyleSpan(Typeface.BOLD),0,title.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.append(title);
        Spannable address = new SpannableString(poi.getAddress());
        address.setSpan(new StyleSpan(Typeface.NORMAL),0,address.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        address.setSpan(new RelativeSizeSpan(0.8f), 0,address.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.append("\n"+address);
        txtName.setText(sp);
        sp.clear();

        //Set Description
        txtDesc.setText(poi.getDescription());

        //Set turni
        String[] days = new String[] {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
        for(String day : days) {
            //Ottiene i giorni
            Spannable spDay = new SpannableString(day);
            spDay.setSpan(new StyleSpan(Typeface.BOLD),0,day.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.append(spDay);
            sp.append("\n");

            //Imposta l'orario giornaliero
            String[] turni = poi.businessHours.get(day);
            Spannable spTurni = new SpannableString(turni[0] + "\n" + turni[1]);
            spTurni.setSpan(new StyleSpan(Typeface.NORMAL),0,spTurni.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spTurni.setSpan(new RelativeSizeSpan(0.8f), 0,spTurni.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.append(spTurni);
            sp.append("\n" +"\n");
        }
        txtHour.setText(sp);

    }
}
