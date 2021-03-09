package com.userdetail.demo.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.userdetail.demo.model.Datum;
import com.userdetail.demo.model.Meta;
import com.userdetail.demo.model.UserDetailBean;
import com.userdetail.demo.R;
import com.userdetail.demo.adapter.RecyclerViewAdapter;
import com.userdetail.demo.utils.Utils;
import com.userdetail.demo.viewmodel.UserDetailLoaderViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements LifecycleOwner{

    UserDetailActivity context;
    UserDetailLoaderViewModel viewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    public int pageNo=1;
    boolean isPagingCalled=false;
    static int totalPages=1;
    ProgressDialog pd;
    List<Datum> userDataList = new ArrayList<>();
    ImageView ivClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        recyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        viewModel = ViewModelProviders.of(context).get(UserDetailLoaderViewModel.class);
        loadMoreData(pageNo);
        viewModel.getUserMutableLiveData().observe(context, userDetailBeanObserver);

        /**
         * Load User Data while Scrolling
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (isPagingCalled) {
                        isPagingCalled = false;
                        pageNo++;
                        loadMoreData(pageNo);
                    }
                }
            }
        });
    }

    /**
     * Load More User Data
     */
    private void loadMoreData(int pageNumber){
        if (Utils.isConnectedWithInternet(context)) {
                viewModel.getData(pageNumber);
        }else {
            Toast.makeText(context,getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Observer
     */
    Observer<UserDetailBean> userDetailBeanObserver = new Observer<UserDetailBean>() {
        @Override
        public void onChanged(UserDetailBean userDetailBean) {
            hideProgressBar();
            try {
                updateAdapter(userDetailBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Update Recyclerview Adapter
     */
    private void updateAdapter(UserDetailBean userDetailBean){
        if(userDetailBean != null){
            Meta meta = userDetailBean.getMeta();
            if(meta != null) {
                totalPages = meta.getPagination().getPages();
            }
            if(pageNo < totalPages){
                isPagingCalled = true;
            }
            if(userDetailBean.getData() != null && userDetailBean.getData().size()>0) {
                userDataList.addAll(userDetailBean.getData());
            }
            if(userDataList != null && userDataList.size()>0){
                if(recyclerViewAdapter == null) {
                    recyclerViewAdapter = new RecyclerViewAdapter(context, userDataList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }else{
                    recyclerViewAdapter.updateRecyclerViewAdapter(userDataList);
                }
            }
        }else{
            Toast.makeText(context, getResources().getString(R.string.no_data_found), Toast.LENGTH_LONG).show();
        }
    }



    /**
     * Show Progress Bar
     */
    public void showProgressBar() {
        if (pd == null) {
            pd = new ProgressDialog(UserDetailActivity.this);
        }
        pd.setMessage(getResources().getString(R.string.loading));
        pd.show();
        pd.setCancelable(false);
    }

    /**
     * hide Progress Bar
     */
    public void hideProgressBar() {
        try {
            if (pd != null && pd.isShowing())
                pd.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
