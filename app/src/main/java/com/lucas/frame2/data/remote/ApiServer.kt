package com.lucas.frame2.data.remote

import com.lucas.frame.data.net.IBean
import com.lucas.frame2.data.bean.ProjectListBean
import com.lucas.frame2.data.bean.RegisterBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @package    ApiServer.kt
 * @author     luan
 * @date       2019-12-25
 * @des        接口
 */
interface ApiServer {
    //登陆
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<IBean>

    //注册
    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): Observable<RegisterBean>

    //项目列表
    @GET("project/list/{cate}/json")
    fun projectList(@Path("cate") cate:Int,@Query("cid") page:Int): Observable<ProjectListBean>
}