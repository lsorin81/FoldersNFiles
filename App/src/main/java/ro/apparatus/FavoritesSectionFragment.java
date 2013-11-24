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
    public FavoritesSectionFragment(Context context) {
        ctx = context;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get favorites from preferences
        getItemsFromSharedPreferences();
        printList();
        addItem("file37.txt");
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }
    private void getItemsFromSharedPreferences(){
        adapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_list_item_1, new String[] { "file1", "file2" });
        setListAdapter(adapter);
    }
    private void printList(){
        Log.e(TAG, "inside printList()");
        int noOfCells = adapter.getCount();
        for(int i=0;i<noOfCells;i++){
            Log.e(TAG, adapter.getItem(i));
        }
    }
    public void addItem(String s){
        int noOfCells = adapter.getCount();
        String[] stringToAdd = new String[noOfCells+1];
        for(int i=0;i<noOfCells;i++){
            stringToAdd[i] = adapter.getItem(i);
        }
        stringToAdd[noOfCells] = s;
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, stringToAdd);
        setListAdapter(adapter);
    }

}
