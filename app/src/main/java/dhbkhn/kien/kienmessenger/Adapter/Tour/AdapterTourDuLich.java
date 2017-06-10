package dhbkhn.kien.kienmessenger.Adapter.Tour;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour.ChiTietTour;

/**
 * Created by kiend on 11/10/2016.
 */
public class AdapterTourDuLich extends FirebaseRecyclerAdapter<TourDuLich, ViewHolderTour> {
    Context mContext;
    Class<TourDuLich> modelClass;
    int modelLayout;
    Class<ViewHolderTour> viewHolderClass;
    String tenCongTy;
    Query ref;
    public AdapterTourDuLich(Context mContext, Class<TourDuLich> modelClass, int modelLayout
            , Class<ViewHolderTour> viewHolderClass, Query ref, String tenCongTy) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.tenCongTy = tenCongTy;
    }

    @Override
    protected void populateViewHolder(final ViewHolderTour viewHolder, final TourDuLich model, int position) {
        String phuongtien = model.getPhuong_tien();
        if(model.getAnh_bia_url()!=null) hienThiAnh(model.getAnh_bia_url(),viewHolder.imgAnhBiaTour);
        if (phuongtien.equals("Ô tô")) {
            viewHolder.imgPhuongTienTour.setImageResource(R.drawable.car);
        } else if (phuongtien.equals("Máy bay")) {
            viewHolder.imgPhuongTienTour.setImageResource(R.drawable.plane);
        } else if (phuongtien.equals("Tàu hỏa")) {
            viewHolder.imgPhuongTienTour.setImageResource(R.drawable.train);
        } else if (phuongtien.equals("Tàu thủy")) {
            viewHolder.imgPhuongTienTour.setImageResource(R.drawable.boat);
        }
        viewHolder.tvItemXuatPhatTour.setText(model.getXuat_phat());
        viewHolder.tvThoiGianTour.setText(model.getThoi_gian());
        viewHolder.tvLichTrinhTour.setText(model.getTen_tour());
        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(model.getGia_tien()).toString();
        viewHolder.tvGiaTour.setText(gia + " VNĐ");
        viewHolder.tvNguoiToChucTour.setText(model.getLien_he());
        viewHolder.tvSoDienThoaiTour.setText(model.getSdt());
        viewHolder.cvChiTietTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTiet = new Intent(mContext, ChiTietTour.class);
                iChiTiet.putExtra("tour", model);
                iChiTiet.putExtra("tencty",tenCongTy);
                mContext.startActivity(iChiTiet);
            }
        });
        viewHolder.imgMenuTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(mContext, viewHolder.imgMenuTour);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_status,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int vt = item.getItemId();
                        switch (vt) {
                            case R.id.item_status_share:
                                break;
                            case R.id.item_status_update:
                                break;
                            case R.id.item_status_delete:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
