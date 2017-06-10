package dhbkhn.kien.kienmessenger.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import dhbkhn.kien.kienmessenger.R;


/**
 * Created by kiend on 9/22/2016.
 */
public class ViewHolderChat extends RecyclerView.ViewHolder{
    public TextView tvItemChatLeft,tvItemChatRight;
    public TextView tvItemAuthorLeft,tvItemAuthorRight;
    public TextView tvItemStatusLeft,tvItemStatusRight;
    public TextView tvChatDate;
    public LinearLayout llLeft,llRight;
    public ViewHolderChat(View itemView) {
        super(itemView);
        llLeft = (LinearLayout) itemView.findViewById(R.id.llLeft);
        llRight = (LinearLayout) itemView.findViewById(R.id.llRight);
        tvItemChatLeft = (TextView) itemView.findViewById(R.id.tvItemChatLeft);
        tvItemChatRight = (TextView) itemView.findViewById(R.id.tvItemChatRight);
        tvItemAuthorLeft = (TextView) itemView.findViewById(R.id.tvItemAuthorLeft);
        tvItemAuthorRight = (TextView) itemView.findViewById(R.id.tvItemAuthorRight);
        tvItemStatusLeft = (TextView) itemView.findViewById(R.id.tvItemStatusLeft);
        tvItemStatusRight = (TextView) itemView.findViewById(R.id.tvItemStatusRight);
        tvChatDate = (TextView) itemView.findViewById(R.id.tvChatDate);
    }
}
