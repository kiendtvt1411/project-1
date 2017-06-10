package dhbkhn.kien.kienmessenger.Adapter.ChatNhom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/27/2016.
 */
public class AdapterNhapThemAnhNguoiTrongNhom extends RecyclerView.Adapter<AdapterNhapThemAnhNguoiTrongNhom.ViewHolderThemAnh> {
    Context mContext;
    List<Friend> dsF;

    public AdapterNhapThemAnhNguoiTrongNhom(Context mContext, List<Friend> dsF) {
        this.mContext = mContext;
        this.dsF = dsF;
    }

    @Override
    public ViewHolderThemAnh onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_nhap_them_nhom_chat,parent,false);
        ViewHolderThemAnh holder = new ViewHolderThemAnh(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderThemAnh holder, int position) {
        holder.imgAvatarNhapThemNhom.setImageResource(R.drawable.xinh1);
    }

    @Override
    public int getItemCount() {
        return dsF.size();
    }

    public class ViewHolderThemAnh extends RecyclerView.ViewHolder {
        ImageView imgAvatarNhapThemNhom;
        public ViewHolderThemAnh(View itemView) {
            super(itemView);
            imgAvatarNhapThemNhom = (ImageView) itemView.findViewById(R.id.imgAvatarNhapThemNhom);
        }
    }
}
