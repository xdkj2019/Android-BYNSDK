package com.xidian.bynadsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class SearchPreference {

	public final static String TAG = SearchPreference.class.getSimpleName();

	private static SearchPreference mInstance;

	private SharedPreferences mPreferences;

	public static final String BYN_SEARCH_HISTORY = "byn_search_history";

	private SearchPreference(Context context) {
		mPreferences = context.getSharedPreferences(BYN_SEARCH_HISTORY, 0);
	}

	public synchronized static SearchPreference getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SearchPreference(context);
		}
		return mInstance;
	}
	Gson gson = new Gson();

	public void setKey(String str) {
		if(!TextUtils.isEmpty(str)&&str.length()>20){
			return;
		}
		Set<String> keys = mPreferences.getStringSet("key",new HashSet<String>());

		keys = new HashSet<>(keys);
		if (keys.size() > 5){
			keys.remove(1);
		}

		keys.add(str) ;
		mPreferences.edit().putStringSet("key", keys).commit();
	}

	public Set<String> getKeys(){
		return mPreferences.getStringSet("key",new HashSet<String>());
	}

	public void clean(){
		mPreferences.edit().remove("key").commit();
	}
}
