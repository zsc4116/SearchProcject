package com.ikeeko.searchproject;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
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
                    List<HomeArticleBean> datas = getContentList(contents, HomeArticleBean.class,
                            "data", "datas");
                    if (datas != null && datas.size() > 0) {
                        adapter.setData(datas);
                    } else {
                        Log.e("warning", "home article is null or size == 0.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("error", "get response body fail.");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", "home article parse fail.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", "request home article fail:" + t.getLocalizedMessage());
            }
        });


    }

    private List getContentList(String contents, Class calzz, String... strings) throws JSONException {
        JSONObject obj;
        for (int i = 0; i < strings.length - 1; i++) {
            obj = JSON.parseObject(contents);
            contents = obj.getString(strings[i]);
        }
        JSONObject dataObj = JSON.parseObject(contents);
        String datasObj = dataObj.getString(strings[strings.length - 1]);
        return JSON.parseArray(datasObj, calzz);
    }
}
