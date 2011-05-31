package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.data.Station;
import com.example.stationsearch.R;

public class StationAdapter extends ArrayAdapter<Station>
{
	private LayoutInflater mInflater;
	private ListButtonClickListener mListener = null;

	public StationAdapter(Context context, List<Station> objects)
	{
		this(context, 0, objects);
	}

	public StationAdapter(Context context, int textViewResourceId, List<Station> objects)
	{
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setListButtonClickListener(ListButtonClickListener listener)
	{
		mListener = listener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if(convertView == null)
		{
			convertView = mInflater.inflate(R.layout.main_list, null);

			holder = new ViewHolder();
			holder.txt_name = (TextView)convertView.findViewById(R.id.txt_name);
			holder.txt_prefecture = (TextView)convertView.findViewById(R.id.txt_prefecture);
			holder.txt_line = (TextView)convertView.findViewById(R.id.txt_line);
			holder.btn_prev = (Button)convertView.findViewById(R.id.btn_prev);
			holder.btn_prev.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					Station station = StationAdapter.this.getItem(position);
					if(mListener != null) mListener.onClick(station, ListButtonClickListener.PREV);
				}
			});
			holder.btn_next = (Button)convertView.findViewById(R.id.btn_next);
			holder.btn_next.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					Station station = StationAdapter.this.getItem(position);
					if(mListener != null) mListener.onClick(station, ListButtonClickListener.NEXT);
				}
			});
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		Station station = this.getItem(position);
		if(station != null)
		{
			holder.txt_name.setText(station.getName());
			holder.txt_prefecture.setText(station.getPrefecture());
			holder.txt_line.setText(station.getLine());
		}

		return convertView;
	}

	class ViewHolder
	{
		TextView txt_name;
		TextView txt_prefecture;
		TextView txt_line;
		Button btn_prev;
		Button btn_next;
	}

}
