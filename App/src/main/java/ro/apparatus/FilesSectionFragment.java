package ro.apparatus;

import android.content.Context;
import android.graphics.Color;
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
    ArrayAdapter<String> folderAdapter;
    ArrayAdapter<String> fileAdapter;
    Context ctx;
    private int noOfFolders=0;
    public FilesSectionFragment(Context context) {
        ctx = context;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] folders = new String[]{"FOLDER1", "FOLDER2", "FOLDER3"};
        String[] files = new String[]{"abc.txt", "file1.txt", "file2.txt", "file3.txt", "file4.txt"};
        String[] result = Arrays.copyOf(folders, folders.length + files.length);
        System.arraycopy(files, 0, result, folders.length, files.length);
        noOfFolders = folders.length;
        folderAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, result);
        setListAdapter(folderAdapter);
        Toast.makeText(ctx, "Press files to add them to Favorites!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(position >= noOfFolders){
            Toast.makeText(ctx, "File added to Favorites!", Toast.LENGTH_SHORT).show();
            ((TextView) v).setTextColor(getResources().getColor(R.color.gold));

            // add to favorites
        }

    }
}
