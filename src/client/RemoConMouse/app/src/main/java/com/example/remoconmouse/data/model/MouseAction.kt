package com.example.remoconmouse.data.model

fun move(x: Int, y: Int): String {
    return "move,${x},${y}"
}

fun click(type: String): String {
    return "click,${type}"
}

fun scroll(type: String): String {
    return "scroll,$type"
}