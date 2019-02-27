package models

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc._
import play.api.Configuration
import play.api.db.Database
import anorm._
import anorm.SqlParser.{get, str}
import java.sql.Connection
import scala.concurrent.ExecutionContext.Implicits.global


object Episode extends Enumeration {
  val NEWHOPE, EMPIRE, JEDI = Value
}

trait Character {
  def id: String
  def name: Option[String]
  def friends: List[String]
  def appearsIn: List[Episode.Value]
}

case class User(
  id: Int,
  firstName: String,
  lastName: Option[String],
  username: Option[String],
  email: Option[String],
  theme: Option[String],
  prefs: Option[String],
)

case class Flashcard(
  id: Int,
  question: String,
  answer: Option[String],
  category: Option[String],
  assessmentMethod: Option[String],
  flashcards_id_fkey: Option[Int],
)

// object User extends Magic[User]
// object Flashcard extends Magic[Flashcard]

case class Human(
  id: String,
  name: Option[String],
  friends: List[String],
  appearsIn: List[Episode.Value],
  homePlanet: Option[String]) extends Character

case class Droid(
  id: String,
  name: Option[String],
  friends: List[String],
  appearsIn: List[Episode.Value],
  primaryFunction: Option[String]) extends Character

// def User(id: Int): Option[User] = {
//   val query = SQL("select * from Users").as( str("firstName") ~< int("id") * )
//   return query.execute()
// }

// @Singleton
// class DatabseConnection @Inject() (database: Database, config: Configuration) {
//   def startConn: Unit = {

//   }
// }

class DatabaseQuizzitude {
  import models.DatabaseQuizzitude._

  def getHero(episode: Option[Episode.Value]) =
    episode flatMap (_ ⇒ getHuman("1000")) getOrElse droids.last

  def getHuman(id: String): Option[Human] = humans.find(c ⇒ c.id == id)

  def getDroid(id: String): Option[Droid] = droids.find(c ⇒ c.id == id)

  // def getFlashcards(id: String): List[AnyRef] = 

  def getUser(id: Int, connection: Connection): User = {
    // Country.update(Country(Id("FRA"), "France", 59225700, Some("Nicolas S."))) <–– Magic[Country]

    val query: Option[Any] = SQL("Select * from Users").execute()
        // .as( str("firstName") ~< int("id") * )

      // SQL("SELECT * FROM Users WHERE id = {i}").
      //   on("id" -> "id").as(patternParser.*)

    println(query)


    println("in the getUser method")

    User(
      id = 2890,
      firstName = "Luke Skywalker",
      lastName = Some(""),
      username = Some(""),
      email = Some(""),
      theme = Some(""),
      prefs = Some("")
    )
  }

  // def getUsers(u: U): Any = {
  //   import play.api.db.Database

  //   println("––>>>> made it db getUsers in Requests !")

  //   val conn = Database.getConnection()

  //   try {
  //     val stmt = conn.createStatement

  //     val rs = stmt.executeQuery("SELECT * FROM users")

  //     println(rs)

  //     while (rs.next) {
  //        println(rs)
  //     }

  //     return "SOMe thing comoing BAAK"
  //   } finally {
  //     conn.close()
  //   }

  //}
}

object DatabaseQuizzitude {
  val humans = List(
    Human(
      id = "1000",
      name = Some("Luke Skywalker"),
      friends = List("1002", "1003", "2000", "2001"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      homePlanet = Some("Tatooine")),
    Human(
      id = "1001",
      name = Some("Darth Vader"),
      friends = List("1004"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      homePlanet = Some("Tatooine")),
    Human(
      id = "1002",
      name = Some("Han Solo"),
      friends = List("1000", "1003", "2001"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      homePlanet = None),
    Human(
      id = "1003",
      name = Some("Leia Organa"),
      friends = List("1000", "1002", "2000", "2001"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      homePlanet = Some("Alderaan")),
    Human(
      id = "1004",
      name = Some("Wilhuff Tarkin"),
      friends = List("1001"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      homePlanet = None)
  )

  val droids = List(
    Droid(
      id = "2000",
      name = Some("C-3PO"),
      friends = List("1000", "1002", "1003", "2001"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      primaryFunction = Some("Protocol")),
    Droid(
      id = "2001",
      name = Some("R2-D2"),
      friends = List("1000", "1002", "1003"),
      appearsIn = List(Episode.NEWHOPE, Episode.EMPIRE, Episode.JEDI),
      primaryFunction = Some("Astromech"))
  )
}