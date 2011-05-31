package com.example.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseFactory
{
	public Response create(String strResponse)
	{
		Response resData = null;
		try
		{
			//ルートオブジェクトの生成
			JSONObject rootObject = new JSONObject(strResponse);

			//responseオブジェクトの生成
			JSONObject resObject = rootObject.getJSONObject("response");

			//stationオブジェクトの生成
			JSONArray stationArray = resObject.getJSONArray("station");
			if(stationArray.length() > 0)
			{
				resData = new Response();
				resData.setStation(new ArrayList<Station>());
				for(int i = 0; i < stationArray.length(); i++)
				{
					JSONObject stationObject = stationArray.getJSONObject(i);

					Station station = new Station();
					station.setName(stationObject.getString("name"));
					String strPrev = stationObject.getString("prev");
					station.setPrev((strPrev.equals("null")) ? null : strPrev);
					String strNext = stationObject.getString("next");
					station.setNext((strNext.equals("null")) ? null : strNext);
					station.setX(stationObject.getDouble("x"));
					station.setY(stationObject.getDouble("y"));
					station.setPostal(stationObject.getInt("postal"));
					station.setPrefecture(stationObject.getString("prefecture"));
					station.setLine(stationObject.getString("line"));

					//解析データを追加
					resData.getStation().add(station);
				}
			}
		}
		catch(Exception e)
		{
			//JSONのフォーマットに合わない場合は例外が発生する。
			resData = null;
		}

		return resData;
	}
}
