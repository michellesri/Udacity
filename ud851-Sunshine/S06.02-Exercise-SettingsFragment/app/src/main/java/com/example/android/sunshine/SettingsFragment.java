package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_screen);

        int prefCount = getPreferenceScreen().getPreferenceCount();
        for (int i = 0; i < prefCount; i++) {
            Preference pref = getPreferenceScreen().getPreference(i);
            String summary = getPreferenceManager().getSharedPreferences().getString(pref.getKey(), "");
            setPreferenceSummary(pref, summary);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void setPreferenceSummary(Preference pref, Object value) {
        String stringValue = value.toString();

        if (pref instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) pref;
            int prefIndex = listPreference.findIndexOfValue(stringValue);

            if (prefIndex >= 0) {
                pref.setSummary(listPreference.getEntries()[prefIndex]);
            } else {
                pref.setSummary(stringValue);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {
            findPreference(key).setSummary(sharedPreferences.getString(key, getString(R.string.pref_location_default)));
        } else if (key.equals(getString(R.string.pref_units_key))) {
            setPreferenceSummary(findPreference(key), sharedPreferences.getString(key, getString(R.string.pref_units_metric)));
        }


    }
}
