package com.socialbird.common.utils

import java.sql
import java.sql.Timestamp
import java.util.Date

import com.socialbird.common.domains.{Tweet, User}
import twitter4j.{GeoLocation, Place, Status}

object TwitterUtility {

  implicit def dateToEpochMilli(date: Date): Long = date.toInstant.toEpochMilli

  implicit def geoLocationToArray(geo: GeoLocation): Array[Double] = {
    if (null != geo) Array(geo.getLatitude, geo.getLongitude)
    else Array.emptyDoubleArray
  }

  implicit def placeToMap(place: Place): Map[String, String] = {
    import place._
    if (null != place) Map("id" -> getId, "name" -> getName, "country" -> getCountry, "countryCode" -> getCountryCode)
    else Map.empty[String, String]
  }

  implicit def convertJavaDateToSqlDate(date: java.util.Date): sql.Date = new java.sql.Date(date.getTime)

  implicit def dateToTimestamp(date: java.util.Date): Timestamp = new Timestamp(date.getTime)

  implicit def tweetUserToUser(user: twitter4j.User): User = {
    import user._
    if (null != user) {
      User(getId, getName, getScreenName, getLocation, getDescription, getProfileImageURL,
        getURL, getFollowersCount, getFriendsCount, getFavouritesCount, getLang, getCreatedAt)
    } else {
      User()
    }
  }

  def statusToTweet(status: Status): Tweet = {
    import status._
    Tweet(getId, getText, getCreatedAt, getSource, getLang, getGeoLocation, getPlace,
      isFavorited, isRetweeted, isRetweet, getFavoriteCount, getRetweetCount, getUser)
  }

}
