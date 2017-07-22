package helpers

import models.ApiResponse
import play.api.libs.json.Json

/**
  * Created by imana on 7/22/2017.
  */
object JsonMapper {

  implicit val apiResponseJson = Json.format[ApiResponse]

}
