package com.example.smartmouse

import java.util.Timer
import kotlin.concurrent.schedule
class OutputController(private val serverManager: ServerManager) {
    private val timer = Timer()

    private var moveCoo: IntArray = intArrayOf(0, 0)

    fun setMoveCoo(x: Int, y: Int) {
        moveCoo[0] = x
        moveCoo[1] = y
    }

    fun mouseEnable () {
        timer.schedule(0, 100) {
            Thread {
                serverManager.send("move.${moveCoo[0]}.${moveCoo[1]}".toByteArray())
            }.start()
        }
    }
}