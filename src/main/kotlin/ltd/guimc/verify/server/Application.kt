package ltd.guimc.verify.server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ltd.guimc.verify.server.plugins.*

fun main() {
    embeddedServer(Netty, port = 5566, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()
}
