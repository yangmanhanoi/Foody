package com.dtn.foody.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.ls.LSInput;

import java.util.List;

public class BinhLuanModel implements Parcelable {
    Double chamdiem;

    protected BinhLuanModel(Parcel in) {
        if (in.readByte() == 0) {
            chamdiem = null;
        } else {
            chamdiem = in.readDouble();
        }
        if (in.readByte() == 0) {
            luotthich = null;
        } else {
            luotthich = in.readLong();
        }
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        maBinhLuan = in.readString();
        listHinhAnh = in.createStringArrayList();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public Double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(Double chamdiem) {
        this.chamdiem = chamdiem;
    }

    Long luotthich;
    ThanhVienModel thanhVienModel;
    String noidung;
    String tieude;
    String mauser;
    String maBinhLuan;
    List<String> listHinhAnh;
    public String getMaBinhLuan() {
        return maBinhLuan;
    }

    public void setMaBinhLuan(String maBinhLuan) {
        this.maBinhLuan = maBinhLuan;
    }

    public List<String> getListHinhAnh() {
        return listHinhAnh;
    }

    public void setListHinhAnh(List<String> listHinhAnh) {
        this.listHinhAnh = listHinhAnh;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }
    public BinhLuanModel()
    {

    }

    public Long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(Long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (chamdiem == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(chamdiem);
        }
        if (luotthich == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(luotthich);
        }
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeString(maBinhLuan);
        parcel.writeStringList(listHinhAnh);
        parcel.writeParcelable(thanhVienModel, i);
    }
}
