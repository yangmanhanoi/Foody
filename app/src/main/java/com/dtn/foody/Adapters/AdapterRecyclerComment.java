package com.dtn.foody.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Model.BinhLuanModel;
import com.dtn.foody.R;
import com.dtn.foody.View.HienThiChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class AdapterRecyclerComment extends RecyclerView.Adapter<AdapterRecyclerComment.ViewHolder> {
    Context context;
    int layout;
    List<Bitmap> listHinh;
    BinhLuanModel binhLuanModel;
    boolean isChiTietBinhLuan;
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhBinhLuan;
        TextView tongHinhBinhLuan;
        FrameLayout khungHinhBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhBinhLuan = itemView.findViewById(R.id.hinhBinhluan);
            tongHinhBinhLuan = itemView.findViewById(R.id.tongHinhBinhLuan);
            khungHinhBinhLuan = itemView.findViewById(R.id.khunghinhBinhLuan);
        }
    }
    public AdapterRecyclerComment(Context context, int layout, List<Bitmap> listHinh, boolean isChiTietBinhLuan, BinhLuanModel binhLuanModel) {
        this.context = context;
        this.layout = layout;
        this.listHinh = listHinh;
        this.binhLuanModel = binhLuanModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;
    }
    @NonNull
    @Override
    public AdapterRecyclerComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerComment.ViewHolder holder,final int position) {
        holder.hinhBinhLuan.setImageBitmap(listHinh.get(position));
        if(!isChiTietBinhLuan)
        {
            if(position == 3)
            {
                int x = listHinh.size() - 4;
                if(x > 0)
                {
                    holder.tongHinhBinhLuan.setText("+" +x);
                    holder.khungHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.khungHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent ichiTietBinhLuan = new Intent(context, HienThiChiTietQuanAnActivity.class);
                            ichiTietBinhLuan.putExtra("binhluanmodel", binhLuanModel);
                            context.startActivity(ichiTietBinhLuan);
                        }
                    });
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        if(!isChiTietBinhLuan) return 4;
        else return listHinh.size();
    }
}
