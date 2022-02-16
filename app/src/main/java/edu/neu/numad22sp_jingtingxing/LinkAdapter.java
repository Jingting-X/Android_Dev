package edu.neu.numad22sp_jingtingxing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    Context context;
    ArrayList<LinkModel> links;
    LinkClickListener linkClickListener;

    public LinkAdapter(Context context, ArrayList<LinkModel> links) {
        this.context = context;
        this.links = links;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item_view, parent, false);
        return new ViewHolder(view, linkClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinkModel link = (LinkModel) links.get(position);
        holder.txtName.setText(link.name);
        holder.txtUrl.setText(link.url);
    }


    @Override
    public int getItemCount() {
        return links.size();
    }

    public void setOnLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtUrl;

        public ViewHolder(View itemView, final LinkAdapter.LinkClickListener linkClickListener) {
            super(itemView);

            txtName = itemView.findViewById(R.id.link_name);
            txtUrl = itemView.findViewById(R.id.link_url);

            itemView.setOnClickListener(view -> {
                int layoutPosition = getLayoutPosition();
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    linkClickListener.onLinkClick(layoutPosition);
                }
            });
        }
    }

    public interface LinkClickListener {
        void onLinkClick(int position);
    }
}
