package cc.loac.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 将当前文件夹压缩
 * @param zipFileName 压缩包名称
 */
fun File.createZip(zipFileName: String): File? {
    if (!this.exists()) return null
    if (!this.isDirectory) return null
    return try {
        ZipOutputStream(FileOutputStream(zipFileName).buffered()).use { zipOut ->
            this.walkTopDown().forEach { file ->
                if (!file.isDirectory) {
                    val entryName = file.relativeTo(this).path.replace(File.separatorChar, '/')
                    zipOut.putNextEntry(ZipEntry(entryName))
                    FileInputStream(file).use { input ->
                        input.copyTo(zipOut)
                    }
                    zipOut.closeEntry()
                }
            }
        }
        File(zipFileName)
    } catch (e: Exception) {
        null
    }
}

