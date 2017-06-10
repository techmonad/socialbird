package com.socialbird.engine

import java.util.Date

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

  def statusToTweet(status: Status): Tweet = {
    import status._
    Tweet(getId, getText, getCreatedAt, getSource, getLang, getGeoLocation, getPlace,
      isFavorited, isRetweeted, isRetweet, getFavoriteCount, getRetweetCount)
  }


}
