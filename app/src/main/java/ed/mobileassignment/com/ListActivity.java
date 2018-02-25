package ed.mobileassignment.com;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ListPoiAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List lstPOI;
    private GlobalVar Var;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.rcvList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ListPoiAdapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        Var = (GlobalVar)getApplicationContext();
        lstPOI = Var.getListPOI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final List lstTemp = new ArrayList();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lstTemp.clear();
                for (Object obj: lstPOI) {
                    POI poi = (POI) obj;
                    if (poi.getName().toLowerCase().startsWith(newText.toLowerCase())) lstTemp.add(poi); //Uso toLower() per fare la ricerca case insensitive
                }
                mAdapter.setSearchResult(lstTemp);
                return false;
            }
        });
        return true;
    }

}
