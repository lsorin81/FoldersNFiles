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
import android.widget.Toast;

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
        Toast.makeText(ctx, "Press files to delete them from favorite list!", Toast.LENGTH_SHORT);
        getItemsFromSharedPreferences();
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
    public void linkFiles(FilesSectionFragment files){
        filesFragment = files;
    }

}
