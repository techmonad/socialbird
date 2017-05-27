package com.socialbird.common.exception

/**
  * Json related exception code start from 3000
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
case class JsonParseException(desc: String = "") extends PlatformException(3000, desc) {
  override val errorType = "JSON_PARSE_EXCEPTION"
}

case class InvalidJsonException(desc: String = "") extends PlatformException(3001, desc) {
  override val errorType = "INVALID_JSON_EXCEPTION"
}
