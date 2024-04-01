package com.example.smartmouse

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ServerManager {
    private var ip: String = ""
    private var port: Int = 0
    private var senderPort: Int = 0

    var isConnect: Boolean = false

    fun connect(ip: String, port: Int, senderPort: Int = 0): Boolean {
        this.ip = ip
        this.port = port
        this.senderPort = senderPort

        Thread {
            send("".toByteArray())
        }.start()

        return isConnect
    }

    fun get(): String {
        return "${this.ip}:${this.port}"
    }

    fun send(data: ByteArray) {
        var socket: DatagramSocket? = null
        try {
            socket = DatagramSocket(senderPort)
            val address = InetAddress.getByName(ip)
            val packet = DatagramPacket(data, data.size, address, port)
            socket.send(packet)
            isConnect = true
        } catch (e: Exception) {
            isConnect = false
        } finally {
            socket?.close()
        }
    }
}