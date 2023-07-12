package cn.tongdun.android.demo.paas.net

import cn.tongdun.android.demo.paas.bean.FraudApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * @Author yang.bai.
 * Date: 2023/2/6
 */
interface ApiService {
//    @FormUrlEncoded
//    @POST("{url}/restricted/androidQuery.json")
//    suspend fun queryBlackboxInfo(
//        @Path("url") url: String,
//        @FieldMap data: Map<String, String>
//    ): Deferred<FraudApiResponse>

    @FormUrlEncoded
    @POST
    fun queryBlackboxInfo(
        @Url url: String,
        @FieldMap data: Map<String, String>
    ): Deferred<FraudApiResponse>
}