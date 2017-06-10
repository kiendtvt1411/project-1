package dhbkhn.kien.kienmessenger.Adapter.Status.Comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/21/2016.
 */
public class ViewHolderComment extends RecyclerView.ViewHolder {
    public ImageView imgAvatarComment, imgSettingComment, imgLikeComment,imgAnhMinhHoaComment;
    public TextView tvUsernameComment,tvLocationComment,tvContentComment,tvTransactionLikeComment,tvLikeComment,tvReplyComment,tvTimeComment;
    public ViewHolderComment(View itemView) {
        super(itemView);
        imgAvatarComment = (ImageView) itemView.findViewById(R.id.imgAvatarComment);
        imgSettingComment = (ImageView) itemView.findViewById(R.id.imgSettingComment);
        imgAnhMinhHoaComment = (ImageView) itemView.findViewById(R.id.imgAnhMinhHoaComment);
        imgLikeComment = (ImageView) itemView.findViewById(R.id.imgLikeComment);
        tvUsernameComment = (TextView) itemView.findViewById(R.id.tvUsernameComment);
        tvLocationComment = (TextView) itemView.findViewById(R.id.tvLocationComment);
        tvContentComment = (TextView) itemView.findViewById(R.id.tvContentComment);
        tvTransactionLikeComment = (TextView) itemView.findViewById(R.id.tvTransactionLikeComment);
        tvLikeComment = (TextView) itemView.findViewById(R.id.tvLikeComment);
        tvReplyComment = (TextView) itemView.findViewById(R.id.tvReplyComment);
        tvTimeComment = (TextView) itemView.findViewById(R.id.tvTimeComment);
    }
}
