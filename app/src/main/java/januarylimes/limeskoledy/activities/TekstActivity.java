package januarylimes.limeskoledy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import januarylimes.limeskoledy.R;
import januarylimes.limeskoledy.model.Koleda;

public class TekstActivity extends BaseActivity {

    Koleda carol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekst);
        carol = (Koleda) getIntent().getSerializableExtra("koleda");
        prepareActivity();
    }

    private void prepareActivity() {
        setAppBar();
        setTextView();
    }

    private void setAppBar() {
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(carol.getNazwa());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setTextView() {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(carol.getTekst());
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        Integer size = mSettings.getInt(FONT_SIZE, 14);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}
