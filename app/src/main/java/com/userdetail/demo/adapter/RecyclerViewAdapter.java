package com.userdetail.demo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.userdetail.demo.R;
import com.userdetail.demo.constants.GlobalConstants;
import com.userdetail.demo.model.Datum;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Activity context;
    List<Datum> datumList;

    public RecyclerViewAdapter(Activity context, List<Datum> datumList) {
        this.context = context;
        this.datumList = datumList;
    }

    /**
     * List Update
     */
    public void updateRecyclerViewAdapter(List<Datum> morePhotoList) {
        this.datumList = morePhotoList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Datum datum = datumList.get(position);
        if(datum != null) {

            holder.nameTxt.setText(datum.getName());
            holder.emailTxt.setText(datum.getEmail());
            holder.genderTxt.setText(datum.getGender());

            if(datum.getStatus() != null && datum.getStatus().length()>0) {
                holder.statusTxt.setText(datum.getStatus());

                if(datum.getStatus().equalsIgnoreCase(GlobalConstants.ACTIVE)){
                    holder.statusImg.setImageResource(R.drawable.circle_green);
                }else{
                    holder.statusImg.setImageResource(R.drawable.circle_red);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return datumList.size()>0?datumList.size():0;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTxt, emailTxt, genderTxt, statusTxt;
        ImageView statusImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            emailTxt = itemView.findViewById(R.id.email_txt);
            genderTxt = itemView.findViewById(R.id.gender_txt);
            statusTxt = itemView.findViewById(R.id.status_txt);
            statusImg = itemView.findViewById(R.id.status_img);
        }
    }
}
