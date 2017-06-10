package com.socialbird.engine.utils

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
  * Lazily instantiated singleton instance of SparkSession
  *
  * Created by anand on 4/6/17.
  */
object SparkSessionSingleton {

  @transient private var instance: SparkSession = _

  def getInstance(sparkConf: SparkConf): SparkSession = {
    if (instance == null) {
      instance = SparkSession
        .builder
        .config(sparkConf)
        .getOrCreate()
    }
    instance
  }

}
