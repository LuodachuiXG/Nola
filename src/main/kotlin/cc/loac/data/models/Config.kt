package cc.loac.data.models

import cc.loac.data.models.enums.ConfigKey
/**
 * 配置数据类
 * @param configId 配置表 ID
 * @param key 配置键
 * @param value 配置数据
 */
data class Config(
    val configId: Int = -1,
    val key: ConfigKey,
    val value: String
)