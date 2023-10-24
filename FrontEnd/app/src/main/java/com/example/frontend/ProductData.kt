package com.example.frontend

import java.io.Serializable
import java.util.Date

data class ProductData  (
    val id : Int,
    val name : String,
    val inform : String,
    val img : String,
    // val date: Date,
    val price : Int,
    val xSize : Double,
    val ySize : Double,
    val zSize : Double
) : Serializable