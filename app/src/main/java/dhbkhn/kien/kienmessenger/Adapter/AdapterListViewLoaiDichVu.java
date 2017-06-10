package dhbkhn.kien.kienmessenger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.LoaiDichVu.LoaiDichVu;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/9/2016.
 */
public class AdapterListViewLoaiDichVu extends ArrayAdapter<LoaiDichVu>{
    Context context;
    int resource;
    List<LoaiDichVu> objects;

    public AdapterListViewLoaiDichVu(Context context, int resource, List<LoaiDichVu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(this.resource, parent, false);
        ImageView imgLoaiDichVu = (ImageView) row.findViewById(R.id.imgLoaiDichVu);
        TextView tvLoaiDichVu = (TextView) row.findViewById(R.id.tvLoaiDichVu);

        if (position == 0) {
            imgLoaiDichVu.setVisibility(View.GONE);
            tvLoaiDichVu.setText("Tìm tất cả dịch vụ");
        }else {
            imgLoaiDichVu.setVisibility(View.VISIBLE);
            LoaiDichVu loaiDichVu = this.objects.get(position);
            tvLoaiDichVu.setText(loaiDichVu.getTENLOAIDV());
            Picasso.with(this.context).load(loaiDichVu.getICONDV()).resize(50,50).centerCrop().into(imgLoaiDichVu);
        }

        return row;
    }
}
