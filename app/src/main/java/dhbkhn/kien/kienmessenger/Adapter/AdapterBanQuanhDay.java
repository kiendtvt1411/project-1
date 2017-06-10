package dhbkhn.kien.kienmessenger.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.BanQuanhDay;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/10/2016.
 */
public class AdapterBanQuanhDay extends RecyclerView.Adapter<AdapterBanQuanhDay.ViewHolderBanQuanhDay> {
    Context mContext;
    List<BanQuanhDay>dsBQD;
    String nguoidung;

    public AdapterBanQuanhDay(Context mContext, List<BanQuanhDay> dsBQD,String nguoidung) {
        this.mContext = mContext;
        this.dsBQD = dsBQD;
        this.nguoidung = nguoidung;
    }

    @Override
    public ViewHolderBanQuanhDay onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_ban_quanh_day, parent, false);
        ViewHolderBanQuanhDay viewHolder = new ViewHolderBanQuanhDay(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBanQuanhDay holder, int position) {
        int[] gioiTinh = new int[]{R.drawable.male, R.drawable.female};
        Random random1 = new Random();
        int gt = random1.nextInt(2);
        Random random2 = new Random();
        int tuoi = random2.nextInt(10) + 18;
        final BanQuanhDay banQuanhDay = dsBQD.get(position);
        holder.tvUsernameBanQuanhDay.setText(banQuanhDay.getTen());
        float kc = Math.round(banQuanhDay.getKhoangCach()*10)/10;
        holder.tvKhoangCachBanQuanhDay.setText(String.valueOf(kc) + "m");
        holder.imgGioiTinhBanQuanhDay.setImageResource(gioiTinh[gt]);
        holder.tvTuoiBanQuanhDay.setText(String.valueOf(tuoi));
    }

    @Override
    public int getItemCount() {
        return dsBQD.size();
    }

    public class ViewHolderBanQuanhDay extends RecyclerView.ViewHolder {
        CardView cvBanQuanhDay;
        TextView tvKhoangCachBanQuanhDay, tvUsernameBanQuanhDay, tvThongTinChiTietBanQuanhDay, tvTuoiBanQuanhDay;
        ImageView imgGioiTinhBanQuanhDay;

        public ViewHolderBanQuanhDay(View itemView) {
            super(itemView);
            cvBanQuanhDay = (CardView) itemView.findViewById(R.id.cvBanQuanhDay);
            tvKhoangCachBanQuanhDay = (TextView) itemView.findViewById(R.id.tvKhoangCachBanQuanhDay);
            tvUsernameBanQuanhDay = (TextView) itemView.findViewById(R.id.tvUsernameBanQuanhDay);
            tvThongTinChiTietBanQuanhDay = (TextView) itemView.findViewById(R.id.tvThongTinChiTietBanQuanhDay);
            tvTuoiBanQuanhDay = (TextView) itemView.findViewById(R.id.tvTuoiBanQuanhDay);
            imgGioiTinhBanQuanhDay = (ImageView) itemView.findViewById(R.id.imgGioiTinhBanQuanhDay);
        }
    }
}
