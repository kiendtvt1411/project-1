package dhbkhn.kien.kienmessenger.Adapter.ChatDon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.ViewHolderChat;
import dhbkhn.kien.kienmessenger.Model.Object.ChatMessage;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/24/2016.
 */
public class ChatDonAdapter extends RecyclerView.Adapter<ViewHolderChat> {
    Context mContext;
    List<ChatMessage> dsChat;
    String nguoidung, emailnguoidung;

    public ChatDonAdapter(Context mContext, List<ChatMessage> dsChat, String nguoidung, String emailnguoidung) {
        this.mContext = mContext;
        this.dsChat = dsChat;
        this.nguoidung = nguoidung;
        this.emailnguoidung = emailnguoidung;
    }

    @Override
    public ViewHolderChat onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_item_chat, parent, false);
        ViewHolderChat holder = new ViewHolderChat(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderChat holder, final int position) {
        ChatMessage mess = dsChat.get(position);
        final boolean[] b = {mess.isClick()};
        Log.d("myChattt", emailnguoidung + " " + mess.getAuthor());
        if (!mess.getAuthor().equals(emailnguoidung)) {
            holder.llLeft.setVisibility(View.VISIBLE);
            holder.tvItemAuthorLeft.setVisibility(View.VISIBLE);
            holder.tvItemAuthorLeft.setText(mess.getAuthor());
            holder.tvItemChatLeft.setText(mess.getContent());
            holder.llRight.setVisibility(View.GONE);
            holder.tvItemStatusRight.setVisibility(View.GONE);
            if (b[0]) {
                holder.tvChatDate.setVisibility(View.VISIBLE);
                holder.tvItemStatusLeft.setVisibility(View.VISIBLE);
                holder.tvChatDate.setText(mess.getDate());
                holder.tvItemStatusLeft.setText(mess.getStatus());
            }else {
                holder.tvChatDate.setVisibility(View.GONE);
                holder.tvItemStatusLeft.setVisibility(View.GONE);
            }
            holder.llLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ChatMessage m : dsChat) {
                        if (dsChat.indexOf(m) == position) {
                            b[0] = !b[0];
                            m.setClick(b[0]);
                            notifyDataSetChanged();
                        }
                        else {
                            m.setClick(false);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }else {
            holder.llRight.setVisibility(View.VISIBLE);
            holder.tvItemAuthorRight.setText(mess.getAuthor());
            holder.tvItemChatRight.setText(mess.getContent());
            holder.llLeft.setVisibility(View.GONE);
            holder.tvItemStatusLeft.setVisibility(View.GONE);
            if (b[0]) {
                holder.tvChatDate.setVisibility(View.VISIBLE);
                holder.tvItemStatusRight.setVisibility(View.VISIBLE);
                holder.tvChatDate.setText(mess.getDate());
                holder.tvItemStatusRight.setText(mess.getStatus());
            }else {
                holder.tvChatDate.setVisibility(View.GONE);
                holder.tvItemStatusRight.setVisibility(View.GONE);
            }
            holder.llRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (ChatMessage m : dsChat) {
                        if (dsChat.indexOf(m) == position) {
                            b[0] = !b[0];
                            m.setClick(b[0]);
                            notifyDataSetChanged();
                        }
                        else {
                            m.setClick(false);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dsChat.size();
    }
}
