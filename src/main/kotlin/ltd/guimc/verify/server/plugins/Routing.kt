package ltd.guimc.verify.server.plugins

import HashUtils.sha256
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import ltd.guimc.verify.server.utils.JsonUtils.respondFailedAuth
import ltd.guimc.verify.server.utils.hcaptchaUtils
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("?")
        }

        get("/verify") {
            val parm = this.context.request.queryParameters
            when (parm["action"]) {
                "new" -> {
                    checkNotNull(parm["appid"])
                    checkNotNull(parm["qqid"])
                    checkNotNull(parm["group"])
                    checkNotNull(parm["time"])
                    checkNotNull(parm["sign"])

                    val appid = parm["appid"]!!
                    val qqid = parm["qqid"]!!
                    val group = parm["group"]!!
                    val clientTime = parm["time"]!!.toLong()
                    val sign = parm["sign"]!!

                    val responseContext = JsonObject

                    if (System.currentTimeMillis() - clientTime >= 5000 && "$appid:${appid.sha256()}:$group:$qqid:$clientTime".sha256() != sign) {
                        call.respondFailedAuth()
                    }
                }

                "do" -> {
                    call.respondText(if (this.context.request.queryParameters["token"]?.let { it1 ->
                            hcaptchaUtils.verifyToken(
                                it1
                            )
                        } == true) "Success Verify" else "Failed Verify")
                }

                "status" -> {
                    // call.respondText("ur trying get status")
                }

                else -> {
                    call.respondFile(File("Frontend/verify.html"))
                }
            }
        }
    }
}
