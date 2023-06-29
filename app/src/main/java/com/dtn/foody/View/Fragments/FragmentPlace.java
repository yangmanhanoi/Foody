package com.dtn.foody.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Controller.OdauController;
import com.dtn.foody.Model.QuanAnModel;
import com.dtn.foody.R;

public class FragmentPlace extends Fragment {
    OdauController odauController;
    RecyclerView recyclerPlaces;
    ProgressBar progressBarOdau;
    SharedPreferences sharedPreferences;
    NestedScrollView nestedScrollView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places, container, false);

        recyclerPlaces = v.findViewById(R.id.recyclerPlaces);
        progressBarOdau = v.findViewById(R.id.progressBarOdau);
        nestedScrollView = v.findViewById(R.id.nestedscrollOdau);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        sharedPreferences = getContext().getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(sharedPreferences.getString("Latitude", "0")));
        location.setLongitude(Double.parseDouble(sharedPreferences.getString("Longtitude", "0")));
        odauController = new OdauController(getContext());
        Log.e("kiemtraodau", sharedPreferences.getString("Latitude", "") + "");
        odauController.getDanhSachQuanAnController(nestedScrollView,recyclerPlaces,progressBarOdau, location);

    }
}
