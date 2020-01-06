package com.txtung.ailatrieuphu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LichSuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_VIEW_ITEM=0;
    private final static int TYPE_VIEW_LOADING=1;
    private final ArrayList<LuotChoi> mListLuotChoi;
    private final LayoutInflater mInflater;

    public LichSuAdapter(Context context, ArrayList<LuotChoi> mListNguoiChoi) {
        this.mListLuotChoi = mListNguoiChoi;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getItemViewType(int position){
        return mListLuotChoi.get(position)==null ? TYPE_VIEW_LOADING : TYPE_VIEW_ITEM;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TYPE_VIEW_ITEM){
            View itemView=this.mInflater.inflate(R.layout.item_lichsu,parent,false);
            return new LuotChoiViewHolder(itemView, this);

        }else if(viewType==TYPE_VIEW_LOADING){
            View itemView = this.mInflater.inflate(R.layout.item_lichsu,parent,false);
            return new LoadingViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LuotChoiViewHolder){
            hienThiThongTin((LuotChoiViewHolder)holder,position);

        }else if(holder instanceof LoadingViewHolder){
            hienThiProgressBar((LoadingViewHolder) holder);
        }

    }

    private void hienThiProgressBar(LoadingViewHolder holder) {
    }

    @Override
    public int getItemCount(){
        return this.mListLuotChoi==null ? 0 : this.mListLuotChoi.size();
    }

    public void notifyItemRangeRemoved(int scrollPosition) {
    }

    class LuotChoiViewHolder extends RecyclerView.ViewHolder{

        private final TextView txt_Ngay, txt_Diem, txt_Socau;
        private final LichSuAdapter mAdapter;

        public LuotChoiViewHolder(@NonNull View itemView, LichSuAdapter mAdapter) {
            super(itemView);
            this.txt_Ngay = itemView.findViewById(R.id.txt_Ngay);
            this.txt_Diem = itemView.findViewById(R.id.txt_Diem);
            this.txt_Socau= itemView.findViewById(R.id.txt_Socau);
            this.mAdapter = mAdapter;
        }
    }
    class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    void hienThiThongTin(LuotChoiViewHolder holder, int position){
        LuotChoi nguoiChoi=this.mListLuotChoi.get(position);
        holder.txt_Ngay.setText(nguoiChoi.getNgay_gio());
        holder.txt_Diem.setText(Integer.toString(nguoiChoi.getSo_diem()));
        holder.txt_Socau.setText(Integer.toString(nguoiChoi.getSo_cau()));

    }
}
