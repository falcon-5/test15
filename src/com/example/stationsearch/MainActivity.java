package com.example.stationsearch;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adapter.ListButtonClickListener;
import com.example.async.StationSearchTask;
import com.example.data.Station;

public class MainActivity extends ListActivity
	implements OnClickListener, ListButtonClickListener
{
	private StationSearchTask mTask = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = (Button)findViewById(R.id.btn_load);
        btn.setOnClickListener(this);
    }

    @Override
    public void onPause()
    {
    	super.onPause();
    	if(mTask != null && !mTask.isCancelled())
    	{
    		mTask.cancel(true);
    	}
    }

    @Override
    public void onClick(View v)
    {
    	int id = v.getId();

    	if(id == R.id.btn_load)
    	{
    		//更新ボタン
    		EditText editStation = (EditText)findViewById(R.id.editStationName);
    		String stationName = editStation.getText().toString();
    		if(stationName.length() > 0)
    		{
    			Uri.Builder builder = createBuilder();
    			builder.appendQueryParameter("name", stationName);
    			ListView list = (ListView)findViewById(android.R.id.list);
    			mTask = new StationSearchTask(this, this, list);
    			mTask.execute(builder.toString());
    		}
    	}
    }

    @Override
    public void onClick(Station station, int buttonType)
    {
    	Uri.Builder builder = createBuilder();

    	//押されたボタンの情報を元に更新
    	if(buttonType == ListButtonClickListener.PREV)
    	{
    		if(station.getPrev() != null)
    		{
    			builder.appendQueryParameter("name", station.getPrev());

    			ListView list = (ListView)findViewById(android.R.id.list);
    			mTask = new StationSearchTask(this, this, list);
    			mTask.execute(builder.toString());
    		}
    	}
    	else
    	{
    		if(station.getNext() != null)
    		{
    			builder.appendQueryParameter("name", station.getNext());

    			ListView list = (ListView)findViewById(android.R.id.list);
    			mTask = new StationSearchTask(this, this, list);
    			mTask.execute(builder.toString());
    		}
    	}
    }

    private Uri.Builder createBuilder()
    {
    	Uri.Builder builder = new Uri.Builder();
    	builder.scheme("http");
    	builder.authority("express.heartrails.com");
    	builder.path("api/json");
    	builder.appendQueryParameter("method", "getStations");

    	return builder;
    }
}