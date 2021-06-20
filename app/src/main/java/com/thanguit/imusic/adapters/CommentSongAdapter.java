package com.thanguit.imusic.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thanguit.imusic.R;
import com.thanguit.imusic.SharedPreferences.DataLocalManager;
import com.thanguit.imusic.models.Comment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentSongAdapter extends RecyclerView.Adapter<CommentSongAdapter.ViewHolder> {
    private static final String TAG = "CommentSongAdapter";

    private Context context;
    private List<Comment> commentList;

    public CommentSongAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentSongAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "User avatar:" + this.commentList.get(position).getUserImage());
        Picasso.get()
                .load(this.commentList.get(position).getUserImage())
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.civAvatarComment);
        holder.tvUserComment.setText(this.commentList.get(position).getUserName().trim());
        holder.tvTimeComment.setText(Handle_Date(this.commentList.get(position).getDate()).trim());
        holder.tvContentComment.setText(this.commentList.get(position).getContent().trim());

        if (this.commentList.get(position).getIdUser().trim().equals(DataLocalManager.getUserID())) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });
        }
    }

    private String Handle_Date(String date) {
        // Code lấy từ link này nè: https://vnsharebox.com/blog/convert-string-to-datetime-android/
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date datetime_1 = simpleDateFormat.parse(date); // Chuyển String ngày nhập vào thành Date

            String currentTime = simpleDateFormat.format(calendar.getTime()); // calendar.getTime(): Trả về đối tượng Date dựa trên giá trị của Calendar.
            Date datetime_2 = simpleDateFormat.parse(currentTime); // Chuyển String ngày nhập vào thành Date

            long diff = datetime_2.getTime() - datetime_1.getTime();
            int hours = (int) (diff / (60 * 60 * 1000));
            int minutes = (int) (diff / (1000 * 60)) % 60;
            int days = (int) (diff / (24 * 60 * 60 * 1000));

            if (days > 0) {
                return " - " + days + " " + context.getString(R.string.days);
            } else {
                if (hours > 0) {
                    return " - " + hours + context.getString(R.string.hours);
                } else {
                    return " - " + minutes + " " + context.getString(R.string.minutes);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return " - " + context.getString(R.string.today);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatarComment;
        private TextView tvUserComment;
        private TextView tvTimeComment;
        private TextView tvContentComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civAvatarComment = itemView.findViewById(R.id.civAvatarComment);
            tvUserComment = itemView.findViewById(R.id.tvUserComment);
            tvTimeComment = itemView.findViewById(R.id.tvTimeComment);
            tvContentComment = itemView.findViewById(R.id.tvContentComment);
        }
    }
}
