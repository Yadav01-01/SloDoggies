package com.bussiness.slodoggiesapp.model.petOwner

data class EventData( val id: Int,
                      val title: String,
                      val description: String,
                      val date: String,
                      val time: String,
                      val duration: String,
                      val location: String,
                      val imageRes: Int? = null,
                      val hasImage: Boolean = false,
    val buttonName : String)
