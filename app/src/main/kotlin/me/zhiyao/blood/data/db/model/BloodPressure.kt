package me.zhiyao.blood.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
@Entity(tableName = "t_blood_pressure")
data class BloodPressure(

    /**
     * 收缩压（mmHg）
     */
    val sys: Int,

    /**
     * 舒展压（mmHg）
     */
    val dia: Int,

    /**
     * 脉搏（次/分钟）
     */
    val pul: Int,

    /**
     * 测量时间
     */
    @ColumnInfo(name = "measure_time")
    val measureTime: Long,

    /**
     * 创建时间
     */
    @ColumnInfo(name = "create_time")
    val createTime: Long
) {

    /**
     * 本地主键
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    /**
     * 上传状态
     */
    var status: Int = 0
}

