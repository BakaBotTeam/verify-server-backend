package ltd.guimc.verify.server.utils

import HashUtils.sha256

object AuthUtils {
    private val secret = System.getProperty("ltd.guimc.verify.secret")

    fun getSecret(appid: String): String {
        return "$appid:$secret".sha256()
    }
}