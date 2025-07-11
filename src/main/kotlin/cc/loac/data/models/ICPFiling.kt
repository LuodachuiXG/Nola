package cc.loac.data.models

/**
 * 工业信息化部备案信息数据类
 * @param icp ICP 备案号
 * @param public 公网安备号
 */
data class ICPFiling(
    val icp: String?,
    val public: String?
)
