package com.example.satset.data.remote.response.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
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

data class Events(
	@field:SerializedName("event_qr")
	val eventQr: Any? = null,

	@field:SerializedName("event_code")
	val eventCode: String? = null,

	@field:SerializedName("event_name")
	val eventName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class DataItem(
	@field:SerializedName("event_id")
	val eventId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("queue_number")
	val queueNumber: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("events")
	val events: Events? = null, // Renamed to MyEvents

	@field:SerializedName("status")
	val status: String? = null
)

