package com.example.smartmouse

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.Timer
import kotlin.concurrent.schedule

class ServerManager {
    private var ip: String = ""
    private var port: Int = 0
    private var senderPort: Int = 0

    fun connect (ip: String, port: Int, senderPort: Int = 0): Boolean {
        var isConnect: Boolean = false

        this.ip = ip
        this.port = port
        this.senderPort = senderPort

        Thread {
            isConnect = send("".toByteArray())
        }.start()

        return isConnect
    }

    fun send(data: ByteArray): Boolean {
        var ret = false
        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(senderPort)
            val address = InetAddress.getByName(ip)
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
}