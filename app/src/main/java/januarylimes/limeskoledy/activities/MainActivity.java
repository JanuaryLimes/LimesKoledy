package januarylimes.limeskoledy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import januarylimes.limeskoledy.R;
import januarylimes.limeskoledy.adapters.CarolAdapter;
import januarylimes.limeskoledy.model.Koleda;
import januarylimes.limeskoledy.xmlHelper.XmlHelper;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareActivity();
    }

    ArrayList<Koleda> carolList;
    CarolAdapter adapter;
    RecyclerView recyclerView;

    private void prepareActivity() {
        loadCarols();
        prepareRecycler();
        prepareToolbar();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        MenuItem settingsItem = toolbar.getMenu().findItem(R.id.action_settings);
        settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(i, SETTINGS_ACTIVITY_REQUEST_CODE);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG_INFORMATION, "po super w onactivityresult");

        if (requestCode == SETTINGS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Boolean result = data.getBooleanExtra(RETURNING_FROM_SETTINGS, false);
                if (result) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recreate();
                        }
                    }, 1);
                }
            }
        }
    }

    private void prepareRecycler() {
        adapter = new CarolAdapter(this, carolList);
        recyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadCarols() {
        XmlHelper xmlHelper = new XmlHelper(getAssets());
        carolList = xmlHelper.getListeKoled();

        Collections.sort(carolList, new Comparator<Koleda>() {
            Collator collator = Collator.getInstance(new Locale("pl", "PL"));

            @Override
            public int compare(Koleda k1, Koleda k2) {
                return collator.compare(k1.getNazwa(), k2.getNazwa());
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Koleda> filteredModelList = filter(carolList, newText);
        adapter = new CarolAdapter(this, filteredModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<Koleda> filter(List<Koleda> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Koleda> filteredModelList = new ArrayList<>();
        for (Koleda model : models) {
            final String text = model.getNazwa().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
