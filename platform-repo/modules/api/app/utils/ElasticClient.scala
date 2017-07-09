package utils

/**
  * Created by anand on 9/7/17.
  */
class ElasticClient {

  @transient private var instance: Int = _

  def getInstance(): Unit = {
    if (instance == 0) {
      instance = 0
    }
    instance
  }

}
