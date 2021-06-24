/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
@file:Suppress("MemberVisibilityCanBePrivate")

package dnl.uxlc.xml.json.conversion

import org.apache.commons.io.FileUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class Downloader {
    val client: HttpClient = HttpClientBuilder.create().build()
    val downloadDir = File("xml-output")

    fun downloadAll() {
        val lines = FileUtils.readLines(File("src/main/resources/bible-boox.txt"))
        lines.forEach {
            println(it)
            val ix = it.substring(0, 2)
            val fname = it.substring(3)
            processFile(ix, fname)
        }
    }

    fun processFile(index:String, bookName:String) {
        val targetFile = File(downloadDir, "$index.$bookName.xml")
        if(targetFile.exists())
            return
        val request = HttpGet("https://tanach.us/Books/${bookName}.xml")
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");
        try {
            val response: HttpResponse = client.execute(request)
            println("Response Code : " + response.statusLine.statusCode)
            val inputStream: InputStream = response.entity.content
            val fos = FileOutputStream(targetFile)
            var inByte: Int
            while (inputStream.read().also { inByte = it } != -1) fos.write(inByte)
            inputStream.close()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main() {
    val downloader = Downloader()
    downloader.downloadAll()
}


