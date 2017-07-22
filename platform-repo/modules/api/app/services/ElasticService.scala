package services

import javax.inject.Inject

import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import utils.ElasticClient

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by anand on 9/7/17.
  */
class ElasticService @Inject()(implicit ec: ExecutionContext) {

  import com.sksamuel.elastic4s.ElasticDsl._

  def executeQuery(): Future[String] = ElasticClient.getInstance().execute {
    search("myindex")
  }.map { result => result.hits.head.sourceAsString }

  def createQuery(): Future[String] = ElasticClient.getInstance().execute {
    bulk(
      indexInto("myindex" / "mytype").fields("country" -> "Mongolia", "capital" -> "Ulaanbaatar"),
      indexInto("myindex" / "mytype").fields("country" -> "Namibia", "capital" -> "Windhoek")
    ).refresh(RefreshPolicy.WAIT_UNTIL)
  }.map { result => result.toString }

}
