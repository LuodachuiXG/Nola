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
    val zipFile = FileOutputStream(zipFileName)
    val zipOut = ZipOutputStream(zipFile)

    this.walkTopDown().forEach { file ->
        if (!file.isDirectory) {
            val entryName = file.relativeTo(this).path.replace(File.separatorChar, '/')
            val zipEntry = ZipEntry(entryName)
            zipOut.putNextEntry(zipEntry)

            val fileInputStream = FileInputStream(file)
            fileInputStream.copyTo(zipOut)
            fileInputStream.close()
            zipOut.closeEntry()
        }
    }

    zipOut.close()
    zipFile.close()
    return File(zipFileName)
}