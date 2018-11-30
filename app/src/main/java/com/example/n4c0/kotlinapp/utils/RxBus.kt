package com.example.n4c0.kotlinapp.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

object RxBus{
    private val publisher=PublishSubject.create<Any>()

    fun publish(event: Any){
        publisher.onNext(event)
    }

    fun<T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}