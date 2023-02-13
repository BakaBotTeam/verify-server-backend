package ltd.guimc.verify.server.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

object JsonUtils {
    suspend fun ApplicationCall.respondFailedAuth() {
        this.respondText("{\"success\":false, \"reason\":\"Unauthorized\"}")
        this.response.status(HttpStatusCode.Unauthorized)
    }
}