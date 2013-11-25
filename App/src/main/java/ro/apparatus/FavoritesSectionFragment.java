package ro.apparatus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FavoritesSectionFragment extends ListFragment {
    private static final String TAG = FavoritesSectionFragment.class.getSimpleName();
    public static final String ARG_SECTION_NUMBER = "section_number";
    private static final String PREF_INTERVAL="Interval";
    SharedPreferences prefs, settingsPrefs;
    ArrayAdapter<String> adapter;
    Context ctx;
    Timer t;
    FilesSectionFragment filesFragment;
    public FavoritesSectionFragment(Context context) {
        ctx = context;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, new String[0]);
        setListAdapter(adapter);
        prefs = ctx.getSharedPreferences(FavoritesSectionFragment.class.getSimpleName(), Context.MODE_PRIVATE);
        settingsPrefs = ctx.getSharedPreferences(Settings.class.getSimpleName(), Context.MODE_PRIVATE);
        getItemsFromSharedPreferences();
        Toast.makeText(ctx, "Press files to delete them from favorite list!", Toast.LENGTH_SHORT);
        t = new  Timer();
        t.schedule(new TimerTask() {
            public void run() {
                deleteAllPrefs();
                copyAllPrefs();
                Log.e(TAG, "Done!");
            }
        }, 1000, settingsPrefs.getInt(PREF_INTERVAL, 2)*1000);// delay, period
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String[] stringMinusOne;
        int noOfCells;
        if(!(adapter == null)){
            noOfCells = adapter.getCount();
            stringMinusOne = new String[noOfCells-1];
            int j=0;
            for(int i=0;i<noOfCells;i++){
                if(adapter.getItem(i).equals(((TextView) v).getText().toString())){
                    Toast.makeText(ctx, "Favorite Removed!", Toast.LENGTH_SHORT).show();
                    continue;
                }
                stringMinusOne[j]= adapter.getItem(i);
                j++;
            }
            adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, stringMinusOne);
            setListAdapter(adapter);
        }
    }
    private void getItemsFromSharedPreferences(){

        Map<String,String> map = (Map<String, String>) prefs.getAll();
        String[] prefsArray = new String[map.size()];
        int i=0;
        for (Map.Entry<String, String> entry : map.entrySet()){
            prefsArray[i] = entry.getKey();
            i++;
        }
        adapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_list_item_1, prefsArray);
        setListAdapter(adapter);
    }
    public void addItem(String s){
        String[] stringToAdd;
        int noOfCells;
        if(!(adapter == null)){
            noOfCells = adapter.getCount();
            stringToAdd = new String[noOfCells+1];
            for(int i=0;i<noOfCells;i++){
                if(adapter.getItem(i).equals(s)){
                    Toast.makeText(ctx, "Item already added!", Toast.LENGTH_SHORT).show();
                    return;
                }
                stringToAdd[i] = adapter.getItem(i);
            }
        }
        else{
            stringToAdd = new String[1];
            noOfCells = 0;
        }
        stringToAdd[noOfCells] = s;
        Toast.makeText(ctx, "File added to Favorites!", Toast.LENGTH_SHORT).show();
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, stringToAdd);
        setListAdapter(adapter);
    }
    private void deleteAllPrefs(){
    // we delete the old records and rewrite them
        SharedPreferences.Editor editor = prefs.edit();
        Map<String,String> map = (Map<String, String>) prefs.getAll();
        for (Map.Entry<String, String> entry : map.entrySet()){
            Log.e(TAG, entry.getKey() + "to be deleted!");
            editor.remove(entry.getKey());
        }
        editor.commit();
    }
    private void copyAllPrefs(){
        SharedPreferences.Editor editor = prefs.edit();
        if(!(adapter == null)){
             for(int i=0;i<adapter.getCount();i++){
                editor.putString(adapter.getItem(i), adapter.getItem(i));
            }
        }
        editor.commit();
    }

    @Override
    public void onDestroy() {
        t.cancel();
        super.onDestroy();
    }
}
