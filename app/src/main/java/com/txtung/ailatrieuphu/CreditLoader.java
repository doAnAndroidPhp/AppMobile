package com.txtung.ailatrieuphu;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CreditLoader extends AsyncTaskLoader<String> {
    public CreditLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData("goi-credit","GET");
    }
}
