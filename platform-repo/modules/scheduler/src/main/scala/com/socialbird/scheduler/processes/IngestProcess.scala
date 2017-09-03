package com.socialbird.scheduler.processes

import com.sksamuel.elastic4s.TcpClient
import com.socialbird.common.utils.ElasticClient
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object IngestProcess {

  import com.sksamuel.elastic4s.ElasticDsl._

  private val client: TcpClient = ElasticClient.getInstance()

  def ingest(index: String, iType: String, data: String): Future[Boolean] = {
    client.execute {
      indexInto(index / iType) doc data refresh RefreshPolicy.IMMEDIATE
    }.map(_.created)
  }

  def delete(index: String, iType: String): Future[Long] = {
    client.execute {
      deleteIn(index / iType).by(matchAllQuery())
    }.map(_.getDeleted)
  }

}
