package dhbkhn.kien.kienmessenger.Adapter.Status;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/17/2016.
 */
public class ViewHolderStatus extends RecyclerView.ViewHolder {
    public ImageView imgStatus, imgAnh1Status, imgAnh2Status, imgAnh3Status, imgLike, imgSettingStatus;
    public TextView tvNhieuAnh, tvUsernameStatus, tvLocationStatus, tvDateStatus, tvContentStatus, tvLike, tvComment, tvShare,tvTransactionLike, tvTransactionComment, tvTransactionShare;
    public LinearLayout llLike, llAnh;

    //layout_type_2
    public TextView tvNguoiShare, tvNguoiDuocShare1, tvNguoiDuocShare2,tvLocationStatusDuocShare
            ,tvDateStatusDuocShare, tvContentStatusDuocShare, tvThoiGianTour, tvNhieuAnhDuocShare;
    public LinearLayout llAnhStatusDuocShare;
    public ImageView imgAvatarNguoiDuocShare, imgAnh1StatusDuocShare
            ,imgAnh2StatusDuocShare, imgAnh3StatusDuocShare;

    public ViewHolderStatus(View itemView) {
        super(itemView);
        imgStatus = (ImageView) itemView.findViewById(R.id.imgStatus);
        imgAnh1Status = (ImageView) itemView.findViewById(R.id.imgAnh1Status);
        imgAnh2Status = (ImageView) itemView.findViewById(R.id.imgAnh2Status);
        imgAnh3Status = (ImageView) itemView.findViewById(R.id.imgAnh3Status);
        imgLike = (ImageView) itemView.findViewById(R.id.imgLike);
        tvNhieuAnh = (TextView) itemView.findViewById(R.id.tvNhieuAnh);
        tvUsernameStatus = (TextView) itemView.findViewById(R.id.tvUsernameStatus);
        tvDateStatus = (TextView) itemView.findViewById(R.id.tvDateStatus);
        tvLocationStatus = (TextView) itemView.findViewById(R.id.tvLocationStatus);
        tvContentStatus = (TextView) itemView.findViewById(R.id.tvContentStatus);
        tvTransactionLike = (TextView) itemView.findViewById(R.id.tvTransactionLike);
        tvTransactionComment = (TextView) itemView.findViewById(R.id.tvTransactionComment);
        tvTransactionShare = (TextView) itemView.findViewById(R.id.tvTransactionShare);
        tvLike = (TextView) itemView.findViewById(R.id.tvLike);
        tvComment = (TextView) itemView.findViewById(R.id.tvComment);
        tvShare = (TextView) itemView.findViewById(R.id.tvShare);
        llLike = (LinearLayout) itemView.findViewById(R.id.llLike);
        llAnh = (LinearLayout) itemView.findViewById(R.id.llAnh);
        imgSettingStatus = (ImageView) itemView.findViewById(R.id.imgSettingStatus);

        //for layout_type_2
        tvNguoiShare = (TextView) itemView.findViewById(R.id.tvNguoiShare);
        tvNguoiDuocShare1 = (TextView) itemView.findViewById(R.id.tvNguoiDuocShare1);
        tvNguoiDuocShare2 = (TextView) itemView.findViewById(R.id.tvNguoiDuocShare2);
        tvLocationStatusDuocShare = (TextView) itemView.findViewById(R.id.tvLocationStatusDuocShare);
        tvDateStatusDuocShare = (TextView) itemView.findViewById(R.id.tvDateStatusDuocShare);
        tvContentStatusDuocShare = (TextView) itemView.findViewById(R.id.tvContentStatusDuocShare);
        tvThoiGianTour = (TextView) itemView.findViewById(R.id.tvThoiGianTour);
        tvNhieuAnhDuocShare = (TextView) itemView.findViewById(R.id.tvNhieuAnhDuocShare);
        llAnhStatusDuocShare = (LinearLayout) itemView.findViewById(R.id.llAnhStatusDuocShare);
        imgAvatarNguoiDuocShare = (ImageView) itemView.findViewById(R.id.imgAvatarNguoiDuocShare);
        imgAnh1StatusDuocShare = (ImageView) itemView.findViewById(R.id.imgAnh1StatusDuocShare);
        imgAnh2StatusDuocShare = (ImageView) itemView.findViewById(R.id.imgAnh2StatusDuocShare);
        imgAnh3StatusDuocShare = (ImageView) itemView.findViewById(R.id.imgAnh3StatusDuocShare);
    }
}
