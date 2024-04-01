package com.example.smartmouse

import java.io.BufferedReader
import java.io.InputStreamReader
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
    private var tcpSocket: Socket? = null

    fun connect(ip: String, port: Int, senderPort: Int = 0): Boolean {
        isConnect = false

        this.ip = ip
        this.port = port
        this.senderPort = senderPort

        Thread {
            sendTcp("tcp connection")
            waitTcp()
        }.start()

        return isConnect
    }

    fun get(): String {
        return "${this.ip}:${this.port}"
    }

    fun sendTcp(message: String) {
        try {
            tcpSocket = Socket(ip, port)
            val writer: PrintWriter = PrintWriter(tcpSocket!!.getOutputStream(), true)
            writer.println(message)
            isConnect = true
        } finally {

        }
    }

    fun waitTcp(): String {
        val stream = tcpSocket!!.getInputStream()
        val buffer = BufferedReader(InputStreamReader(stream))
        val received = buffer.readLine()
        isConnect = true

        return received
    }

    fun sendUdp(message: String) {
        val data = message.toByteArray()
        var udpSocket: DatagramSocket? = null
        try {
            udpSocket = DatagramSocket(senderPort)
            val address = InetAddress.getByName(ip)
            val packet = DatagramPacket(data, data.size, address, port)
            udpSocket.send(packet)
        } finally {
            udpSocket?.close()
        }
    }
}