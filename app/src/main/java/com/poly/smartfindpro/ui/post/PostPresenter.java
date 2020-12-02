package com.poly.smartfindpro.ui.post;

import android.content.Context;
import android.util.Log;

public class PostPresenter implements PostContract.Presenter {

    private Context context;

    private PostContract.ViewModel mViewModel;

    public PostPresenter(Context context, PostContract.ViewModel mViewModel) {
        this.context = context;
        this.mViewModel = mViewModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    public void nextFragment(String isStatus, String jsonData, String jsonPhoto){



    }


}
