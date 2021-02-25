package me.zhiyao.blood.data.api

import me.zhiyao.blood.data.api.request.UploadRequest
import me.zhiyao.blood.data.api.response.BaseResponse
import retrofit2.http.POST

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
interface ApiService {

    @POST("upload")
    suspend fun upload(uploadRequest: UploadRequest): BaseResponse

}
