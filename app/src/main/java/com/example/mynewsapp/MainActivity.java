package com.example.mynewsapp;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewsapp.Modal.NewsDataModal;

import java.util.ArrayList;

import cz.intik.overflowindicator.OverflowPagerIndicator;
import cz.intik.overflowindicator.SimpleSnapHelper;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsDataModal>> {
    RecyclerView rvNewsArticles;
    OverflowPagerIndicator overflow;
    ArrayList<NewsDataModal> newsDataList;
    NewsArticlesAdapter newArticleAdapter;
    TextView tvSomethingWrong;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        rvNewsArticles = findViewById(R.id.rvNewsArticles);
        overflow = findViewById(R.id.view_pager_indicator);
        tvSomethingWrong = findViewById(R.id.tvSomeThing);
        relativeLayout = findViewById(R.id.relativeLayout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        newsDataList = new ArrayList<>();

        if (isConnectedToInternet()) {
            progressDialog.show();
            getSupportLoaderManager().initLoader(1, null, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rvNewsArticles.setLayoutManager(layoutManager);
            newArticleAdapter = new NewsArticlesAdapter(this, newsDataList);
            rvNewsArticles.setAdapter(newArticleAdapter);
            addCardLikeFuctionality();
        } else {
            tvSomethingWrong.setVisibility(View.VISIBLE);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }

    private Boolean isConnectedToInternet() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void addCardLikeFuctionality() {
        overflow.attachToRecyclerView(rvNewsArticles);
        SimpleSnapHelper pageSnapHelper = new SimpleSnapHelper(overflow);
        rvNewsArticles.setOnFlingListener(null);
        pageSnapHelper.attachToRecyclerView(rvNewsArticles);
    }

    @NonNull
    @Override
    public Loader<ArrayList<NewsDataModal>> onCreateLoader(int id, @Nullable Bundle args) {

        NewsDataAsyncLoader newsDataAsyncLoader = new NewsDataAsyncLoader(this, newsDataList);
        newsDataAsyncLoader.forceLoad();
        return newsDataAsyncLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<NewsDataModal>> loader, ArrayList<NewsDataModal> data) {
        if (data != null) {
            newArticleAdapter.notifyDataSetChanged();
            rvNewsArticles.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        } else {
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
            rvNewsArticles.setVisibility(View.GONE);
            tvSomethingWrong.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<NewsDataModal>> loader) {
        rvNewsArticles.setAdapter(null);
    }
}
