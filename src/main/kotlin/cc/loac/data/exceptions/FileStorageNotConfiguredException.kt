package cc.loac.data.exceptions

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件组策略还未配置异常
 * @param fileStorageModeEnum 文件存储策略
 */
class FileStorageNotConfiguredException(fileStorageModeEnum: FileStorageModeEnum) :
    MyException("文件存储策略 [$fileStorageModeEnum] 还未配置")