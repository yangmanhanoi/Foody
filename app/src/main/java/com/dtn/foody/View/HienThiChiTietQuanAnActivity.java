package com.dtn.foody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtn.foody.Adapters.AdapterRecyclerComment;
import com.dtn.foody.Model.BinhLuanModel;
import com.dtn.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietQuanAnActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txtTieudeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
    RecyclerView recyclerViewBinhLuan;
    List<Bitmap> bitmapList;
    BinhLuanModel binhLuanModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);

        circleImageView = findViewById(R.id.circleImageuser);
        txtTieudeBinhLuan = findViewById(R.id.txtTieudeBinhLuan);
        txtNoiDungBinhLuan = findViewById(R.id.txtNoiDungBinhLuan);
        txtSoDiem = findViewById(R.id.chandiemBinhLuan1);
        recyclerViewBinhLuan = findViewById(R.id.recyclerviewHinhBinhLuan);

        bitmapList = new ArrayList<>();
        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");

        txtTieudeBinhLuan.setText(binhLuanModel.getTieude());
        txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        txtSoDiem.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhUser(circleImageView, binhLuanModel.getThanhVienModel().getHinhanh());

        for (String linkhinh : binhLuanModel.getListHinhAnh()) {
            StorageReference storageReferenceHinhAnhUser = FirebaseStorage.getInstance().getReference().child("HinhquanAn").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReferenceHinhAnhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getListHinhAnh().size()) {
                        AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(HienThiChiTietQuanAnActivity.this, R.layout.layout_custom_hinhbinhluan, bitmapList, true, binhLuanModel);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietQuanAnActivity.this, 2);
                        recyclerViewBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewBinhLuan.setAdapter(adapterRecyclerComment);
                        adapterRecyclerComment.notifyDataSetChanged();
                    }
                }
            });
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
