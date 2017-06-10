package dhbkhn.kien.kienmessenger.Adapter.Tour;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 11/10/2016.
 */
public class AdapterListLoTrinh extends RecyclerView.Adapter<AdapterListLoTrinh.ViewHolderLoTrinh> {
    Context mContext;
    List<String> dsLotrinh, dsKeyLoTrinh;

    public AdapterListLoTrinh(Context mContext, List<String> dsLotrinh, List<String>dsKeyLoTrinh) {
        this.mContext = mContext;
        this.dsLotrinh = dsLotrinh;
        this.dsKeyLoTrinh = dsKeyLoTrinh;
    }

    @Override
    public ViewHolderLoTrinh onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_item_lotrinh, parent, false);
        ViewHolderLoTrinh holder = new ViewHolderLoTrinh(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderLoTrinh holder, final int position) {
        String lotrinh = dsLotrinh.get(position);
        holder.tvItemLoTrinh.setText(lotrinh);
        holder.imgClearLoTrinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsLotrinh.remove(position);
                dsKeyLoTrinh.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsLotrinh.size();
    }

    public class ViewHolderLoTrinh extends RecyclerView.ViewHolder {
        TextView tvItemLoTrinh;
        ImageView imgClearLoTrinh;

        public ViewHolderLoTrinh(View itemView) {
            super(itemView);
            tvItemLoTrinh = (TextView) itemView.findViewById(R.id.tvItemLoTrinh);
            imgClearLoTrinh = (ImageView) itemView.findViewById(R.id.imgClearLoTrinh);
        }
    }
}
