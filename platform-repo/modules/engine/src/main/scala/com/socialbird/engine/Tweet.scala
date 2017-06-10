package com.socialbird.engine

/**
  * Created by anand on 4/6/17.
  */
case class Tweet
(
  id: Long,
  text: String,
  createdAt: Long,
  source: String,
  languageCode: String,
  geoLocation: Array[Double] = Array.emptyDoubleArray,
  place: Map[String, String] = Map.empty[String, String],
  isFavorited: Boolean,
  isRetweeted: Boolean,
  isRetweet: Boolean,
  favoriteCount: Int,
  retweetCount: Int
) {

}
