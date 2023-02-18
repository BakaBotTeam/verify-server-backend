package ltd.guimc.verify.server.utils

import ltd.guimc.verify.server.utils.HashUtils.sha256

object AuthUtils {
    private val secret = System.getProperty("ltd.guimc.verify.secret")

    fun getSecret(appid: String): String {
        return "$appid:$secret".sha256()
    }
}