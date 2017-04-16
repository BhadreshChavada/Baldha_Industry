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

import prerak.com.baldha.OrderActivity;
import prerak.com.baldha.R;
import prerak.com.baldha.model.login.DailyReport.report;
import prerak.com.baldha.model.login.getshop.Shop;

/**
 * Created by Bhadresh Chavada on 06-04-2017.
 */

public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.ViewHolder> {
    private ArrayList<report> mArrayList;
    Context context;

    public DailyReportAdapter(ArrayList<report> arrayList, Context context) {
        mArrayList = arrayList;
        this.context = context;
    }

    @Override
    public DailyReportAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_daily_report, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyReportAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tv_startdate.setText(mArrayList.get(i).getSdate());
        viewHolder.tv_enddate.setText(mArrayList.get(i).getEdate());
        viewHolder.tv_no_order.setText(mArrayList.get(i).getCount());

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_startdate, tv_enddate, tv_no_order;

        public ViewHolder(View view) {
            super(view);

            tv_startdate = (TextView) view.findViewById(R.id.txt_startdate);
            tv_enddate = (TextView) view.findViewById(R.id.txt_enddate);
            tv_no_order = (TextView) view.findViewById(R.id.txt_order_count);

        }
    }

}
