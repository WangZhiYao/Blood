package me.zhiyao.blood.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author WangZhiYao
 * @date 2021/2/26
 */


object TimeUtils {

    const val YYYY_MM_DD = "yyyy.MM.dd"
    const val YYYY_MM_DD_E_HH_MM = "yyyy.MM.dd E HH:mm"

    fun timestamp2Str(pattern: String, timestamp: Long): String {
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        return sdf.format(Date(timestamp))
    }
}