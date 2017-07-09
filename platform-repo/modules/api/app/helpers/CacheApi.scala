package helpers

import javax.inject.{Inject, Singleton}

import akka.Done
import play.api.cache.AsyncCacheApi

import scala.concurrent.{ExecutionContext, Future}

/**
  * A class to handle cache functionality
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
@Singleton
class CacheApi @Inject()(cache: AsyncCacheApi)(implicit ec: ExecutionContext) {

  /**
    * setData : Set user related data in user cache map
    *
    * @param token The Cache token
    * @param key   The Data Map Key
    * @param data  The Data
    * @return Future[Done]
    */
  def setData[V](token: String, key: String, data: V): Future[Done] = {
    cache.set(token, getData(token).map(_ ++ Map(key -> data)))
  }

  /**
    * getData :Get cached data map of given token
    *
    * @param token The Cache token
    * @return The Future Data Map
    */
  def getData(token: String): Future[Map[String, Any]] = {
    cache.get[Map[String, Any]](token).map(_.getOrElse(Map()))
  }

  /**
    * removeData:Remove cached data map for given token
    *
    * @param token The Cache token
    * @return The Future[Done]
    */
  def removeData(token: String): Future[Done] = {
    cache.remove(token)
  }

}
