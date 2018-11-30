package com.example.n4c0.kotlinapp.utils

import io.reactivex.subjects.PublishSubject

object RxBus{
    private val publisher=PublishSubject.create<Any>()

    fun publish(event: Any){

    }
}