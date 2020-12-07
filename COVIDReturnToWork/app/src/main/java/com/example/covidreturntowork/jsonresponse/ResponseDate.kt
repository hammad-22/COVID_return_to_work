package com.example.covidreturntowork.jsonresponse

import com.google.gson.annotations.SerializedName

//this code helps receive JSON data for past week
data class ResponseDate(

	@field:SerializedName("date")
	val date: Int? = null,

	@field:SerializedName("death")
	val death: Int? = null,

	@field:SerializedName("totalTestResultsIncrease")
	val totalTestResultsIncrease: Int? = null,

	@field:SerializedName("pending")
	val pending: Int? = null,

	@field:SerializedName("hospitalizedCurrently")
	val hospitalizedCurrently: Int? = null,

	@field:SerializedName("hospitalizedIncrease")
	val hospitalizedIncrease: Int? = null,

	@field:SerializedName("states")
	val states: Int? = null,

	@field:SerializedName("onVentilatorCumulative")
	val onVentilatorCumulative: Int? = null,

	@field:SerializedName("hospitalized")
	val hospitalized: Int? = null,

	@field:SerializedName("negative")
	val negative: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("hospitalizedCumulative")
	val hospitalizedCumulative: Int? = null,

	@field:SerializedName("inIcuCumulative")
	val inIcuCumulative: Int? = null,

	@field:SerializedName("negativeIncrease")
	val negativeIncrease: Int? = null,

	@field:SerializedName("positiveIncrease")
	val positiveIncrease: Int? = null,

	@field:SerializedName("deathIncrease")
	val deathIncrease: Int? = null,

	@field:SerializedName("totalTestResults")
	val totalTestResults: Int? = null,

	@field:SerializedName("inIcuCurrently")
	val inIcuCurrently: Int? = null,

	@field:SerializedName("dateChecked")
	val dateChecked: String? = null,

	@field:SerializedName("onVentilatorCurrently")
	val onVentilatorCurrently: Int? = null,

	@field:SerializedName("positive")
	val positive: Int? = null,

	@field:SerializedName("posNeg")
	val posNeg: Int? = null,

	@field:SerializedName("recovered")
	val recovered: Int? = null,

	@field:SerializedName("lastModified")
	val lastModified: String? = null,

	@field:SerializedName("hash")
	val hash: String? = null
)
