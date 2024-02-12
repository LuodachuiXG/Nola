package cc.loac.data.sql.tables

import cc.loac.data.models.enums.ConfigKey
import org.jetbrains.exposed.sql.Table


/**
 * 配置表
 */
object Configs : Table("config") {
    /** 配置表 ID **/
    val configId = integer("configId").autoIncrement()
    /** 配置键 **/
    val key = enumerationByName<ConfigKey>("key", 50).uniqueIndex()
    /** 配置数据 **/
    val value = text("value");

    override val primaryKey = PrimaryKey(configId)
}