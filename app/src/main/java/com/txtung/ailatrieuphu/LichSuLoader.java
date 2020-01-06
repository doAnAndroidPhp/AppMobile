package com.txtung.ailatrieuphu;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class LichSuLoader extends AsyncTaskLoader<String> {
    int nguoichoi_id;
    int page, limit;
    public LichSuLoader(@NonNull Context context , int id, int page, int limit) {
        super(context);
        nguoichoi_id = id;
        this.page = page;
        this.limit = limit;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("lay-luot-choi?nguoi_choi_id="+nguoichoi_id, "GET");
    }
}
