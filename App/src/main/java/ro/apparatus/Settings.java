package ro.apparatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class Settings extends Activity {
    private static final String PREF_INTERVAL="Interval";
    SharedPreferences prefs;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ctx = this;
        prefs = getSharedPreferences(Settings.class.getSimpleName(), Context.MODE_PRIVATE);
        findViewById(R.id.settingsSaveBTN).setOnClickListener(settingsSaveOCL);
    }
    OnClickListener settingsSaveOCL = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewGroup vg = (ViewGroup) findViewById(R.id.settingsRG);
            for(int i=0;i<vg.getChildCount();i++){
                View view = vg.getChildAt(i);
                if(((RadioButton) view).isChecked()){
                    // save to SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    switch(i){
                        // the buttons order remains the same(2,5,15)
                        case 0:
                            editor.putInt(PREF_INTERVAL, 2);
                            break;
                        case 1:
                            editor.putInt(PREF_INTERVAL, 5);
                            break;
                        case 2:
                            editor.putInt(PREF_INTERVAL, 15);
                            break;
                        default:
                            break;
                    }
                    editor.commit();
                    Intent toMainActivity = new Intent(ctx, MainActivity.class);
                    startActivity(toMainActivity);
                    finish();
                }
            }

        }
    };
}
