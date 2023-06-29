package com.dtn.foody.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Adapters.AdapterRecyclerPlaces;
import com.dtn.foody.Model.QuanAnModel;
import com.dtn.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OdauController {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerPlaces adapterRecyclerPlaces;int itembandau = 3;
    public OdauController(Context context)
    {
        this.context = context;
        quanAnModel = new QuanAnModel();

    }
    public void getDanhSachQuanAnController(NestedScrollView nestedScrollView, RecyclerView recyclerOdau, ProgressBar progressBarOdau, Location vitriHientai)
    {
        List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerPlaces = new AdapterRecyclerPlaces(quanAnModelList, R.layout.custom_layout_recyclerview_places, context);
        recyclerOdau.setAdapter(adapterRecyclerPlaces);

        progressBarOdau.setVisibility(View.VISIBLE);

        OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhsachQuanAn(QuanAnModel quanAnModel) {
                List<Bitmap> bitmaps = new ArrayList<>();
                for(String linkhinh : quanAnModel.getHinhanhquanan())
                {
                    StorageReference storageReferenceHinhAnh = FirebaseStorage.getInstance().getReference().child("HinhquanAn").child(linkhinh);
                    long ONE_MEGABYTE = 1024*1024;
                    storageReferenceHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps.add(bitmap);
                            quanAnModel.setBitmapList(bitmaps);

                            if(quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanan().size()) {
                                quanAnModelList.add(quanAnModel);
                                adapterRecyclerPlaces.notifyDataSetChanged();
                                progressBarOdau.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) != null)
                {
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight())
                    {
                        itembandau += 3;
                        quanAnModel.getDanhSachQuanan(odauInterface, vitriHientai, itembandau, itembandau - 3);
                    }
                }
            }
        });

        quanAnModel.getDanhSachQuanan(odauInterface, vitriHientai, itembandau, 0);
    }
}
