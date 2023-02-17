package ltd.guimc.verify.server.utils

import java.io.IOException


object HCaptchaUtils {
    private const val siteKey = "f5612471-2623-4b8b-b30d-dce43b9f0f98"
    private const val secretKey = "0x9EE2fcac438B199cCbC327fe8f5FB1c92AaB4eA8"

    fun verifyToken(string: String): Boolean {
        // form parameters
        // form parameters
        val formBody: RequestBody = Builder()
            .add("id", 10)
            .build()

        val request: Request = Builder()
            .url("http://www.example.com/page.php")
            .post(formBody)
            .build()


        val httpClient = OkHttpClient()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful()) throw IOException("Unexpected code $response")

            // Get response body
            System.out.println(response.body().string())
        }
    }
}