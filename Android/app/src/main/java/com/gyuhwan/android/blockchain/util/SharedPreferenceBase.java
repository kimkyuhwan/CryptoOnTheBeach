package com.gyuhwan.android.blockchain.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.gyuhwan.android.blockchain.MApplication;
import com.gyuhwan.android.blockchain.dataSchema.BuyerResult;
import com.gyuhwan.android.blockchain.dataSchema.Data;
import com.gyuhwan.android.blockchain.dataSchema.ItemSearchResult;
import com.gyuhwan.android.blockchain.dataSchema.Keystore;
import com.gyuhwan.android.blockchain.dataSchema.User;
import com.gyuhwan.android.blockchain.dataSchema.UserData;

public class SharedPreferenceBase {
    private static SharedPreferences sp;

    //Additionol method for String type

    public static void putSharedPreference(String key, String value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putSharedPreference(String key, boolean value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putIntSharedPreference(String key, int value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putKeyStoreSharedPreference(String key, Keystore value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.commit();
    }

    public static void putItemListSharedPreference(String key, ItemSearchResult value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.commit();
    }

    public static void putUserDataSharedPreference(String key, UserData value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.commit();
    }

    public static void putBuyerListSharedPreference(String key, BuyerResult value) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.commit();
    }

    public static String getSharedPreference(String key, @Nullable String defaultValue) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static Boolean getSharedPreference(String key) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static int getIntSharedPreference(String key) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static Keystore getKeyStoreSharedPreference(String key) {
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = sp.getString(key, "");
        Keystore obj = gson.fromJson(json, Keystore.class);
        return obj;
    }

    public static ItemSearchResult getItemListSharedPreference(String key){
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = sp.getString(key, "");
        ItemSearchResult obj = gson.fromJson(json, ItemSearchResult.class);
        return obj;
    }

    public static UserData getUserDataSharedPreference(String key){
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = sp.getString(key, "");
        UserData obj = gson.fromJson(json, UserData.class);
        return obj;
    }

    public static BuyerResult getBuyerListSharedPreference(String key){
        sp = MApplication.getInstance().getSharedPreferences("storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = sp.getString(key, "");
        BuyerResult obj = gson.fromJson(json, BuyerResult.class);
        return obj;
    }
}
