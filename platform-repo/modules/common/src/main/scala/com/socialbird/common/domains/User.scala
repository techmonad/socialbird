package com.socialbird.common.domains

import java.sql.Timestamp
import java.util.Date

/**
  * Created by anand on 10/6/17.
  */
case class User
(
  id: Long = 0L,
  name: String = "Unknown",
  screenName: String = "Unknown",
  location: String = "Unknown",
  description: String = "Unknown",
  profileImageURL: String = "Unknown",
  url: String = "Unknown",
  followers: Int = 0,
  followings: Int = 0,
  favourites: Int = 0,
  language: String = "Unknown",
  createdAt: Timestamp = new Timestamp(new Date().getTime)
) {

}
