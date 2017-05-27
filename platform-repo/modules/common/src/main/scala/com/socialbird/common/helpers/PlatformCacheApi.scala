package com.socialbird.common.helpers

import javax.inject.{Inject, Singleton}

import play.api.cache.CacheApi

/**
  * A class to handle cache functionality
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
@Singleton
class PlatformCacheApi @Inject()(cache: CacheApi) extends PlatformLogger {

  /**
    * setData : Set user related data in user cache map
    *
    * @param token
    * @param key
    * @param data
    * @return
    */
  def setData[V](token: String, key: String, data: V): Boolean = {
    try {
      val dataMap = getData(token) ++ Map(key -> data)
      cache.set(token, dataMap)
      true
    } catch {
      case t: Throwable => error(t.getMessage, t); false
    }
  }

  /**
    * getData :Get cached data map of given token
    *
    * @param token
    * @return
    */
  def getData(token: String): Map[String, Any] = {
    cache.get[Map[String, Any]](token).getOrElse(Map())
  }

  /**
    * removeData:Remove cached data map for given token
    *
    * @param token
    * @return
    */
  def removeData(token: String): Boolean = {
    try {
      cache.remove(token); true
    } catch {
      case t: Throwable => error(t.getMessage, t); false
    }
  }

}
