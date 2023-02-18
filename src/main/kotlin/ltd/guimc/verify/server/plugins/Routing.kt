package ltd.guimc.verify.server.plugins

import HashUtils.sha256
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ltd.guimc.verify.server.utils.AuthUtils
import ltd.guimc.verify.server.utils.HCaptchaUtils
import ltd.guimc.verify.server.utils.JsonUtils.respondFailedAuth
import ltd.guimc.verify.server.utils.SessionUtils
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
                    try {
                        checkNotNull(parm["appid"])
                        checkNotNull(parm["qqid"])
                        checkNotNull(parm["group"])
                        checkNotNull(parm["time"])
                        checkNotNull(parm["sign"])
                    } catch (_: IllegalStateException) {
                        call.respondFailedAuth()
                    }

                    val appid = parm["appid"]!!
                    val qqid = parm["qqid"]!!
                    val group = parm["group"]!!
                    val clientTime = parm["time"]!!.toLong()
                    val sign = parm["sign"]!!

                    if (System.currentTimeMillis() - clientTime >= 5000 && "$appid:${AuthUtils.getSecret(appid)}:$group:$qqid:$clientTime".sha256() != sign) {
                        call.respondFailedAuth()
                    }

                    call.respondText("{\"success\":true, \"session\":\"${SessionUtils.newSession()}\"}")
                }

                "do" -> {
                    //
                    if (this.context.request.queryParameters["token"]?.let { it1 ->
                            HCaptchaUtils.verifyToken(
                                it1
                            )
                        } == true && SessionUtils.findSession(this.context.request.queryParameters["session"]!!)) {
                        SessionUtils.finishSession(this.context.request.queryParameters["session"]!!)
                        call.respondText("Success Verify")
                    } else {
                        call.respondText("Failed Verify or Cannot find session")
                    }

                }

                "status" -> {
                    if (SessionUtils.findSucceedSession(this.context.request.queryParameters["session"]!!)) {
                        call.respondText("{\"success\":true, \"timeout\":false}")
                    } else if (SessionUtils.findTimedoutSession(this.context.request.queryParameters["session"]!!)) {
                        call.respondText("{\"success\":false, \"timeout\":true}")
                    } else {
                        call.respondText("{\"success\":false, \"timeout\":false}")
                    }
                }

                else -> {
                    if (SessionUtils.findSession(this.context.request.queryParameters["session"]!!)) {
                        call.respondFile(File("Frontend/verify.html"))
                    } else {
                        call.respondText("Cannot find session")
                    }
                }
            }
        }
    }
}
