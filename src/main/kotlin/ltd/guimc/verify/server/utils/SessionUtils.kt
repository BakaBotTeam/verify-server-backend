package ltd.guimc.verify.server.utils

import java.util.Timer
import java.util.TimerTask

object SessionUtils {
    val runningSessions = mutableMapOf<String, Long>()
    val timedoutSessions = mutableListOf<String>()

    fun register() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                checkTimedout()
            }
        }, 0, 500)
    }

    fun checkTimedout() {
        runningSessions.forEach {
            if (System.currentTimeMillis() - it.value >= 60*1000) {
                runningSessions.remove(it.key)
            }
        }
    }
}