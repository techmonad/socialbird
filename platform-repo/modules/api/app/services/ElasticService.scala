package services

import javax.inject.Inject

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import play.api.libs.json.{JsValue, Json}
import utils.ElasticClient
import com.sksamuel.elastic4s.searches.aggs.DateHistogramAggregation
import com.sksamuel.elastic4s.searches.aggs.SumAggregationDefinition


import scala.concurrent.{ExecutionContext, Future}

/** s
  * Created by anand on 9/7/17.
  */
class ElasticService @Inject()(implicit ec: ExecutionContext) {

  import com.sksamuel.elastic4s.ElasticDsl._

  /**
    *
    * @return is top ten politicians.
    */
  /* def debugQuery(){
     val q = search("other" / "users").termQuery("text","modi").aggs{
     dateHistogramAggregation("histogram").interval(24*60*60).field("createdAt")
     }
     println("debug")
     println(ElasticClient.getInstance().show(q))
   }*/

  def debugQuery(name: String) {

    val q1 = search("other" / "users").matchQuery("text", "modi")

    println("==============Query with word in text===============")
    println(ElasticClient.getInstance().show(q1))

    //tweets  per day by a politician
    val q2 = search("other" / "users").termQuery("user.name", "modi").aggs {
      dateHistogramAggregation("hist").interval(24 * 60 * 60).field("createdAt")
    }
    println("==============tweets  per day by a politician===============")
    println(ElasticClient.getInstance().show(q2))

    //Average number of followers per day basis of a particular politicians
    val q3 = search("other" / "users").termQuery("user.name", name).aggs {
      dateHistogramAggregation("hist").field("createdAt").interval(24 * 60 * 60)
        .subAggregations(SumAggregationDefinition("avg_followers").field("users.followers"))
    }
    println("==============Average number of followers per day basis of a particular politicians===============")
    println(ElasticClient.getInstance().show(q3))

    //Average number a politicians is following  per day basis
     val q4 = search("other" / "users").termQuery("user.name", name).aggs {
       dateHistogramAggregation("hist").field("createdAt").interval(24 * 60 * 60)
         .subAggregations(SumAggregationDefinition("avg_followings").field("susers.followings"))
     }
    println("==============Average number a politicians is following  per day basis===============")
    println(ElasticClient.getInstance().show(q4))

    //top tweet based on max number of favouried tweet of a politician
     val q5 = search("other" / "users").termQuery("user.name", name).stats("favoriteCount")
    println("==============top tweet based on max number of favouried tweet of a politician===============")
    println(ElasticClient.getInstance().show(q5))

    //top tweets(max number of retweeted tweets) of a politicians
    val q6 = search("other" / "users").termQuery("user.name", name).stats("retweetCount")

    println("==============top tweets(max number of retweeted tweets) of a politicians===============")
    println(ElasticClient.getInstance().show(q6))
  }

  def testFunc(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").matchQuery("text", name)
  }.map {
    result =>
      result.hits.map { hit =>
        Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
      }.toList
  }


  /*
  * tweets  per day by a politician
 */
  def tweetsStats(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").termQuery("user.name", name).aggs {
      dateHistogramAggregation("hist").interval(24 * 60 * 60).field("createdAt")
      //.subAggregations(SumAggregationDefinition("sum_agg").field(""))
    }
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }

  /*
* Average number of followers per day basis of a particular politicians
*
* */
  def followersStats(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").termQuery("user.name", name).aggs {
      dateHistogramAggregation("hist").field("createdAt").interval(24 * 60 * 60)
        .subAggregations(SumAggregationDefinition("avg_followers").field("users.followers"))
    }
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }


  /*
* Average number a politicians is following  per day basis
*
* */
  def followingStats(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").termQuery("user.name", name).aggs {
      dateHistogramAggregation("hist").field("createdAt").interval(24 * 60 * 60)
        .subAggregations(SumAggregationDefinition("avg_followings").field("susers.followings"))
    }
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }


  /*
   * top tweet based on max number of favouried tweet of a politician
   *
   */
  def tweetsStatsByName(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").termQuery("user.name", name).stats("favoriteCount")
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }

  /*
* top tweets(max number of retweeted tweets) of a politicians
*
* */
  def topRetweeted(name: String): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("other" / "users").termQuery("user.name", name).stats("retweetCount")
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }

}

