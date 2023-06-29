package com.dtn.foody.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Model.BinhLuanModel;
import com.dtn.foody.Model.ChiNhanhModel;
import com.dtn.foody.Model.QuanAnModel;
import com.dtn.foody.R;
import com.dtn.foody.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerPlaces extends RecyclerView.Adapter<AdapterRecyclerPlaces.ViewHolder>{

    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecyclerPlaces(List<QuanAnModel> quanAnModelList, int resource, Context context)
    {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenQuanAnOdau, chamdiemBinhLuan1, chamdiemBinhLuan2, tongBinhLuan, tongHinhAnhQuanAn;
        ImageView hinhQuanAn;
        Button btnDatMonOdau;
        TextView txtNoiDungBinhLuan, txtNoiDungBinhLuan2, txtTieuDe, txtTieuDe2, txtdiemTrungbinh;
        TextView DiachiQuanan, Khoangcach;
        CircleImageView circleImageuser, circleImageView2;
        LinearLayout containerBinhLuan1,containerBinhLuan2;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanAnOdau);
            btnDatMonOdau = itemView.findViewById(R.id.btnDatMonOdau);
            hinhQuanAn = itemView.findViewById(R.id.imageHinhQuanAn);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtNoiDungBinhLuan2 = itemView.findViewById(R.id.txtNoiDungBinhLuan2);
            txtTieuDe = itemView.findViewById(R.id.txtTieudeBinhLuan);
            txtTieuDe2 = itemView.findViewById(R.id.txtTieudeBinhLuan2);
            circleImageuser = itemView.findViewById(R.id.circleImageuser);
            circleImageView2 = itemView.findViewById(R.id.circleImageuser2);
            containerBinhLuan1 = itemView.findViewById(R.id.containerBinhluan1);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            chamdiemBinhLuan1 = itemView.findViewById(R.id.chandiemBinhLuan1);
            chamdiemBinhLuan2 = itemView.findViewById(R.id.chandiemBinhLuan2);
            tongBinhLuan = itemView.findViewById(R.id.tongBinhLuan);
            tongHinhAnhQuanAn = itemView.findViewById(R.id.tongHinhAnhQuanAn);
            txtdiemTrungbinh = itemView.findViewById(R.id.txtdiemTrungBinh);
            DiachiQuanan = itemView.findViewById(R.id.diachiQuanan);
            Khoangcach = itemView.findViewById(R.id.khoangcach);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    @NonNull
    @Override
    public AdapterRecyclerPlaces.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerPlaces.ViewHolder holder, int position) {
        QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());
        // Xét xem quán ăn đó có hỗ trợ đặt món k
        if(quanAnModel.isGiaohang())
        {
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }
        // Lấy hình ảnh quán ăn
        if(quanAnModel.getBitmapList().size() > 0)
        {
            holder.hinhQuanAn.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
        // Lấy các bình luận, hình ảnh về quán ăn đó
        if(quanAnModel.getBinhLuanModelList().size() > 0)
        {
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieuDe.setText(binhLuanModel.getTieude());
            holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
            holder.chamdiemBinhLuan1.setText(binhLuanModel.getChamdiem() + " ");

            setHinhAnhUser(holder.circleImageuser, binhLuanModel.getThanhVienModel().getHinhanh());
            if(quanAnModel.getBinhLuanModelList().size() > 2)
            {
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.chamdiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + " ");
                holder.txtTieuDe2.setText(binhLuanModel2.getTieude());
                holder.txtNoiDungBinhLuan2.setText(binhLuanModel2.getNoidung());

                setHinhAnhUser(holder.circleImageView2, binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            int tongBinhLuan = 0;
            double tongdiem = 0;
            // Tính tổng bình luận, và tổng điểm
            for(BinhLuanModel x : quanAnModel.getBinhLuanModelList())
            {
                tongBinhLuan += x.getListHinhAnh().size();
                tongdiem += x.getChamdiem();
            }
            if(tongBinhLuan > 0) holder.tongHinhAnhQuanAn.setText(tongBinhLuan + "");
            double diemTB = tongdiem/quanAnModel.getBinhLuanModelList().size();
            holder.txtdiemTrungbinh.setText(String.format("%.1f",diemTB));
            holder.tongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        }else
        {
            holder.containerBinhLuan1.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
            holder.tongBinhLuan.setText("0");
        }
        // Lấy địa chỉ và khoảng cách đến quán ăn đó từu vị trí hiện tại
        if(quanAnModel.getChiNhanhModelList().size() > 0)
        {
            ChiNhanhModel tmp = quanAnModel.getChiNhanhModelList().get(0);
            for(ChiNhanhModel x : quanAnModel.getChiNhanhModelList())
            {
                if(x.getKhaongcach() < tmp.getKhaongcach())
                {
                    tmp = x;
                }
            }
            holder.Khoangcach.setText(String.format("%.1f", tmp.getKhaongcach()) + "km");
            holder.DiachiQuanan.setText(tmp.getDiachi());
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan", (Parcelable) quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });

    }


    private void setHinhAnhUser(final CircleImageView circleImageuser, String linkhinh)
    {
        StorageReference storageReferenceHinhAnhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024*1024;
        storageReferenceHinhAnhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageuser.setImageBitmap(bitmap);
            }
        });
    }
    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }

}
