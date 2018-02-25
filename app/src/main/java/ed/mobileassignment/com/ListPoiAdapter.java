package ed.mobileassignment.com;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by marco on 24/02/2018.
 */

public class ListPoiAdapter extends RecyclerView.Adapter<ListPoiAdapter.ViewHolder> {
    private GlobalVar Var;
    private List lstPOI;
    private Context context;

    public ListPoiAdapter(Context mcontext) {
        this.context = mcontext;
        Var=((GlobalVar)mcontext);
        lstPOI = Var.getListPOI();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageView imgBack;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgBack = itemView.findViewById(R.id.imgBack);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_poi, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListPoiAdapter.ViewHolder holder, int position) {
        final POI poi = (POI) lstPOI.get(position);
        //Setto il nome del POI
        holder.txtTitle.setText(poi.getName());

        //Carico l'immagine se disponibile. Non ho inserito un placeholder o error perchè utilizzo direttamente il backround in caso di assenza immagine
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        Glide.with(this.context).applyDefaultRequestOptions(requestOptions).load(poi.getImagePath()).into(holder.imgBack);

        //Setto l'evento click per aprire l'activity di dettaglio
        holder.txtTitle.setTag(poi);
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, DetailsActivity.class);
                myIntent.putExtra("POI", Parcels.wrap(poi));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstPOI.size();
    }


}
