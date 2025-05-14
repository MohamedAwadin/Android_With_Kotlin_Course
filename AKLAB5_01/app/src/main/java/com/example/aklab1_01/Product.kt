package com.example.aklab1_01

import java.io.Serializable


data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val thumbnail: String
) : Serializable