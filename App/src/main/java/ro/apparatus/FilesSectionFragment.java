package ro.apparatus;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;


public class FilesSectionFragment extends ListFragment {
    private static final String TAG = FilesSectionFragment.class.getSimpleName();
    public static final String ARG_SECTION_NUMBER = "section_number";
    ArrayAdapter adapter;
    Context ctx;
    String[] folders;
    String[] files;
    String[] result;
    private int noOfFolders=0;
    FavoritesSectionFragment favoritesFragment;
    public FilesSectionFragment(Context context) {
        ctx = context;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        folders = new String[]{"FOLDER1", "FOLDER2", "FOLDER3"};
        files = new String[]{"abc.txt", "file1.txt", "file2.txt", "file3.txt", "file4.txt"};
        result = new String[folders.length+files.length];
        System.arraycopy(folders, 0, result, 0, folders.length);
        System.arraycopy(files, 0, result, folders.length, files.length);
        // noOfFolders has to be initiated properly
        noOfFolders = folders.length;
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, result);
        setListAdapter(adapter);
        Toast.makeText(ctx, "Press files to add them to Favorites!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(position >= noOfFolders){
            favoritesFragment.addItem(((TextView) v).getText().toString());
        }
    }
    public void linkFavorites(FavoritesSectionFragment fav){
        favoritesFragment = fav;
    }

}
