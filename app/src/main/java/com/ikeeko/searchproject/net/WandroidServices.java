package com.ikeeko.searchproject.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ZSC on 2020-06-23.
 */
public interface WandroidServices {

    /**
     * 获取首页文章列表
     */
    @GET("/article/list/0/json")
    Call<ResponseBody> getHomeArticles();
}
