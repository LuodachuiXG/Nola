package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.ConfigDao
import cc.loac.data.sql.tables.Configs
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/**
 * 配置表操作接口实现类
 */
class ConfigDaoImpl : ConfigDao {
    /**
     * 将数据库检索结果转为 [Config] 实体类
     */
    private fun resultRowToConfig(row: ResultRow) = Config(
        configId = row[Configs.configId],
        key = row[Configs.key],
        value = row[Configs.value]
    )

    /**
     * 增加配置
     * @param config 配置数据类
     */
    override suspend fun addConfig(config: Config): Config? = dbQuery {
        val insertStatement = Configs.insert {
            it[key] = config.key
            it[value] = config.value
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToConfig)
    }

    /**
     * 删除配置
     * @param key 配置表键
     */
    override suspend fun deleteConfig(key: ConfigKey): Boolean = dbQuery {
        Configs.deleteWhere { Configs.key eq key } > 0
    }


    /**
     * 更新配置
     * @param config 配置数据类
     */
    override suspend fun updateConfig(config: Config) = dbQuery {
        Configs.update({
            Configs.configId eq config.configId
        }) {
            it[value] = config.value
        } > 0
    }

    /**
     * 获取配置
     * @param key 配置键
     */
    override suspend fun config(key: ConfigKey): String? = dbQuery {
        val result = Configs.selectAll().where(Configs.key eq key).firstOrNull() ?: return@dbQuery null
        resultRowToConfig(result).value
    }
}