package controllers

import javax.inject._

import play.api._
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
    for {
      _ <- elasticService.createQuery()
      data <- elasticService.executeQuery()
    } yield Ok(views.html.index(data))
  }

}
