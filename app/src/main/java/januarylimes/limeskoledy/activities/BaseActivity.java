package januarylimes.limeskoledy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import januarylimes.limeskoledy.R;

public class BaseActivity extends AppCompatActivity {

    public static final String IS_LIGHT_THEME = "IS_LIGHT_THEME";
    public static final String FONT_SIZE = "FONT_SIZE";
    public static final Integer SETTINGS_ACTIVITY_REQUEST_CODE = 1;
    public static final String RETURNING_FROM_SETTINGS = "RETURNING_FROM_SETTINGS";
    public static final String TAG_INFORMATION = "----- informacja";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setProperTheme();
    }

    private void setProperTheme() {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        if (mSettings.getBoolean(IS_LIGHT_THEME, false)) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
