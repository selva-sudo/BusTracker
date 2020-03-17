package com.android.selvaraj.displaybus.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.selvaraj.displaybus.Interface.RecyclerViewItemClickListener;
import com.android.selvaraj.displaybus.R;
import com.android.selvaraj.displaybus.activity.BusListActivity;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    private RecyclerViewItemClickListener listener;
    private String[] busList;
    private Context context;

    public BusAdapter(Context context, RecyclerViewItemClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_item, viewGroup, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvBusItem.setText(busList[i]);
        viewHolder.tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.tvButton);
                popupMenu.inflate(R.menu.rv_options);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_view) {
                            ((BusListActivity) context).showDetails(i);
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return busList.length;
    }

    public void updateData(String[] busList, BusListActivity busListActivity) {
        this.busList = busList;
        this.context = busListActivity;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBusItem;
        private TextView tvButton;

        public ViewHolder(@NonNull final View itemView, final RecyclerViewItemClickListener listener) {
            super(itemView);
            tvBusItem = itemView.findViewById(R.id.tv_bus_item);
            tvButton = itemView.findViewById(R.id.textViewOptions);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

    }
}
