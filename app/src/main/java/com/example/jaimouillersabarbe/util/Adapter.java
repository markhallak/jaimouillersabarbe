package com.example.jaimouillersabarbe.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaimouillersabarbe.R;
import com.example.jaimouillersabarbe.models.Report;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Report> reportList;

    public Adapter(Context context, List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position >= reportList.size() || reportList.get(position) == null){
            return;
        }

        Report report = (Report) reportList.get(position);
        holder.reportTitleTxt.setText(report.getTitle());
        holder.reportAreaTxt.setText("Area of Government: " + report.getArea());
        holder.reportCityTxt.setText("City: " + report.getCity());
        holder.reportAmountTxt.setText("Amount: " + report.getAmount());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView reportTitleTxt, reportAreaTxt, reportCityTxt, reportAmountTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportTitleTxt = itemView.findViewById(R.id.reportTitleTxt);
            reportAreaTxt = itemView.findViewById(R.id.reportAreaTxt);
            reportCityTxt = itemView.findViewById(R.id.reportCityTxt);
            reportAmountTxt = itemView.findViewById(R.id.reportAmountTxt);
        }
    }
}
