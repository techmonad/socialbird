package models

import play.api.libs.json.JsValue

/**
  * Created by imana on 7/22/2017.
  */
case class ApiRequest
(
  token: String,
  body: JsValue,
  params: Map[String, JsValue]
) {

}
