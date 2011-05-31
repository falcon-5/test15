package com.example.adapter;

import com.example.data.Station;

public interface ListButtonClickListener
{
	public static final int PREV = 1;
	public static final int NEXT = 2;

	public void onClick(Station station, int buttonType);
}
