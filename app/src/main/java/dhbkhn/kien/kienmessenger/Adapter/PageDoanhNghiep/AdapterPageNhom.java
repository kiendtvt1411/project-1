package dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.PageDoanhNghiep;
import dhbkhn.kien.kienmessenger.View.ThemPageGroup.ChiTietGroupPage;

/**
 * Created by kiend on 11/4/2016.
 */
public class AdapterPageNhom extends FirebaseRecyclerAdapter<PageDoanhNghiep, ViewHolderPageNhom> {
    Context mContext;
    Class<PageDoanhNghiep> modelClass;
    int modelLayout;
    Class<ViewHolderPageNhom> viewHolderClass;
    Query ref;
    public AdapterPageNhom(Context mContext, Class<PageDoanhNghiep> modelClass, int modelLayout, Class<ViewHolderPageNhom> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
    }

    @Override
    protected void populateViewHolder(ViewHolderPageNhom viewHolder, final PageDoanhNghiep model, int position) {
        hienThiAnh(model.getAnh_bia_url_cty(),viewHolder.imgItemAnhBiaPageNhom);
        hienThiAnh(model.getAvatar_url_cty(), viewHolder.imgItemAvatarPageNhom);
        viewHolder.tvItemTenPageNhom.setText(model.getTen_cty());
        viewHolder.tvItemDiaChiPageNhom.setText(model.getDiachi_cty());
        viewHolder.cvPageNhomThamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTiet = new Intent(mContext, ChiTietGroupPage.class);
                iChiTiet.putExtra("page", model);
                iChiTiet.putExtra("tencty",model.getTen_cty());
                iChiTiet.putExtra("keyCongTy", model.getKey_page_cty());
                mContext.startActivity(iChiTiet);
            }
        });
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
