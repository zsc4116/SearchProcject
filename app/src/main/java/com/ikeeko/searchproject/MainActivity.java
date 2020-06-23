package com.ikeeko.searchproject;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ikeeko.searchproject.adapter.HomeContentAdapter;
import com.ikeeko.searchproject.bean.HomeArticleBean;
import com.ikeeko.searchproject.net.RetrofitManager;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private HomeContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initOther();
    }

    private void initOther() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        adapter = new HomeContentAdapter();

        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(adapter);

    }

    private void initView() {
        rvContent = findViewById(R.id.rv_home_article);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Call<ResponseBody> response = RetrofitManager.getInstance().getServices().getHomeArticles();
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String contents = null;
                try {
                    contents = response.body().string();
                    List<HomeArticleBean> datas = processContents(contents);
                    adapter.setData(datas);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    private List<HomeArticleBean> processContents(String contents) {
        JSONObject obj = JSON.parseObject(contents);
        String data = obj.getString("data");
        JSONObject dataObj = JSON.parseObject(data);
        String datasObj = dataObj.getString("datas");
        return JSON.parseArray(datasObj, HomeArticleBean.class);
    }
}
