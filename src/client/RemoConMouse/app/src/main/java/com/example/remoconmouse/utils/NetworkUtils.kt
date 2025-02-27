package com.example.remoconmouse.utils

object NetworkUtils {
    fun isIP(ip: String): Boolean {
        if (ip.isBlank()) return false
        val parsedIP = ip.split(".")
        if (parsedIP.size != 4) return false

        return parsedIP.all { section ->
            section.toIntOrNull()?.let { it in 0..255 } ?: false
        }
    }
}