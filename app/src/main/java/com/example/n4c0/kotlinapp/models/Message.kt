package com.example.n4c0.kotlinapp.models

import java.util.*

data class Message(val authorId: String = "", val message: String = "", val profileImageURL: String = "", val sendAt: Date = Date())