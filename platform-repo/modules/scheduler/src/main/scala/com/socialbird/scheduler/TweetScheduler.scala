package com.socialbird.scheduler

import java.util.List

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.socialbird.common.utils.TwitterUtility
import com.socialbird.scheduler.utils.SchedulerConf
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import twitter4j.TwitterFactory
import twitter4j.conf._

import scala.collection.JavaConversions._

/**
  * Created by Sanjul on 31/08/17.
  */
object TweetScheduler extends App {

  val cb = new ConfigurationBuilder()

  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(SchedulerConf.consumerKey)
    .setOAuthConsumerSecret(SchedulerConf.consumerSecret)
    .setOAuthAccessToken(SchedulerConf.accessToken)
    .setOAuthAccessTokenSecret(SchedulerConf.accessTokenSecret)

  val factory = new TwitterFactory()
  val twitter = factory.getInstance()

  val politiciansMap = Map("narendramodi" -> "18839785",
    "arunjaitley" -> "2183816041",
    "rajnathsingh" -> "1346439824",
    "rsprasad" -> "2222673457",
    "smritiirani" -> "130104041",
    "AmitShah" -> "1447949844",
    "Swamy39" -> "60937837",
    "digvijaya_28" -> "371730289",
    "ajaymaken" -> "59983585",
    "JhaSanjay" -> "50943008",
    "KapilSibal" -> "1910442534",
    "priyankac19" -> "19760270",
    "ShashiTharoor" -> "24705126",
    "ArvindKejriwal" -> "405427035",
    "msisodia" -> "30417501",
    "DrKumarVishwas" -> "141633175",
    "_YogendraYadav" -> "1317076194",
    "PiyushGoyal" -> "1324334436",
    "nitin_gadkari" -> "97217966",
    "PrakashJavdekar" -> "1072993274",
    "sureshpprabhu" -> "141208596",
    "manoharparrikar" -> "347035710",
    "PChidambaram_IN" -> "3097503906",
    "JM_Scindia" -> "2892809359",
    "rssurjewala" -> "3066787711",
    "meira_kumar" -> "879284103022956544",
    "laluprasadrjd" -> "786958314",
    "NitishKumar" -> "143409075",
    "MamataOfficial" -> "2526794479",
    "Gen_VKSingh" -> "1856414335",
    "drharshvardhan" -> "77732185",
    "drramansingh" -> "373280952",
    "ChouhanShivraj" -> "1251083774",
    "ncbn" -> "85221650"
  )

  var instance: TcpClient = TcpClient.transport(ElasticsearchClientUri("localhost", 9300))

  import com.sksamuel.elastic4s.ElasticDsl._

  instance.execute {
    deleteIndex("socialbird/politicianTweets")
  }

  println("socialbird/politicianTweets index deleted/flushed")
  for ((k, v) <- politiciansMap) {
    val statusList: List[twitter4j.Status] = twitter.getUserTimeline(v.toLong)
    for (inVal <- statusList) {
      val tweet: com.socialbird.common.domains.Tweet = TwitterUtility.statusToTweet(inVal)
      val gson: com.google.gson.Gson = new com.google.gson.Gson()
      val tweetJson: String = gson.toJson(tweet)
      instance.execute {
        indexInto("socialbird" / "politicianTweets") doc tweetJson refresh RefreshPolicy.IMMEDIATE
      }
    }
  }

  println("===========All tweets saved successfully===========")

  for ((k, v) <- politiciansMap) {
    val user: twitter4j.User = twitter.showUser(v.toLong)
    val usr: com.socialbird.common.domains.User = TwitterUtility.tweetUserToUser(twitter.showUser(v.toLong))
    val gson: com.google.gson.Gson = new com.google.gson.Gson()
    val userJson: String = gson.toJson(usr)
    instance.execute {
      indexInto("socialbird" / "politicians") doc userJson refresh (RefreshPolicy.IMMEDIATE)
    }.await
    println("Saving details of |" + user.getScreenName() + "|")
  }

  println("==========Politicians details are saved successfully=============")

}