package com.socialbird.common.exception

/**
  * Database related exception start from 1000
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
case class DatabaseException(desc: String = "") extends PlatformException(1000, desc) {
  override val errorType = "DB_EXCEPTION"
}
