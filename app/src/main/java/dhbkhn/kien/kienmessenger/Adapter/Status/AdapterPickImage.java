package dhbkhn.kien.kienmessenger.Adapter.Status;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/19/2016.
 */
public class AdapterPickImage extends RecyclerView.Adapter<AdapterPickImage.ViewHolderPickImage> {
    Activity activity;
    List<Bitmap>dsBitmap;

    public AdapterPickImage(Activity activity, List<Bitmap> dsBitmap) {
        this.activity = activity;
        this.dsBitmap = dsBitmap;
    }

    @Override
    public ViewHolderPickImage onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_pick_image_to_status, parent, false);
        ViewHolderPickImage viewHolderPickImage = new ViewHolderPickImage(row);
        return viewHolderPickImage;
    }

    @Override
    public void onBindViewHolder(ViewHolderPickImage holder, final int position) {
        Bitmap bm = dsBitmap.get(position);
        holder.imgPickForStatus.setImageBitmap(bm);
        holder.imgClearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dsBitmap.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsBitmap.size();
    }

    public class ViewHolderPickImage extends RecyclerView.ViewHolder {
        ImageView imgPickForStatus, imgClearImg;
        public ViewHolderPickImage(View itemView) {
            super(itemView);
            imgPickForStatus = (ImageView) itemView.findViewById(R.id.imgPickForStatus);
            imgClearImg = (ImageView) itemView.findViewById(R.id.imgClearImg);
        }
    }
}
