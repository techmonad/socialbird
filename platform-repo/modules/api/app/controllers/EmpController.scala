package controllers

import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

case class EmpData(name: String, age: Int)

/**
 * Emp form controller for Play Scala
 */
class EmpController @Inject()(mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {

  val empForm = Form(
    mapping(
      "name" -> text,
      "age" -> number
    )(EmpData.apply)(EmpData.unapply)
  )

  def empGet() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.emp.form(empForm))
  }

  def empPost() = Action { implicit request: MessagesRequest[AnyContent] =>
    empForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.emp.form(formWithErrors))
      },
      empData => {
        /* binding success, you get the actual value. */       
        /* flashing uses a short lived cookie */ 
        Redirect(routes.EmpController.empGet()).flashing("success" -> ("Successful " + empData.toString))
      }
    )
  }
}
