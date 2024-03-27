package com.example.smartmouse

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.Timer
import kotlin.concurrent.schedule

class ConnectClient {
    private val timer = Timer()

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

    fun connect (host: String, port: Int, data: ByteArray, senderPort: Int = 0) {
        timer.schedule(0, 200) {
            Thread {
                send(host, port, data, senderPort)
            }.start()
        }
    }
}