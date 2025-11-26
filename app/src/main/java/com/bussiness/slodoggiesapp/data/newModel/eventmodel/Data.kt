package com.bussiness.slodoggiesapp.data.newModel.eventmodel

data class Data(
    val `data`: MutableList<DataX>,
    val limit: Int,
    val page: String,
    val total_count: Int,
    val total_page: Int
)