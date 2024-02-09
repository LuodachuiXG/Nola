package cc.loac.data.models

import cc.loac.data.models.enums.ConfigKey
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

/**
 * 配置数据类
 */
data class Config(
    /** 配置表 ID **/
    val configId: Int = -1,
    /** 配置键 **/
    val key: ConfigKey,
    /** 配置数据 **/
    val value: String
)