package com.example.satset.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("epochtime")
	val epochtime: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("data")
	val data: Data? = null, // No need to declare Data class here

	@field:SerializedName("timezone")
	val timezone: Any? = null,

	@field:SerializedName("execution_time")
	val executionTime: String? = null
) {
	data class Data(
		@field:SerializedName("token")
		val token: String? = null
	)
}