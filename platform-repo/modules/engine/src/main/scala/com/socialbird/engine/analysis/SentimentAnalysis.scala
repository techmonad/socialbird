package com.socialbird.engine.analysis

import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

import scala.collection.JavaConverters._

/**
  * Created by anand on 10/6/17.
  */
object SentimentAnalysis {

  @transient private var sentimentPipeline: StanfordCoreNLP = _

  def intToSentiment(value: Int): String = {
    value match {
      case 0 => "STRONG_NEGATIVE"
      case 1 => "WEAK_NEGATIVE"
      case 2 => "NEUTRAL"
      case 3 => "WEAK_POSITIVE"
      case 4 => "STRONG_POSITIVE"
      case _ => "NEUTRAL"
    }
  }

  private def getOrCreateSentimentPipeline(): StanfordCoreNLP = {
    if (sentimentPipeline == null) {
      val props = new Properties()
      props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
      sentimentPipeline = new StanfordCoreNLP(props)
    }
    sentimentPipeline
  }

  def sentiment: UserDefinedFunction = udf { sentence: String =>
    try {
      val pipeline = getOrCreateSentimentPipeline()
      val annotation = pipeline.process(sentence)
      val tree = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
        .asScala
        .head
        .get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])
      intToSentiment(RNNCoreAnnotations.getPredictedClass(tree))
    } catch {
      case ex: Exception => intToSentiment(0)
    }
  }

}
