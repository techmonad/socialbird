package models

import play.api.libs.json.{JsValue, Json}


/**
  * Created by imana on 7/22/2017.
  */
case class ApiResponse
(
  status: Int,
  message: Option[String] = None,
  data: Map[String, JsValue] = Map.empty[String, JsValue]
) {

  import helpers.JsonMapper._

  def toJson = Json.toJson(this)

  def toJsonString = toJson.toString

}

