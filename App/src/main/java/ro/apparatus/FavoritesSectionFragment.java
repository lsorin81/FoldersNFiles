package ro.apparatus;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class FavoritesSectionFragment extends ListFragment {
    private static final String TAG = FavoritesSectionFragment.class.getSimpleName();
    public static final String ARG_SECTION_NUMBER = "section_number";
    ArrayAdapter<String> adapter;
    Context ctx;
    FilesSectionFragment filesFragment;
    public FavoritesSectionFragment(Context context) {
        ctx = context;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, new String[0]);
        setListAdapter(adapter);
        getItemsFromSharedPreferences();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }
    private void getItemsFromSharedPreferences(){
//        adapter = new ArrayAdapter<>(ctx,
//                android.R.layout.simple_list_item_1, new String[] { "file1", "file2" });
//        setListAdapter(adapter);
    }
    public void addItem(String s){
        String[] stringToAdd;
        int noOfCells;
        if(!(adapter == null)){
            noOfCells = adapter.getCount();
            stringToAdd = new String[noOfCells+1];
            for(int i=0;i<noOfCells;i++){
                stringToAdd[i] = adapter.getItem(i);
            }
        }
        else{
            stringToAdd = new String[1];
            noOfCells = 0;
        }
        stringToAdd[noOfCells] = s;
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, stringToAdd);
        setListAdapter(adapter);
    }
    public void linkFiles(FilesSectionFragment files){
        filesFragment = files;
    }

}
