package com.socialbird.common.helpers

import org.slf4j.LoggerFactory

/**
  * A trait to handle logging.
  *
  * Created by anand on 27/5/17.
  *
  * @author SocialBird
  */
trait PlatformLogger {

  private val logger = LoggerFactory.getLogger(this.getClass)

  protected def debug(message: String): Unit = logger.debug(message)

  protected def debug(message: String, exception: Throwable): Unit = logger.debug(message, exception)

  protected def info(message: String): Unit = logger.info(message)

  protected def info(message: String, exception: Throwable): Unit = logger.info(message, exception)

  protected def warn(message: String): Unit = logger.warn(message)

  protected def warn(message: String, exception: Throwable): Unit = logger.warn(message, exception)

  protected def error(message: String): Unit = logger.error(message)

  protected def error(message: String, exception: Throwable): Unit = logger.error(message, exception)

}
