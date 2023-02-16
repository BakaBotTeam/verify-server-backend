package ltd.guimc.verify.server.utils

import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object hcaptchaUtils {
    val siteKey = "f5612471-2623-4b8b-b30d-dce43b9f0f98"
    val secretKey = "0x9EE2fcac438B199cCbC327fe8f5FB1c92AaB4eA8"

    fun verifyToken(string: String): Boolean {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://hcaptcha.com/siteverify"))
            .POST(formData(mapOf("response" to string, "secret" to secretKey, "sitekey" to siteKey)))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())
        return response.body().indexOf("\"success\":true") != -1
    }

    private fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

    private fun formData(data: Map<String, String>): HttpRequest.BodyPublisher? {

        val res = data.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}
            .joinToString("&")

        return HttpRequest.BodyPublishers.ofString(res)
    }
}