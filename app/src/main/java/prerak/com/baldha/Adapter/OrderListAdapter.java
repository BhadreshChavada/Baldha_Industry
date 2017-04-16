package prerak.com.baldha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prerak.com.baldha.OrderActivity;
import prerak.com.baldha.OrderListActivity;
import prerak.com.baldha.R;
import prerak.com.baldha.model.login.OrderModel.Order;
import prerak.com.baldha.model.login.OrderModel.Product;
import prerak.com.baldha.model.login.getshop.Shop;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class OrderListAdapter extends BaseExpandableListAdapter {

    List<Order> mArrayList;
    HashMap<String, List<Product>> productmap;
    Context context;

    public OrderListAdapter(List<Order> mArrayList, HashMap<String, List<Product>> productmap, Context context) {

        this.mArrayList = mArrayList;
        this.productmap = productmap;
        this.context = context;

    }


    @Override
    public int getGroupCount() {
        return productmap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return productmap.get(mArrayList.get(groupPosition).getProduct()).size();
        return productmap.get(mArrayList.get(groupPosition).getOrderID()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_orderlist_parent, null);
        }


//        convertView = View.inflate(context, R.layout.adapter_shop, parent);
        TextView Shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
        TextView Date = (TextView) convertView.findViewById(R.id.tv_orderdate);

        Shopname.setText(mArrayList.get(groupPosition).getShopName());
        Date.setText(mArrayList.get(groupPosition).getOrderDate());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_order_childview, null);
        }


//        convertView = View.inflate(context, R.layout.adapter_shop, parent);
        TextView txt_productname = (TextView) convertView.findViewById(R.id.txt_productname);
        TextView quantity = (TextView) convertView.findViewById(R.id.txt_quantity);

        txt_productname.setText(productmap.get(mArrayList.get(groupPosition).getOrderID()).get(childPosition).getProductName());
        quantity.setText(productmap.get(mArrayList.get(groupPosition).getOrderID()).get(childPosition).getProductQnt());
//        Date.setText(mArrayList.get(groupPosition).getOrderDate());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
