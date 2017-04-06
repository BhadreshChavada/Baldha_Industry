package prerak.com.baldha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import prerak.com.baldha.OrderActivity;
import prerak.com.baldha.R;
import prerak.com.baldha.model.login.getshop.Shop;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> implements Filterable {
    private ArrayList<Shop> mArrayList;
    private ArrayList<Shop> mFilteredList;
    Context context;

    public ShopAdapter(ArrayList<Shop> arrayList, Context context) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.context = context;
    }

    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_shop, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_name.setText(mFilteredList.get(i).getShopName());
        viewHolder.tv_version.setText(mFilteredList.get(i).getOwnerName());
        viewHolder.tv_api_level.setText(mFilteredList.get(i).getContactNO());
        viewHolder.ll_adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("ShopId", mArrayList.get(i).getShopID());
                intent.putExtra("ShopName", mArrayList.get(i).getShopName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Shop> filteredList = new ArrayList<>();

                    for (Shop androidVersion : mArrayList) {

                        if (androidVersion.getShopName().toLowerCase().contains(charString) || androidVersion.getOwnerName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Shop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_version, tv_api_level;
        LinearLayout ll_adapter;

        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_version = (TextView) view.findViewById(R.id.tv_version);
            tv_api_level = (TextView) view.findViewById(R.id.tv_api_level);
            ll_adapter = (LinearLayout) view.findViewById(R.id.ll_adapter);

        }
    }

}
