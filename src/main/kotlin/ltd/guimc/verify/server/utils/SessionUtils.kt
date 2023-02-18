package ltd.guimc.verify.server.utils

import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

object SessionUtils {
    private val runningSessions = mutableMapOf<String, Long>()
    private val timedoutSessions = mutableListOf<String>()
    private val successSessions = mutableListOf<String>()

    private const val STRING_LENGTH = 16

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
                timedoutSessions.add(it.key)
            }
        }
    }

    fun finishSession(session: String) {
        runningSessions.remove(session)
        successSessions.add(session)
    }

    fun newSession() : String {
        val session = randomStringByKotlinRandom()
        runningSessions[session] = System.currentTimeMillis()
        return session
    }

    fun findSession(session: String): Boolean = runningSessions.keys.indexOf(session) != -1
    fun findSucceedSession(session: String): Boolean = successSessions.indexOf(session) != -1
    fun findTimedoutSession(session: String): Boolean = timedoutSessions.indexOf(session) != -1

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun randomStringByKotlinRandom() = (1..STRING_LENGTH)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")
}