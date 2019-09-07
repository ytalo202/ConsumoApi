package com.example.consumoandroid;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consumoandroid.model.Task;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterHolder> {

    private List<Task> taskList;

    private OnItemClickLister mLister;
    private MainView mainView;

    Context mContext;
    public static class TaskAdapterHolder extends  RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mName;
        public TextView mType;
        public TextView mValue;
        public ImageView mImgDelete;
        public ImageView mImgEdit;

        public TaskAdapterHolder(View itemView, final OnItemClickLister lister) {
            super(itemView);
//            mImageView = itemView.findViewById(R.id.imagenPica);
            mName = itemView.findViewById(R.id.txtName);
            mType = itemView.findViewById(R.id.txtType);
            mValue = itemView.findViewById(R.id.txtValue);
            mImgDelete = itemView.findViewById(R.id.delete);
            mImgEdit = itemView.findViewById(R.id.edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lister != null){
                        int position = getAdapterPosition();
                       if (position!= RecyclerView.NO_POSITION){
                           lister.onItemClick(position);
                       }
                    }
                }
            });
//            mImageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (lister != null){
//                        int position = getAdapterPosition();
//                        if (position!= RecyclerView.NO_POSITION){
//                            lister.onDeleteClick(position);
//                        }
//                    }
//                }
//            });
        }
    }

    public void setOnItemClick(OnItemClickLister lister){
        mLister = lister;

    }

    public TaskAdapter(List<Task> userList, Context context ,MainView mainView){
        taskList = userList;
        mContext = context;
        this.mainView = mainView;
    }


    @NonNull
    @Override
    public TaskAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        TaskAdapterHolder evk = new TaskAdapterHolder(v,mLister);
        return  evk;

    }



    public interface OnItemClickLister {
        void  onItemClick(int position);
//        void  onDeleteClick(int position);
    }


    public void load(List<Task> taskList) {
        this.taskList = taskList;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskAdapterHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.mName.setText(task.getName());
        holder.mType.setText(String.valueOf(task.getType()));
        holder.mValue.setText(String.valueOf(task.getValue()));

        holder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "holaaaa "+ task.getName(), Toast.LENGTH_SHORT).show();

                mainView.deleteItem(task);
            }
        });



        holder.mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "holaaaa "+ task.getName(), Toast.LENGTH_SHORT).show();

                mainView.editItem(task);
            }
        });
//     String url = "https://www.pasionfutbol.com/__export/1532545089176/sites/pasionlibertadores/img/2018/07/25/mario-ricardo-cristobal-colon-de-paraguay-1_2.jpg_1151390924.jpg";
//        Picasso.with(mContext).load(url).into(holder.mImageView);
//        Picasso.with(mContext).setIndicatorsEnabled(true);

//        holder.mImageView.setImageResource(R.drawable.f5);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


}
