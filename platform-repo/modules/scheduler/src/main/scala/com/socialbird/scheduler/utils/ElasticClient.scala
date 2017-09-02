package com.socialbird.scheduler.utils

import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}

/**
  * Created by anand on 9/7/17.
  */
object ElasticClient {

  @transient private var instance: TcpClient = _

  def getInstance(): TcpClient = {
    if (instance == null) {
      instance = TcpClient.transport(ElasticsearchClientUri("localhost", 9300))
    }
    instance
  }

  def closeInstance(): Unit = {
    instance.close()
  }

}
