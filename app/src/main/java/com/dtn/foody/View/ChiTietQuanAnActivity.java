package com.dtn.foody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Adapters.AdapterComment;
import com.dtn.foody.Model.QuanAnModel;
import com.dtn.foody.Model.TienIchModel;
import com.dtn.foody.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback{
    TextView txtTenQuanAnChiTiet, txtDiachiQuanAnChiTiet, thoiGianHoatDong, trangThaiHoatDong;
    TextView tongHinhAnh, tongCheckin, tongBinhLuan, tongDanhDau, tongLuotChiaSe;
    ImageView imageViewHinhAnh;
    QuanAnModel quanAnModel;
    TextView txtTieuDeToolbar, txtGioihanGia;
    Toolbar toolbar;
    RecyclerView recyclerComment;
    AdapterComment adapterComment;
    GoogleMap googleMap;
    MapFragment mapFragment;
    LinearLayout khungTienich;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_chitietquanan);
//        Log.e("Kiemtra", "jennie");

        txtTenQuanAnChiTiet = findViewById(R.id.txtTenQuanAnChiTiet);
        txtDiachiQuanAnChiTiet = findViewById(R.id.txtDiachiChiTiet);
        txtGioihanGia = findViewById(R.id.txtGioiHanGia);
        thoiGianHoatDong = findViewById(R.id.thoiGianHoatDong);
        trangThaiHoatDong = findViewById(R.id.trangThaiHoatdong);
        tongCheckin = findViewById(R.id.tongCheckin);
        tongBinhLuan = findViewById(R.id.tongBinhLuan);
        tongHinhAnh = findViewById(R.id.tongHinhAnh);
        tongLuotChiaSe = findViewById(R.id.tongLuotChiaSe);
        tongDanhDau = findViewById(R.id.tongDangDau);
        imageViewHinhAnh = findViewById(R.id.imageViewHinhAnh);
        txtTieuDeToolbar = findViewById(R.id.tieuDeToolbar);
        toolbar = findViewById(R.id.toolbar);
        recyclerComment = findViewById(R.id.recyclerCommnent);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        quanAnModel = (QuanAnModel) getIntent().getParcelableExtra("quanan");
        khungTienich = findViewById(R.id.khungTienich);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
   //     mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String gioHientai = simpleDateFormat.format(calendar.getTime());
        String gioMocua = quanAnModel.getGiomocua();
        String gioDongcua = quanAnModel.getGiodongcua();

        try {
            String[] str1 = gioMocua.split(":", 2);
            String[] str2 = gioDongcua.split(":", 2);
            int a = Integer.parseInt(str1[0]), b = Integer.parseInt(str2[0]);
            if(a > b)
            {
                int tmp = Integer.parseInt(str2[0]);
                tmp += 24;
                str2[0] = String.valueOf(tmp);
            }
            gioDongcua = "";
            gioDongcua += str2[0] + ":" + str2[1];
            Log.e("kiemtra", gioDongcua);
            Date datehHientai = simpleDateFormat.parse(gioHientai);
            Date dateMocua = simpleDateFormat.parse(gioMocua);
            Date dateDongcua = simpleDateFormat.parse(gioDongcua);
            if(datehHientai.after(dateMocua) && datehHientai.before(dateDongcua))
            {
                trangThaiHoatDong.setText("Đã mở cửa");
                trangThaiHoatDong.setTextColor(Color.parseColor("#FF4CAF50"));
            }
            else{
                trangThaiHoatDong.setText("Chưa mở");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());
        txtTenQuanAnChiTiet.setText(quanAnModel.getTenquanan());
//        Log.e("kiemtra", quanAnModel.getChiNhanhModelList().size() + " - " + quanAnModel.getChiNhanhModelList().get(0).getDiachi());
        txtDiachiQuanAnChiTiet.setText(quanAnModel.getChiNhanhModelList().get(0).getDiachi());
        thoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        tongHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        tongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        thoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());

        DownloadHinhTienich();

        if(quanAnModel.getGiatoida() != null && quanAnModel.getGiatoithieu() != null)
        {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoida = quanAnModel.getGiatoida() + "";
            String giatoithieu = quanAnModel.getGiatoithieu() + "";
            txtGioihanGia.setText(giatoithieu + "đ - " + giatoida + "đ");
        }
        else
        {
            txtGioihanGia.setVisibility(View.INVISIBLE);
        }

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("HinhquanAn").child(quanAnModel.getHinhanhquanan().get(0));
        long ONE_MEGABYTE = 1024*1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageViewHinhAnh.setImageBitmap(bitmap);
            }
        });
        // Load danh sach binh luan
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerComment.setLayoutManager(layoutManager);
        adapterComment = new AdapterComment(this, R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList());
        recyclerComment.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();

        NestedScrollView nestedScrollView = findViewById(R.id.nestedscrollCHitiet);
        nestedScrollView.smoothScrollTo(0,0);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        float latitude = (float) quanAnModel.getChiNhanhModelList().get(0).getLatitude();
        float longtitude = (float) quanAnModel.getChiNhanhModelList().get(0).getLongitude();
        LatLng latLng = new LatLng(latitude, longtitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(quanAnModel.getTenquanan());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }
    private void DownloadHinhTienich()
    {
        for(String matienich : quanAnModel.getTienich())
        {
            DatabaseReference nodeTienich = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(matienich);
            nodeTienich.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024*1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageView = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                            layoutParams.setMargins(10,10,10,10);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setPadding(5,5,5,5);
                            imageView.setImageBitmap(bitmap);
                            khungTienich.addView(imageView);
                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//
        }

    }
}
