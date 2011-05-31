package com.example.async;

import org.apache.http.HttpStatus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.adapter.ListButtonClickListener;
import com.example.adapter.StationAdapter;
import com.example.data.Response;
import com.example.data.ResponseFactory;
import com.example.stationsearch.R;
import com.example.web.HttpServerIF;

public class StationSearchTask extends AsyncTask<String, Void, Integer>
{
	private ProgressDialog mDialog = null;
	private Context mContext;
	private ListButtonClickListener mListener;
	private ListView mList;
	private Response mRes;

	public StationSearchTask(Context context, ListButtonClickListener listener, ListView list)
	{
		mContext = context;
		mListener = listener;
		mList = list;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage(mContext.getString(R.string.dialog_receiving));
		mDialog.setIndeterminate(true);
		mDialog.show();
	}

	@Override
	protected Integer doInBackground(String... url)
	{
		int iRet = 0;

		HttpServerIF svr = new HttpServerIF();
		iRet = svr.requestText(url[0]);
		if(iRet == HttpStatus.SC_OK)
		{
			//受信データを解析
			ResponseFactory factory = new ResponseFactory();
			mRes = factory.create(svr.getResText());
			if(mRes == null)
			{
				//データが変換できない
				iRet = -1;
			}
		}
		return new Integer(iRet);
	}

	@Override
	protected void onPostExecute(Integer result)
	{
		super.onPostExecute(result);

		if(result == HttpStatus.SC_OK)
		{
			//ListViewにデータを表示
			StationAdapter adapter = new StationAdapter(mContext, mRes.getStation());
			adapter.setListButtonClickListener(mListener);
			mList.setAdapter(adapter);
		}
		mDialog.dismiss();
	}
}
