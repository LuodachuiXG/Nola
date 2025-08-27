package cc.loac.data.models

/**
 * 工业信息化部备案信息数据类
 * @param icp ICP 备案号
 * @param police 公网安备号
 * @param public 公网安备号（此字段已废弃，保留为兼容旧版，防止报错）
 */
data class ICPFiling(
    val icp: String?,
    val police: String?,
    // 此字段已废弃，保留为兼容旧版，防止报错
    val public: String?
)
