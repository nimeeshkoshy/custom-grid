package com.example.customgridview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<Item> itemList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public ItemAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.title.setText(item.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(item.getImage())
                .placeholder(R.drawable.image)
                .into(holder.imageView);

        holder.editButton.setOnClickListener(v -> listener.onEditClick(position));
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView imageView;
        private final ImageView editButton;
        private final ImageView deleteButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            imageView = itemView.findViewById(R.id.itemImage);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

