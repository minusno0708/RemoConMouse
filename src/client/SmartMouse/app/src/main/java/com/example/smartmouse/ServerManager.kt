package com.example.smartmouse

import java.io.PrintWriter
import java.net.Socket
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
            sendTcp("tcp connection")
        }.start()

        return isConnect
    }

    fun get(): String {
        return "${this.ip}:${this.port}"
    }

    fun sendTcp(message: String) {
        var socket: Socket? = null
        try {
            socket = Socket(ip, port)
            val writer: PrintWriter = PrintWriter(socket.getOutputStream(), true)
            writer.println(message)
            isConnect = true
        } catch (e: Exception) {
            isConnect = false
        } finally {
            socket?.close()
        }
    }

    fun sendUdp(message: String) {
        val data = message.toByteArray()
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