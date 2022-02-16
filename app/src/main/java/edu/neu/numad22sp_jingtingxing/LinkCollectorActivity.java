package edu.neu.numad22sp_jingtingxing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<LinkModel> links;
    private LinkAdapter adapter;
    private FloatingActionButton btnFabAdd;
    private RecyclerView recyclerView;
    private EditText linkName;
    private EditText linkUrl;
    private AlertDialog inputAlertDialog;
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        links = new ArrayList<>();
        init(savedInstanceState);

        btnFabAdd = findViewById(R.id.btnFabAdd);
        btnFabAdd.setOnClickListener(v -> addLink());
        createRecyclerView();
        createInputAlertDialog();

        adapter.setOnLinkClickListener(position -> links.get(position).onLinkUnitClicked(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                links.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void addLink() {
        linkName.getText().clear();
        linkUrl.setText("http://");
        linkName.requestFocus();
        inputAlertDialog.show();
    }

    public void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerLink);
        recyclerView.setHasFixedSize(true);
        adapter = new LinkAdapter(this, links);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void createInputAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.link_list_view, null);

        linkName = view.findViewById(R.id.edtName);
        linkUrl = view.findViewById(R.id.edtUrl);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Add),
                        (dialog, id) -> {
                            LinkModel link = new LinkModel(linkName.getText().toString(), linkUrl.getText().toString());
                            if (link.isValid()) {
                                links.add(0, link);
                                adapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, "Add link successfully!", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(recyclerView, "Invalid input. Please check.", Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        inputAlertDialog = alertDialogBuilder.create();
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = links == null ? 0 : links.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put linkName information id into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", links.get(i).getLinkName());
            // put linkURL information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", links.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (links == null || links.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String linkURL = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");

                    LinkModel model = new LinkModel(linkName, linkURL);

                    links.add(model);
                }
            }
        }

    }

}

