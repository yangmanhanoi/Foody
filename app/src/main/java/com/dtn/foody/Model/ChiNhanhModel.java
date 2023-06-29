package com.dtn.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChiNhanhModel implements Parcelable {
    String diachi;
    double latitude, longitude, khaongcach;

    public ChiNhanhModel()
    {

    }

    protected ChiNhanhModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khaongcach = in.readDouble();
    }

    public static final Creator<ChiNhanhModel> CREATOR = new Creator<ChiNhanhModel>() {
        @Override
        public ChiNhanhModel createFromParcel(Parcel in) {
            return new ChiNhanhModel(in);
        }

        @Override
        public ChiNhanhModel[] newArray(int size) {
            return new ChiNhanhModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhaongcach() {
        return khaongcach;
    }

    public void setKhaongcach(double khaongcach) {
        this.khaongcach = khaongcach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(khaongcach);
    }
}
