package com.bussiness.slodoggiesapp.data.model.businessProvider

import com.google.gson.annotations.SerializedName

data class Service(  @SerializedName("serviceId")
                     val serviceId: Int = 0,

                     @SerializedName("serviceTitle")
                     val serviceTitle: String = "",

                     @SerializedName("price")
                     val price: String = "",

                     @SerializedName("currency")
                     val currency: String = "",

                     @SerializedName("description")
                     val description: String = "",

                     @SerializedName("media")
                     val media: List<ServiceMedia> = emptyList())
