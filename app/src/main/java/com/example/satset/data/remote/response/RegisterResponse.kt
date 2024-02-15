package com.example.satset.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("epochtime")
	val epochtime: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("timezone")
	val timezone: Any? = null,

	@field:SerializedName("execution_time")
	val executionTime: String? = null
) {
	data class Data(

		@field:SerializedName("user")
		val user: User? = null,

		@field:SerializedName("token")
		val token: String? = null
	)
}

data class User(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
