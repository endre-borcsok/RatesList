package com.example.app.network.model

interface RatesEntity {
    var success: Boolean
    var base: String
    var date: String
    var rates: Map<String, Float>
}