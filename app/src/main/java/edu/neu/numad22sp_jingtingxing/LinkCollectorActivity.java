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

    ArrayList<LinkModel> links;
    LinkAdapter adapter;
    FloatingActionButton btnFabAdd;
    RecyclerView recyclerView;
    EditText linkName;
    EditText linkUrl;
    AlertDialog inputAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        recyclerView = findViewById(R.id.recyclerLink);
        btnFabAdd = findViewById(R.id.btnFabAdd);
        btnFabAdd.setOnClickListener(v -> addLink());
        links = new ArrayList<>();


        recyclerView.setHasFixedSize(true);
        adapter = new LinkAdapter(this, links);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        createInputAlertDialog();
        adapter.setOnLinkClickListener(position -> links.get(position).onLinkUnitClicked(this));

//        btnFabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog = new Dialog(LinkCollectorActivity.this);
//                dialog.setContentView(R.layout.item);
//
//                EditText edtName = dialog.findViewById(R.id.edtName);
//                EditText edtUrl = dialog.findViewById(R.id.edtUrl);
//                Button btnAddLink = dialog.findViewById(R.id.btnAddLink);
//
//                btnAddLink.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String linkName = "";
//                        String linkUrl = "";
//
//                        if (!edtName.getText().toString().equals("")) {
//                            linkName = edtName.getText().toString();
//                        } else {
//                            Toast.makeText(LinkCollectorActivity.this, "Please enter link name!", Toast.LENGTH_SHORT).show();
//                        }
//
//                        if (!edtUrl.getText().toString().equals("")) {
//                            linkUrl = edtUrl.getText().toString();
//                        } else {
//                            Toast.makeText(LinkCollectorActivity.this, "Please enter link!", Toast.LENGTH_SHORT).show();
//                        }
//
//                        links.add(new LinkModel(linkName, linkUrl));
//
//                        adapter.notifyItemInserted(links.size() - 1);
//
//                        recyclerView.scrollToPosition(links.size() - 1);
//
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });

//        links.add(new LinkModel("www", "123"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LinkAdapter(this, links);
        recyclerView.setAdapter(adapter);

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
        linkUrl.getText().clear();
        linkName.requestFocus();
        inputAlertDialog.show();
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
                                Snackbar.make(recyclerView, getString(R.string.link_add_success), Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(recyclerView, getString(R.string.link_invalid), Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        inputAlertDialog = alertDialogBuilder.create();
    }
}
