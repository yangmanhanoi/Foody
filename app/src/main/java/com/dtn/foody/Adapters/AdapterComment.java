package com.dtn.foody.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Model.BinhLuanModel;
import com.dtn.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {
    Context context;
    List<BinhLuanModel> binhLuanModelList;
    int layout;
    List<Bitmap> bitmapList;
    public AdapterComment(Context context, int layout, List<BinhLuanModel> binhLuanModelList)
    {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
        bitmapList = new ArrayList<>();
    }
    @NonNull
    @Override
    public AdapterComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComment.ViewHolder holder, int position) {
        BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieuDe.setText(binhLuanModel.getTieude());
        holder.txtNoiDung.setText(binhLuanModel.getNoidung());
        holder.chamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhUser(holder.circleImageUser, binhLuanModel.getThanhVienModel().getHinhanh());

        for (String linkhinh : binhLuanModel.getListHinhAnh()) {
            StorageReference storageReferenceHinhAnhUser = FirebaseStorage.getInstance().getReference().child("HinhquanAn").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReferenceHinhAnhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getListHinhAnh().size()) {
                        AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(context, R.layout.layout_custom_hinhbinhluan, bitmapList, false, binhLuanModel);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerComment);
                        adapterRecyclerComment.notifyDataSetChanged();
                    }
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        int soBinhluan = binhLuanModelList.size();
        if(soBinhluan > 5)
        {
            return 5;
        }
        else
        {
            return binhLuanModelList.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageUser;
        TextView txtTieuDe, txtNoiDung, chamDiemBinhLuan;
        RecyclerView recyclerViewHinhBinhLuan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.txtTieudeBinhLuan);
            txtNoiDung = itemView.findViewById(R.id.txtNoiDungBinhLuan);
            circleImageUser = itemView.findViewById(R.id.circleImageuser);
            chamDiemBinhLuan = itemView.findViewById(R.id.chandiemBinhLuan1);
            recyclerViewHinhBinhLuan = itemView.findViewById(R.id.recyclerviewHinhBinhLuan);
        }

        @Override
        public String toString() {
            return super.toString();
        }
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
}
