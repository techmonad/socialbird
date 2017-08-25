package services

import javax.inject.Inject

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import play.api.libs.json.{JsValue, Json}
import utils.ElasticClient
import com.sksamuel.elastic4s.searches.aggs.DateHistogramAggregation


import scala.concurrent.{ExecutionContext, Future}

/**s
  * Created by anand on 9/7/17.
  */
class ElasticService @Inject()(implicit ec: ExecutionContext) {

  import com.sksamuel.elastic4s.ElasticDsl._

  /**
    *
    * @return is top ten politicians.
    */
  def debugQuery(){
    val q = search("other" / "users").termQuery("text","modi").aggs{
    dateHistogramAggregation("histogram").interval(24*60*60).field("createdAt")
    }
    println("debug")
    println(ElasticClient.getInstance().show(q))
  }

  def topTenPoliticians(): Future[List[JsValue]] = ElasticClient.getInstance().execute {
     //search in "other"->"users" query { termQuery("text", "india") }

    //search in "other"->"users" rawQuery {"query":{"match_all": {}}}

  /*   search("other/users").AggregationBuilders
      .dateHistogram("agg")
      .field("createdAt")
      .dateHistogramInterval(DateHistogramInterval.days(1));;*/
    search("other" / "users").termQuery("text","modi").aggs{
      dateHistogramAggregation("histogram").interval(24*60*60).field("createdAt")
    }

  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }

}

