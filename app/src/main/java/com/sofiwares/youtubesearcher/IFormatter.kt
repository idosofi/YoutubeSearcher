package com.sofiwares.youtubesearcher

/**
 * Created by Ido Sofi on 11/25/2017.
 */
interface IFormatter<T> {
    fun format(value: T): String
}