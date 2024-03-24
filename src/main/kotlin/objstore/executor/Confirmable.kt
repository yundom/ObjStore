package com.github.yundom.objstore.executor

interface Confirmable {
    fun prompt(): String
}