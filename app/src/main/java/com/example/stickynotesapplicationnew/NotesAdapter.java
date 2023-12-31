package com.example.stickynotesapplicationnew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>{



    private Context context;
    private List<NotesModel> notesModelList;

    public NotesAdapter(Context context) {
        this.context = context;
        notesModelList = new ArrayList<>();
    }

    public void add(NotesModel notesModel)
    {
        notesModelList.add(notesModel);
        notifyDataSetChanged();
    }

    public void clear(){
        notesModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NotesModel notesModel = notesModelList.get(position);
        holder.title1.setText(notesModel.getTitle());
        holder.content1.setText(notesModel.getContents());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id",notesModel.getId());
                intent.putExtra("title",notesModel.getTitle());
                intent.putExtra("content",notesModel.getContents());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return notesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title1,content1;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            title1 = itemView.findViewById(R.id.titlenote);
            content1 = itemView.findViewById(R.id.contentnote);
        }
    }

}
