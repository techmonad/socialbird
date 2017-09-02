package com.socialbird.common.helpers

import java.util

import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConversions._

/**
  * A class to handle config functionality
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
class PlatformConfig(conf: String) {

  lazy val config: Config = ConfigFactory.load(conf)

  /**
    * getString : It returns value to corresponding key as a String.
    *
    * @param key The Conf Key
    * @return
    */
  def getString(key: String): String = config.getString(key)

  /**
    * getInt : It returns value to corresponding key as a Int.
    *
    * @param key The Conf Key
    * @return
    */
  def getInt(key: String): Int = config.getInt(key)

  /**
    * getStringList : It returns list of values to corresponding to key.
    *
    * @param key The Conf Key
    * @return
    */
  def getStringList(key: String): List[String] = config.getStringList(key).toList

  /**
    * getLongList : It returns list of values to corresponding to key.
    *
    * @param key The Conf Key
    * @return
    */
  def getLongList(key: String): List[Long] = config.getLongList(key).toList.map(_.toLong)

}
