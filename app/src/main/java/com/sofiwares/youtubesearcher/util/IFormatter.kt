package com.sofiwares.youtubesearcher.util

/**
 * Created by Ido Sofi on 11/25/2017.
 */
interface IFormatter<T> {
    fun format(value: T): String
}