package me.zhiyao.blood.util

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author WangZhiYao
 * @date 2021/2/26
 */
object TimeUtils {

    fun timestamp2Str(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd E HH:mm", Locale.CHINA)
        return sdf.format(Date(timestamp))
    }
}