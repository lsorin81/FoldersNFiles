package ro.apparatus;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
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

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilesSectionFragment extends ListFragment {
    private static final String TAG = FilesSectionFragment.class.getSimpleName();
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String URL_STRING = "http://quickweb.ro/ftest.html";
    SqliteDbAdapter sda;
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
        getWebPage();
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
    @SuppressWarnings("unchecked")
    private void getWebPage(){
        new AsyncTask(){
            @Override
            protected String doInBackground(Object... params) {

                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(URL_STRING);
                    ResponseHandler<String> resHandler = new BasicResponseHandler();
                    String page = null;
                try {
                    page = httpClient.execute(httpGet, resHandler);
                } catch (IOException e){ e.printStackTrace();}
                return page;
            }

            @Override
            protected void onPostExecute(Object msg) {
                if(msg==null){
                    Log.e(TAG, "The page couldn't be recovered");
                    return;
                }
                parseWebPage(msg.toString());
                initializeAdapter();
            }
        }.execute(null, null, null);
    }

    private void parseWebPage(String s) {
        sda = new SqliteDbAdapter(ctx);
        sda.open();
        String[] lines = s.split("\n");
        for(String line:lines){
            Log.e(TAG, line);
            String[] fNames = line.split("\t");
            sda.insertF(fNames[0],fNames[1]);
            Log.e(TAG, fNames[1]);
        }
        sda.close();
    }
    public void initializeAdapter(){
        List<String> fileBuffer = new ArrayList<>();
        int i=0;
        sda = new SqliteDbAdapter(ctx);
        sda.open();
        Cursor c = sda.fetchSortedTable();
        result = new String[c.getCount()];
        c.moveToFirst();
        do{
            if(c.getString(1).equals("f")){
                fileBuffer.add(c.getString(2));
            }
            else{
                // we transform the Folders to uppercase
                result[i] = c.getString(2).toUpperCase();
                i++;
            }
        }while(c.moveToNext());
        noOfFolders = i;
        for(int j=0;j<fileBuffer.size();j++){
            result[j+i]=fileBuffer.get(j);
        }
        adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_list_item_1, result);
        setListAdapter(adapter);
        Toast.makeText(ctx, "Press files to add them to Favorites!", Toast.LENGTH_SHORT).show();
        // we start fresh every time
        sda.dropTable();
        sda.close();
    }

}
