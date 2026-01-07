package com.bussiness.slodoggiesapp.network

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEventBus @Inject constructor() {
    val sessionExpired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
}
