package com.socialbird.common.exception

/**
  * Spark Engine related exception code start from 2000
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
case class SparkProcessException(desc: String = "") extends PlatformException(2000, desc) {
  override val errorType = "SPARK_PROCESS_EXCEPTION"
}

case class DataFrameTransformException(desc: String = "") extends PlatformException(2001, desc) {
  override val errorType = "DATA_FRAME_TRANSFORM_EXCEPTION"
}

case class RDDTransformException(desc: String = "") extends PlatformException(2002, desc) {
  override val errorType = "RDD_TRANSFORM_EXCEPTION"
}

case class DataFrameLoadException(desc: String = "") extends PlatformException(2003, desc) {
  override val errorType = "DATA_FRAME_LOAD_EXCEPTION"
}

case class ElasticDataIngestException(desc: String = "") extends PlatformException(2004, desc) {
  override val errorType = "ELASTIC_DATA_INGEST_EXCEPTION"
}

case class ElasticDataExtractException(desc: String = "") extends PlatformException(2005, desc) {
  override val errorType = "ELASTIC_DATA_EXTRACT_EXCEPTION"
}

case class WorkFlowDataException(desc: String = "") extends PlatformException(2006, desc) {
  override val errorType = "WORKFLOW_DATA_EXCEPTION"
}
