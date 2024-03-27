package com.example.smartmouse

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.Timer
import kotlin.concurrent.schedule

class ConnectClient {
    private val timer = Timer()

    private var moveCoo: IntArray = intArrayOf(0, 0)

    fun setMoveCoo(x: Int, y: Int) {
        moveCoo[0] = x
        moveCoo[1] = y
    }

    private fun send(host: String, port: Int, data: ByteArray, senderPort: Int): Boolean {
        var ret = false
        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(senderPort)
            val address = InetAddress.getByName(host)
            val packet = DatagramPacket(data, data.size, address, port)
            socket.send(packet)
            ret = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            socket?.close()
        }

        return ret
    }

    fun connect (host: String, port: Int, senderPort: Int = 0) {
        timer.schedule(0, 100) {
            Thread {
                send(host, port, "move.${moveCoo[0]}.${moveCoo[1]}".toByteArray(), senderPort)
            }.start()
        }
    }
}