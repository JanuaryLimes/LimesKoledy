package januarylimes.limeskoledy.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import januarylimes.limeskoledy.R;

public class SettingsActivity extends BaseActivity {

    private EditText mEditText;
    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettings = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);

        Button mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RETURNING_FROM_SETTINGS, true);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        Switch mSwitch = (Switch) findViewById(R.id.theme_switch);
        mSwitch.setChecked(mSettings.getBoolean(IS_LIGHT_THEME, false));
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(IS_LIGHT_THEME, b);
                editor.apply();
            }
        });

        mEditText = (EditText) findViewById(R.id.edit_fontsize);
        Integer previousSize = mSettings.getInt(FONT_SIZE, 14);
        mEditText.setText(previousSize.toString(), TextView.BufferType.EDITABLE);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer size = 14;
                try {
                    size = Integer.parseInt(mEditText.getText().toString());
                } catch (Exception e) {
                }
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt(FONT_SIZE, size);
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
