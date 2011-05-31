package com.example.data;

import java.util.ArrayList;

public class Response extends BaseData
{
	private ArrayList<Station> station;

	public ArrayList<Station> getStation() {
		return station;
	}

	public void setStation(ArrayList<Station> station) {
		this.station = station;
	}
}
