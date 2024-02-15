package com.example.satset.data.remote.response.ads

import com.google.gson.annotations.SerializedName

data class AdsResponse(

	@field:SerializedName("epochtime")
	val epochtime: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("timezone")
	val timezone: Any? = null,

	@field:SerializedName("execution_time")
	val executionTime: String? = null
)

data class DataItem(

	@field:SerializedName("ads_image_url")
	val adsImageUrl: String? = null,

	@field:SerializedName("ads_title")
	val adsTitle: String? = null
)
