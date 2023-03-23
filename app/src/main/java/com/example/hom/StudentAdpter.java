package com.example.hom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentAdpter extends RecyclerView.Adapter<StudentAdpter.ViewHolder> {

    Context context;
    List<StudentModel> studentModelsList;

    public StudentAdpter(Context context, List<StudentModel> studentModelsList) {
        this.context = context;
        this.studentModelsList = studentModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_row_for_recycleview,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            StudentModel studentmodel = studentModelsList.get(position);
            holder.tvlast.setText("lastname="+ studentmodel.getLastname());
            holder.tvfrist.setText("Fristname" +studentmodel.getFristname());

            String imageUri=null;
            imageUri = studentmodel.getImage();
            Picasso.get().load(imageUri).into(holder.imageview);

    }

    @Override
    public int getItemCount() {
        return studentModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView tvfrist,tvlast;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.image_recycle_id);
            tvfrist = itemView.findViewById(R.id.tvfristname_recycle_id);
            tvlast = itemView.findViewById(R.id.tvlastname_recycle_id);

        }
    }
}
