package com.txtung.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class LichSuChoiGame extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<String> {
    private final ArrayList<LuotChoi> mListLuotChoi = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private int id_nguoi_choi;
    private LichSuAdapter mLichSuAdapter;
    private static final int PAGE_SIZE=15;
    private int currentPage=1;
    private boolean isLastPage=false;
    private int totalPage;
    private boolean isLoading =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_choi_game);
        mRecyclerView = findViewById(R.id.rcv_Lichsu);
        this.mLichSuAdapter=new LichSuAdapter(this,mListLuotChoi);
        this.mRecyclerView.setAdapter(mLichSuAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = getIntent();
        id_nguoi_choi = intent.getIntExtra("id",0);

        mLichSuAdapter = new LichSuAdapter(this, mListLuotChoi);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
        getSupportLoaderManager().restartLoader(0,null,this);

        loadData(null);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
                LinearLayoutManager layoutManager=(LinearLayoutManager)mRecyclerView.getLayoutManager();
                int visibleItemCount=layoutManager.getChildCount();
                int totalItemCount=layoutManager.getItemCount();
                int firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition();

                if(!isLoading && !isLastPage){
                    if((visibleItemCount + firstVisibleItemPosition)>=totalItemCount && firstVisibleItemPosition>=0 && totalItemCount>=PAGE_SIZE){
                        isLoading=true;
                        currentPage++;
                        mListLuotChoi.add(null);
                        mLichSuAdapter.notifyItemInserted(mListLuotChoi.size()-1);
                        Bundle data=new Bundle();
                        data.putInt("page",currentPage);
                        data.putInt("limit",PAGE_SIZE);
                        loadData(data);
                    }
                }
            }
        });
    }

    private AlertDialog taoThongBao(String s) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(s).setTitle("Loi");
        return builder.create();
    }
    private void loadData(@NonNull  Bundle data) {
        if(getSupportLoaderManager().getLoader(0) !=null){
            getSupportLoaderManager().restartLoader(0,data,this);

        }
        getSupportLoaderManager().initLoader(0,data,this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        int page=1;
        int limit=25;
        if(args!=null){
            page=args.getInt("page");
            limit=args.getInt("limit");
        }
        return new LichSuLoader(this, id_nguoi_choi,page,limit);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try{
            if(data==null){
                taoThongBao("Khong the ket noi server").show();
                return;
            }
            JSONObject jsonObject=new JSONObject((String) data);
            int total=jsonObject.getInt("total");
            totalPage=total/PAGE_SIZE;
            if(total % PAGE_SIZE !=0){
                totalPage++;
            }
            if(mListLuotChoi.size()>0){
                mListLuotChoi.remove(mListLuotChoi.size()-1);
                int scrollPosition=mListLuotChoi.size();
                mLichSuAdapter.notifyItemRangeRemoved(scrollPosition);
            }
            JSONArray itemsArray = jsonObject.getJSONArray("luot_choi");
            for(int i=0; i<itemsArray.length(); i++) {
                String ngay= itemsArray.getJSONObject(i).getString("ngay_gio");
                int diem  = itemsArray.getJSONObject(i).getInt("diem");
                int socau = itemsArray.getJSONObject(i).getInt("so_cau");
                this.mListLuotChoi.add(new LuotChoi(socau, diem, ngay));
            }
            mLichSuAdapter.notifyDataSetChanged();
            isLoading=false;
            isLastPage=(currentPage==totalPage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
