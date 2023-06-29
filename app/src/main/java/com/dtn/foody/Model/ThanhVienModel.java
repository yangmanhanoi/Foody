package com.dtn.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class ThanhVienModel implements Parcelable {
    String hoten;
    String hinhanh;

    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        mathanhVien = in.readString();
        
    }

    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };

    public String getMathanhVien() {
        return mathanhVien;
    }

    public void setMathanhVien(String mathanhVien) {
        this.mathanhVien = mathanhVien;
    }

    String mathanhVien;
    DatabaseReference dataNodeThanhVien;
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }


    public ThanhVienModel()
    {
        dataNodeThanhVien = FirebaseDatabase.getInstance().getReference().child("thanhviens");
    }
    public void themThanhvienController(ThanhVienModel thanhVienModel, String uid)
    {
        dataNodeThanhVien.child(uid).setValue(thanhVienModel, uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoten);
        parcel.writeString(hinhanh);
        parcel.writeString(mathanhVien);
    }
}
