package com.example.remoconmouse.utils

object MouseActionUtils {
    fun connect(): String {
        return "connect"
    }

    fun disconnect(): String {
        return "disconnect"
    }

    fun move(x: Int, y: Int): String {
        return "move,${x},${y}"
    }

    fun click(type: String): String {
        return "click,$type"
    }

    fun scroll(type: String): String {
        return "scroll,$type"
    }
}