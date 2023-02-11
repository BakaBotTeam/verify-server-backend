package ltd.guimc.verify.server

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import ltd.guimc.verify.server.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/verify?action=new&appid=XL_VER_APP_UJj5C2c56SC412c121C&group=1116545&qqid=545610&time=0&sign=26b9eecefd8bd327fd095501206a78f441d03dcd4f31e967c4df14c648933a13").apply {
            println(bodyAsText())
        }
    }
}
