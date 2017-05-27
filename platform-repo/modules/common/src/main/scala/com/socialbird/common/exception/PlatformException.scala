package com.socialbird.common.exception

/**
  * Platform related exception framework
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
abstract class PlatformException(val statusCode: Int, val description: String) extends Exception(description) {

  def this(description: String) = this(400, description)

  val errorType: String

}
