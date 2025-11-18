package com.bussiness.slodoggiesapp.network

import java.io.IOException

class NoNetworkException(message: String = "No internet connection") : IOException(message)