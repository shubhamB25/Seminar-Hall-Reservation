package com.example.seminarhall.dataBase;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seminarhall.R;
import com.example.seminarhall.ReservedHall;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {
    private static final String TAG = "ReceiptAdapter";
    Context context;

    private List<ReservedHall> reserveList;
    public ItemClickListener itemClickListener;
    private List<Integer> status;

    public ReceiptAdapter(Context context,List<ReservedHall> halls, List<Integer> stats) {
        reserveList = halls;
        status = stats;
        this.context=context;
        setUpDrawable();
    }

    private void setUpDrawable() {
//        accepted = context.getDrawable(R.drawable.ic_checkbox);
//        error = context.getDrawable(R.drawable.error);
//        waiting = context.getDrawable(R.drawable.ic_bug);
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_layout, parent, false);
        return new ReceiptAdapter.ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        if(holder.image!=null)
        if (status.get(position) == 0) {
            holder.image.setImageResource(R.drawable.inprocess);
        } else if (status.get(position) == 1) {
            holder.image.setImageResource(R.drawable.accepted);
        } else if (status.get(position) == 2) {
            holder.image.setImageResource(R.drawable.rejected);
        }
        holder.sDate.setText(reserveList.get(position).getStartDateText());
        holder.eDate.setText(reserveList.get(position).getEndDateText());
        holder.sTime.setText(reserveList.get(position).getStartTime());
        holder.eTime.setText(reserveList.get(position).getEndTime());
        holder.purpose.setText(reserveList.get(position).getPurpose());
        holder.Hallid.setText(reserveList.get(position).getHallId().toUpperCase());
    }


    @Override
    public int getItemCount() {
        return reserveList.size();
    }

    public void setListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class ReceiptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Hallid, sDate, eDate, sTime,eTime,purpose;
        ImageView image;
        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            Hallid = (TextView) itemView.findViewById(R.id.TextRoom);
            sDate = (TextView) itemView.findViewById(R.id.TextStartDate);
            eDate = (TextView) itemView.findViewById(R.id.TextEndDate);
            sTime = (TextView) itemView.findViewById(R.id.TextStartTime);
            image = (ImageView)itemView.findViewById(R.id.imageView);
            eTime=itemView.findViewById(R.id.TextEndTime);
            purpose = itemView.findViewById(R.id.TextViewPurpose);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
