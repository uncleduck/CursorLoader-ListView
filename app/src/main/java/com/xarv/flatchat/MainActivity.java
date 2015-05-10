package com.xarv.flatchat;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final  int  URL_LOADER = 1;
    private String authorities= "com.xarv.flatchat";
    private String[] mProjection = {"_id","MSG_ID","MSG_TYPE" ,"MSG_DATA","MSG_TIME"};
    private Uri msgTable = Uri.parse("content://" + authorities+"/content"  + "/MSG_TABLE");

    private ListView msgListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgListView = (ListView) findViewById(R.id.msg_list);
        msgListView.setDivider(this.getResources().getDrawable(R.drawable.transperent_color));
        msgListView.setDividerHeight(20);
        getLoaderManager().initLoader(URL_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        MainActivity.this,   // Parent activity context
                        msgTable,        // Table to query
                        mProjection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
       // String _id = data.getString(0);
        MessageAdapter msgAdapter = new MessageAdapter(this,data,false);
        msgListView.setAdapter(msgAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
