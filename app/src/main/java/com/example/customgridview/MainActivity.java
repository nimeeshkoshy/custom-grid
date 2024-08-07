package com.example.customgridview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fab_add);

        itemList = new ArrayList<>();


        itemList.add(new Item("kashmir" , ContextCompat.getDrawable(this, R.drawable.kashmir)));
        itemList.add(new Item("meghalaya" , ContextCompat.getDrawable(this, R.drawable.meghalaya)));
        itemList.add(new Item("delhi" , ContextCompat.getDrawable(this, R.drawable.delhi)));
        itemList.add(new Item("sikim" , ContextCompat.getDrawable(this, R.drawable.sikim)));

        adapter = new ItemAdapter( itemList, this);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2
        ));
        recyclerView.setAdapter(adapter);

        // Set up the Floating Action Button to add new items
        fabAdd.setOnClickListener(v -> showAddItemDialog());
    }

    @Override
    public void onEditClick(int position) {
        showEditItemDialog(position);
    }

    @Override
    public void onDeleteClick(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyDataSetChanged();
        }
    }

    private void showAddItemDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        EditText titleInput = dialogView.findViewById(R.id.editTitle);
        ImageView imageView = dialogView.findViewById(R.id.imageView);

        imageView.setOnClickListener(v -> {
            // Implement image picker functionality here if needed
        });

        builder.setTitle("Add New Item")
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    Drawable imageDrawable = ContextCompat.getDrawable(this, R.drawable.tajmahal); // Default image

                    itemList.add(new Item(title, imageDrawable));
                    adapter.notifyDataSetChanged();  // Notify the adapter that the data set has changed
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void showEditItemDialog(int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        EditText titleInput = dialogView.findViewById(R.id.editTitle);
        ImageView imageView = dialogView.findViewById(R.id.imageView);

        Item item = itemList.get(position);
        titleInput.setText(item.getTitle());
        imageView.setImageDrawable(item.getImage());

        imageView.setOnClickListener(v -> {

        });

        builder.setTitle("Edit Item")
                .setPositiveButton("Update", (dialog, which) -> {
                    item.setTitle(titleInput.getText().toString());
                    item.setImage(imageView.getDrawable());
                    adapter.notifyItemChanged(position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create().show();
    }
}



