package models

import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema._

import scala.concurrent.Future

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {
  /**
    * Resolves the lists of users / flashcards. These resolutions are batched and
    * cached for the duration of a query.
    */

  // val users = Fetcher.caching(
  //   (ctx: QuizzitudeRepository, ids: Seq[Int]) ⇒
  //     Future.successful(ids.flatMap(id ⇒ ctx.getUser(id))))(HasId(_.id))

  val flashcards = Fetcher.caching(
    (ctx: QuizzitudeRepository, cards: List[AnyRef]) ⇒
      Future.successful(ctx.getFlashcards(id)))(HasId(_.id))

  val Flashcard =
    ObjectType(
      "Flashcard",
      "A question/answer pair.",
      fields[QuizzitudeRepository, Flashcard](
        Field("id", IntType,
          Some("The id of the flashcard."),
          resolve = _.value.id),
        Field("question", OptionType(StringType),
          Some("The first name of the flashcard."),
          resolve = _.value.question),
        Field("answer", OptionType(StringType),
          Some("The last name of the flashcard."),
          resolve = _.value.answer),
        Field("category", OptionType(StringType),
          Some("The category of the flashcard."),
          resolve = _.value.category),
        Field("assessmentMethod", OptionType(StringType),
          Some("The email of the flashcard."),
          resolve = _.value.assessmentMethod),
        Field("flashcards_id_fkey", OptionType(IntType),
          Some("The id of the flashcard's user."),
          resolve = _.value.flashcards_id_fkey),
      ))

  val User =
    ObjectType(
      "User",
      "A user of the Quizzitude app.",
      fields[QuizzitudeRepository, User](
        Field("id", IntType,
          Some("The id of the user."),
          resolve = _.value.id),
        Field("firstName", OptionType(StringType),
          Some("The first name of the user."),
          resolve = _.value.firstName),
        Field("lastName", OptionType(StringType),
          Some("The last name of the user."),
          resolve = _.value.lastName),
        Field("username", OptionType(StringType),
          Some("The username of the user."),
          resolve = _.value.username),
        Field("email", OptionType(StringType),
          Some("The email of the user."),
          resolve = _.value.email),
        Field("theme", OptionType(StringType),
          Some("The user's selected UI theme."),
          resolve = _.value.theme),
        Field("prefs", OptionType(StringType),
          Some("The prefs of the user."),
          resolve = _.value.prefs)
      ))
    ))

  val ID = Argument("id", StringType, description = "id of the character")
  val IdentificationNumber = Argument("id", IntType, description = "the user's id")
  val U = Argument("u", StringType, description = "a null value")

  val Query = ObjectType(
    "Query", fields[QuizzitudeRepository, Unit](
      Field("user", OptionType(User),
        arguments = IdentificationNumber :: Nil,
        // resolve = ctx ⇒ ctx.User(ctx arg IdentificationNumber)),
        resolve = ctx ⇒ ctx.ctx.getUser(ctx arg IdentificationNumber, connection)),
      Field("flashcards", ListType(Flashcard),
        arguments = IdentificationNumber :: Nil,
        resolve = ctx ⇒ ctx.ctx.getFlashcards(ctx arg IdentificationNumber)),

      // Field("user", User,
      //   arguments = ID :: Nil,
      //   resolve = Projector((ctx, f) ⇒ ctx.ctx.getUser(ctx arg ID).get))
    ))

  val QuizzitudeSchema = Schema(Query)
}
