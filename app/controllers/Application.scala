package controllers

import play.api._
import play.api.mvc._
import javax.inject.Inject

class Application @Inject() (components: ControllerComponents) extends AbstractController(components) {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
