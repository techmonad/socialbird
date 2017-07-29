package controllers

import javax.inject._

import models.ApiResponse
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.ElasticService

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(elasticService: ElasticService)(cc: ControllerComponents, implicit val ec: ExecutionContext)
  extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action.async { implicit request: Request[AnyContent] =>
    val result = for {
       data <- elasticService.topTenPoliticians()
    } yield ApiResponse(OK, None, Map("total" -> Json.toJson(data), "results" -> Json.toJson(data)))

    result.map(apiRes => Ok(apiRes.toJson))
  }
}
