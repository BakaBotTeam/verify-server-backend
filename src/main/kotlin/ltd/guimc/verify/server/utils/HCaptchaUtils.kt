package ltd.guimc.verify.server.utils

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL


object HCaptchaUtils {
    private const val siteKey = "f5612471-2623-4b8b-b30d-dce43b9f0f98"
    private const val secretKey = "0x9EE2fcac438B199cCbC327fe8f5FB1c92AaB4eA8"

    fun verifyToken(string: String): Boolean {
        val url = URL("https://hcaptcha.com/siteverify")
        val postData = "response=$string&secret=$secretKey&sitekey=$siteKey"
        var response = ""

        val conn = url.openConnection()
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length", postData.length.toString())

        DataOutputStream(conn.getOutputStream()).use { it.writeBytes(postData) }
        BufferedReader(InputStreamReader(conn.getInputStream())).use { bf ->
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                response += line
            }
        }

        println(response)
        return response.indexOf("\"success\":true") != -1
    }
}