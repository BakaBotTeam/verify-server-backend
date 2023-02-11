package ltd.guimc.verify.server.plugins

import HashUtils.sha256
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

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

                    // if (System.currentTimeMillis() - clientTime >= 5000) {
                    //     call.respondText("{\"success\":false, \"reason\":\"TimeProblem\"}")
                    //     call.response.status(HttpStatusCode.Unauthorized)
                    // }

                    call.respondText("$appid:${appid.sha256()}:$group:$qqid:$clientTime".sha256())
                }

                "do" -> {
                    // call.respondText("ur trying verify")
                }

                "status" -> {
                    // call.respondText("ur trying get status")
                }

                else -> {
                    call.respondText("???")
                }
            }
        }
    }
}
