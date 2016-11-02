package com.yidingliu.dev.knowldegelibrary.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by chris zou on 2016/7/29.
 */
public class SharedPreferencesHelper {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private SharedPreferencesHelper ( Context context, String keyName ) {
        this.mContext = context;
        mSharedPreferences = context.getSharedPreferences(keyName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }

    public void putValue(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public void putValue(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void putValue(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public void putValue(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void putValue(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
        mEditor.commit();
    }

    public <T> T getValue(String key) {
        return getValue(key,"");
    }

    public <T> T getValue(String key, String defaultValue) {
        Map<String, ?> map = getAll();
        Set<String> keySet = map.keySet();
        map.get(key);
        for (String item : keySet) {
            if (item.equalsIgnoreCase(key)) {
                return (T) map.get(item);
            }
        }
        return (T) defaultValue;
    }

    public void removeValue(String key) {
        mEditor.remove(key);
    }

    public Set<String> getSet(String key) {
        return mSharedPreferences.getStringSet(key, null);
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }


    public static class Builder {

        private static Builder instance;
        private static SharedPreferencesHelper helper;

        public static Builder getInstance(Context context, String keyName) {
            if (instance == null) {
                instance = new Builder();
            }
            if (helper == null) {
                helper = new SharedPreferencesHelper(context, keyName);
            }
            return instance;
        }

        public Builder putValue(String key, String value) {
            helper.putValue(key, value);
            return this;
        }

        public Builder putValue(String key, int value) {
            helper.putValue(key, value);
            return this;
        }

        public Builder putValue(String key, long value) {
            helper.putValue(key, value);
            return this;
        }

        public Builder putValue(String key, boolean value) {
            helper.putValue(key, value);
            return this;
        }

        public Builder putValue(String key, float value) {
            helper.putValue(key, value);
            return this;
        }

        public Builder putValue(String key, Set<String> value) {
            helper.putValue(key, value);
            return this;
        }

        public SharedPreferencesHelper getHelper() {
            return helper;
        }
    }
}
