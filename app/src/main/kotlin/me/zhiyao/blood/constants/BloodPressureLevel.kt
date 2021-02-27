package me.zhiyao.blood.constants

/**
 *
 * @author WangZhiYao
 * @date 2021/2/26
 */
enum class BloodPressureLevel(
    /**
     * 最低收缩压
     */
    val minSys: Int,

    /**
     * 最高收缩压
     */
    val maxSys: Int,

    /**
     * 最低舒展压
     */
    val minDia: Int,

    /**
     * 最高舒展压
     */
    val maxDia: Int
) {

    /**
     * 低血压
     */
    LOW(0, 89, 0, 59),

    /**
     * 正常低值
     */
    NORMAL_LOW(90, 99, 60, 64),

    /**
     * 正常血压
     */
    NORMAL(100, 119, 65, 79),

    /**
     * 正常高值
     */
    NORMAL_HIGH(120, 139, 80, 89),

    /**
     * 一级高血压
     */
    HIGH_LEVEL_1(140, 159, 90, 99),

    /**
     * 二级高血压
     */
    HIGH_LEVEL_2(160, 179, 100, 109),

    /**
     * 三级高血压
     */
    HIGH_LEVEL_3(180, Int.MAX_VALUE, 110, Int.MAX_VALUE)

}