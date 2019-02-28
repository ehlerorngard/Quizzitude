package models

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc._
import play.api.Configuration
import play.api.db.Database
import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global

import java.util.Date
import play.api.db.DBApi
import anorm._
import anorm.SqlParser.{get, str, scalar}
import scala.concurrent.Future


case class User(
  id: Int,
  firstName: String,
  lastName: Option[String],
  username: Option[String],
  email: Option[String],
  theme: Option[String],
  prefs: Option[String],
)

object User {
  implicit def toParameters: ToParameterList[User] =
    Macro.toParameters[User]
}

case class Flashcard(
  id: Int,
  question: String,
  answer: Option[String],
  category: Option[String],
  assessmentMethod: Option[String],
  flashcards_id_fkey: Option[Int],
)

object Flashcard {
  implicit def toParameters: ToParameterList[Flashcard] =
    Macro.toParameters[Flashcard]
}

@javax.inject.Singleton
class QuizzitudeRepository @Inject()(dbapi: DBApi, companyRepository: CompanyRepository)(implicit ec: DatabaseExecutionContext) {   //// OPTIONALLY inject other repository
  import models.QuizzitudeRepository._  

  private val db = dbapi.database("default")

/////////////////////
//// parsers   /////
///////////////////
  /**
   * Parse from a ResultSet
   */
  private val simpleUser = {
    get[Option[Int]]("user.id") ~
      get[String]("user.firstName") ~
      get[String]("user.lastName") ~
      get[String]("user.username") ~
      get[String]("user.email") ~
      get[String]("user.theme") ~
      get[String]("user.prefs") map {
      case id ~ firstName ~ lastName ~ username ~ email ~ theme ~ prefs =>
        User(id, firstName, lastName, username, email, theme, prefs)
    }
  }

  private val simpleFlashcard = {
    get[Option[Int]]("flashcard.id") ~
      get[String]("flashcard.question") ~
      get[String]("flashcard.answer") ~
      get[String]("flashcard.category") ~
      get[String]("flashcard.assessmentMethod") ~
      get[String]("flashcard.flashcards_id_fkey") map {
      case id ~ question ~ answer ~ category ~ assessmentMethod ~ flashcards_id_fkey =>
        User(id, question, answer, category, assessmentMethod, flashcards_id_fkey)
    }
  }


/////////////////////////
//// CRUD methods   ////
///////////////////////

  def getFlashcardsByUser(id: Int): Future[List[Flashcard]] = Future {
    db.withConnection { implicit connection => 
      SQL("""
        select * from flashcards
        where flashcards_id_fkey = {id}
      """).as(simpleFlashcard)
    }
  }

  def getFlashcard(id: Int): Future[Flashcard] = Future {
    db.withConnection { implicit connection => 
      SQL(""" 
        select from flashcards
        where id = {id}
      """)
    }
  }

  def insertFlashcard(flashcard: Flashcard): Future[Option[Long]] = Future {
    db.withConnection { implicit connection =>
      SQL("""
        insert into flashcard values (
          (select next value for flashcard_seq),
          {name}, {introduced}, {discontinued}, {companyId}
        )
      """).bind(flashcard).executeInsert()
    }
  }

  def updateFlashcard(id: Long, flashcard: Flashcard) = Future {
    db.withConnection { implicit connection =>
      SQL("""
        update flashcard set name = {name}, introduced = {introduced}, 
          discontinued = {discontinued}, company_id = {companyId}
        where id = {id}
      """).bind(flashcard.copy(id = Some(id)/* ensure */)).executeUpdate()
      // case class binding using ToParameterList,
      // note using SQL(..) but not SQL.. interpolation
    }
  }

  def deleteFlashcard(id: Int) = Future {
    db.withConnection { implicit connection => 
      SQL(""" 
        DELETE from flashcards
        where id = {id}
      """).executeUpdate()
    }
  }

}

