package com.dtn.foody.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dtn.foody.Controller.OdauInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuanAnModel implements Parcelable {
    Long luotthich;
    Long giatoida, giatoithieu;

    public Long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(Long giatoida) {
        this.giatoida = giatoida;
    }

    public Long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(Long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    protected QuanAnModel(Parcel in) {
        if (in.readByte() == 0) {
            luotthich = null;
        } else {
            luotthich = in.readLong();
            giatoida = in.readLong();
            giatoithieu = in.readLong();
        }
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();
        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
      //  bitmapList = in.createTypedArrayList(Bitmap.CREATOR);
        chiNhanhModelList = new ArrayList<ChiNhanhModel>();
        in.readTypedList(chiNhanhModelList, ChiNhanhModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList, BinhLuanModel.CREATOR);

    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    boolean giaohang;
    String giodongcua;
    String giomocua;
    String tenquanan;
    String videogioithieu;
    String maquanan;
    List<String> tienich;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    DatabaseReference dataQuanAn;
    List<ChiNhanhModel> chiNhanhModelList;
    List<Bitmap> bitmapList;

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<ChiNhanhModel> getChiNhanhModelList() {
        return chiNhanhModelList;
    }

    public void setChiNhanhModelList(List<ChiNhanhModel> chiNhanhModelList) {
        this.chiNhanhModelList = chiNhanhModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public QuanAnModel(){
        dataQuanAn = FirebaseDatabase.getInstance().getReference();
    }
    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;
    public void getDanhSachQuanan(final OdauInterface odauInterface, Location vitriHientai, int itemtieptheo, int itembandau)
    {

        List<QuanAnModel> QuananModellist = new ArrayList<QuanAnModel>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            // snapshot: chính là địa chỉ gốc trong firebase
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataRoot = snapshot;
                LayDanhsachQuanAn(snapshot, odauInterface, vitriHientai,itemtieptheo,itembandau);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        if(dataRoot != null)
        {
            LayDanhsachQuanAn(dataRoot, odauInterface, vitriHientai, itemtieptheo, itembandau);
        }
        else
        {
            dataQuanAn.addListenerForSingleValueEvent(valueEventListener);
        }


    }
    private  void LayDanhsachQuanAn(DataSnapshot snapshot, OdauInterface odauInterface, Location vitriHientai, int itemtieptheo, int itembandau)
    {
        // dataSnapshot chỉ vào địa chỉ quán ăn
        DataSnapshot dataSnapshot = snapshot.child("quanans");
        int i = 0;
        QuanAnModel quananModel;
        // Lấy danh sách quán ăn
        for (DataSnapshot tmp : dataSnapshot.getChildren()) {
            if(i == itemtieptheo)
            {
                break;
            }
            if(i < itembandau)
            {
                ++i;
                continue;
            }
            ++i;
            quananModel = tmp.getValue(QuanAnModel.class);
            quananModel.setMaquanan(tmp.getKey());
            DataSnapshot gethinhanh = snapshot.child("hinhanhquanans").child(tmp.getKey());
            List<String> listHinhAnh = new ArrayList<>();
            // Lấy mã hình ảnh quán ăn theo mã
            for (DataSnapshot value : gethinhanh.getChildren()) {
                listHinhAnh.add(value.getValue(String.class));
            }
             Log.e("kiemtra", quananModel.getTenquanan());
            quananModel.setHinhanhquanan(listHinhAnh);
            // Lấy danh sách bình luận của quán ăn
            List<BinhLuanModel> listBinhLuan = new ArrayList<>();
            DataSnapshot snapshotbinhLuan = snapshot.child("binhluans").child(quananModel.getMaquanan());
            for (DataSnapshot res : snapshotbinhLuan.getChildren()) {
                BinhLuanModel binhLuanModel = res.getValue(BinhLuanModel.class);
                binhLuanModel.setMaBinhLuan(res.getKey());
                ThanhVienModel thanhVienModel = snapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> list = new ArrayList<>();
                DataSnapshot snapshotHinhAnhBinhLuan = snapshot.child("hinhanhbinhluans").child(binhLuanModel.getMaBinhLuan());
                for (DataSnapshot data : snapshotHinhAnhBinhLuan.getChildren()) {
                    list.add(data.getValue(String.class));
                }
                binhLuanModel.setListHinhAnh(list);
                listBinhLuan.add(binhLuanModel);
            }
            quananModel.setBinhLuanModelList(listBinhLuan);

            // Lấy chi nhánh các quán ăn
            List<ChiNhanhModel> chiNhanhModelList;
            chiNhanhModelList = new ArrayList<>();
            DataSnapshot getChinhanh = snapshot.child("chinhanhquanans").child(quananModel.getMaquanan());
            for (DataSnapshot val : getChinhanh.getChildren()) {
                ChiNhanhModel chiNhanhModel = val.getValue(ChiNhanhModel.class);
                Location vitriQuanan = new Location("");
                vitriQuanan.setLatitude(chiNhanhModel.getLatitude());
                vitriQuanan.setLongitude(chiNhanhModel.getLongitude());
                double kc = vitriHientai.distanceTo(vitriQuanan);
                kc /= 1_000;
                chiNhanhModel.setKhaongcach(kc);
                chiNhanhModelList.add(chiNhanhModel);
            }
            quananModel.setChiNhanhModelList(chiNhanhModelList);
            odauInterface.getDanhsachQuanAn(quananModel);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (luotthich == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(luotthich);
            parcel.writeLong(giatoida);
            parcel.writeLong(giatoithieu);
        }
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giodongcua);
        parcel.writeString(giomocua);
        parcel.writeString(tenquanan);
        parcel.writeString(videogioithieu);
        parcel.writeString(maquanan);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanan);
        parcel.writeTypedList(chiNhanhModelList);
        parcel.writeTypedList(binhLuanModelList);
    }
}
