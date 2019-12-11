package com.txtung.ailatrieuphu;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CauHoiLoader extends AsyncTaskLoader<String> {
    int id;
    public CauHoiLoader(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("cau-hoi?linh_vuc=4","GET");
    }
}
