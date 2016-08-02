package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.db.slick._
import slick.driver.JdbcProfile
import models.Tables._
import javax.inject.Inject

import slick.BlockingH2Driver._
import slick.BlockingH2Driver.api._

object UserController {
  
  case class UserForm(id: Option[Long], name: String, companyId: Option[Int])
  
  val userForm = Form(
    mapping(
      "id"        -> optional(longNumber),
      "name"      -> nonEmptyText(maxLength = 20),
      "companyId" -> optional(number)
    )(UserForm.apply)(UserForm.unapply)
  )
  
}

class UserController @Inject()(val dbConfigProvider: DatabaseConfigProvider, val messagesApi: MessagesApi) extends Controller 
    with HasDatabaseConfigProvider[JdbcProfile] with I18nSupport {
  import UserController._

  def list = Action { implicit request =>
    db.withSession { implicit session =>
      Ok(views.html.user.list(Users.sortBy(t => t.id).list))
    }
  }

  def edit(id: Option[Long]) = Action { implicit request =>
    db.withSession { implicit session =>
      val form = if (id.isDefined) {
        val user = Users.filter(t => t.id === id.get.bind).first
        userForm.fill(UserForm(Some(user.id), user.name, user.companyId))
      } else {
        userForm
      }

      Ok(views.html.user.edit(form, Companies.sortBy(_.id).list))
    }
  }
  
  def create = Action { implicit request =>
    db.withSession { implicit session =>
      userForm.bindFromRequest.fold(
        error => {
          BadRequest(views.html.user.edit(error, Companies.sortBy(t => t.id).list))
        },
        form => {
          val user = UsersRow(0, form.name, form.companyId)
          Users.unsafeInsert(user)
          Redirect(routes.UserController.list)
        }
      )
    }
  }
  
  def update = Action { implicit request =>
    db.withSession { implicit session =>
      userForm.bindFromRequest.fold(
        error => {
          BadRequest(views.html.user.edit(error, Companies.sortBy(t => t.id).list))
        },
        form => {
          val user = UsersRow(form.id.get, form.name, form.companyId)
          Users.filter(t => t.id === user.id.bind).unsafeUpdate(user)
          Redirect(routes.UserController.list)
        }
      )
    }
  }
  
  def remove(id: Long) = Action { implicit request =>
    db.withSession { implicit session =>
      Users.filter(t => t.id === id.bind).unsafeDelete
      Redirect(routes.UserController.list)
    }
  }

}