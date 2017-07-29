package services

import javax.inject.Inject

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import play.api.libs.json.{JsValue, Json}
import utils.ElasticClient

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by anand on 9/7/17.
  */
class ElasticService @Inject()(implicit ec: ExecutionContext) {

  import com.sksamuel.elastic4s.ElasticDsl._

  /**
    *
    * @return will top ten politicians.
    */
  def topTenPoliticians(): Future[List[JsValue]] = ElasticClient.getInstance().execute {
    search("tweet/analysis")
  }.map { result =>
    result.hits.map { hit =>
      Json.toJson(hit.sourceAsMap.map { case (k, v) => k -> v.toString })
    }.toList
  }

}
