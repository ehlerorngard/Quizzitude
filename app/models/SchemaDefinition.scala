package models

import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema._

import scala.concurrent.Future

/**
 * Defines a GraphQL schema for the current project
 */
object SchemaDefinition {
  /**
    * Resolves the lists of characters. These resolutions are batched and
    * cached for the duration of a query.
    */
  val characters = Fetcher.caching(
    (ctx: DatabaseQuizzitude, ids: Seq[String]) ⇒
      Future.successful(ids.flatMap(id ⇒ ctx.getHuman(id) orElse ctx.getDroid(id))))(HasId(_.id))

  // val users = Fetcher.caching(
  //   (ctx: DatabaseQuizzitude, ids: Seq[Int]) ⇒
  //     Future.successful(ids.flatMap(id ⇒ ctx.getUser(id))))(HasId(_.id))

  // val flashcards = Fetcher.caching(
  //   (ctx: DatabaseQuizzitude, cards: List[AnyRef]) ⇒
  //     Future.successful(ctx.getFlashcards(id)))(HasId(_.id))


  val EpisodeEnum = EnumType(
    "Episode",
    Some("One of the films in the Star Wars Trilogy"),
    List(
      EnumValue("NEWHOPE",
        value = Episode.NEWHOPE,
        description = Some("Released in 1977.")),
      EnumValue("EMPIRE",
        value = Episode.EMPIRE,
        description = Some("Released in 1980.")),
      EnumValue("JEDI",
        value = Episode.JEDI,
        description = Some("Released in 1983."))))

  val Character: InterfaceType[DatabaseQuizzitude, Character] =
    InterfaceType(
      "Character",
      "A character in the Star Wars Trilogy",
      () ⇒ fields[DatabaseQuizzitude, Character](
        Field("id", StringType,
          Some("The id of the character."),
          resolve = _.value.id),
        Field("name", OptionType(StringType),
          Some("The name of the character."),
          resolve = _.value.name),
        Field("friends", ListType(Character),
          Some("The friends of the character, or an empty list if they have none."),
          complexity = Some((_, _, children) ⇒ 100 + 1.5 * children),
          resolve = ctx ⇒ characters.deferSeqOpt(ctx.value.friends)),
        Field("appearsIn", OptionType(ListType(OptionType(EpisodeEnum))),
          Some("Which movies they appear in."),
          resolve = _.value.appearsIn map (e ⇒ Some(e)))
      ))

  val Flashcard =
    ObjectType(
      "Flashcard",
      "A question/answer pair.",
      fields[DatabaseQuizzitude, Flashcard](
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
      fields[DatabaseQuizzitude, User](
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
        // Field("friends", ListType(Character),
        //   Some("The friends of the human, or an empty list if they have none."),
        //   complexity = Some((_, _, children) ⇒ 100 + 1.5 * children),
        //   resolve = ctx ⇒ characters.deferSeqOpt(ctx.value.friends)),
        // Field("appearsIn", OptionType(ListType(OptionType(EpisodeEnum))),
        //   Some("Which movies they appear in."),
        //   resolve = _.value.appearsIn map (e ⇒ Some(e))),
      ))

  val Human =
    ObjectType(
      "Human",
      "A humanoid creature in the Star Wars universe.",
      interfaces[DatabaseQuizzitude, Human](Character),
      fields[DatabaseQuizzitude, Human](
        Field("id", StringType,
          Some("The id of the human."),
          resolve = _.value.id),
        Field("name", OptionType(StringType),
          Some("The name of the human."),
          resolve = _.value.name),
        Field("friends", ListType(Character),
          Some("The friends of the human, or an empty list if they have none."),
          complexity = Some((_, _, children) ⇒ 100 + 1.5 * children),
          resolve = ctx ⇒ characters.deferSeqOpt(ctx.value.friends)),
        Field("appearsIn", OptionType(ListType(OptionType(EpisodeEnum))),
          Some("Which movies they appear in."),
          resolve = _.value.appearsIn map (e ⇒ Some(e))),
        Field("homePlanet", OptionType(StringType),
          Some("The home planet of the human, or null if unknown."),
          resolve = _.value.homePlanet)
      ))

  val Droid = ObjectType(
    "Droid",
    "A mechanical creature in the Star Wars universe.",
    interfaces[DatabaseQuizzitude, Droid](Character),
    fields[DatabaseQuizzitude, Droid](
      Field("id", StringType,
        Some("The id of the droid."),
        tags = ProjectionName("_id") :: Nil,
        resolve = _.value.id),
      Field("name", OptionType(StringType),
        Some("The name of the droid."),
        resolve = ctx ⇒ Future.successful(ctx.value.name)),
      Field("friends", ListType(Character),
        Some("The friends of the droid, or an empty list if they have none."),
        complexity = Some((_, _, children) ⇒ 100 + 1.5 * children),
        resolve = ctx ⇒ characters.deferSeqOpt(ctx.value.friends)),
      Field("appearsIn", OptionType(ListType(OptionType(EpisodeEnum))),
        Some("Which movies they appear in."),
        resolve = _.value.appearsIn map (e ⇒ Some(e))),
      Field("primaryFunction", OptionType(StringType),
        Some("The primary function of the droid."),
        resolve = _.value.primaryFunction)
    ))

  val ID = Argument("id", StringType, description = "id of the character")
  val IdentificationNumber = Argument("id", IntType, description = "the user's id")
  val U = Argument("u", StringType, description = "a null value")

  val EpisodeArg = Argument("episode", OptionInputType(EpisodeEnum),
    description = "If omitted, returns the hero of the whole saga. If provided, returns the hero of that particular episode.")

  val Query = ObjectType(
    "Query", fields[DatabaseQuizzitude, Unit](
      Field("hero", Character,
        arguments = EpisodeArg :: Nil,
        deprecationReason = Some("Use `human` or `droid` fields instead"),
        resolve = (ctx) ⇒ ctx.ctx.getHero(ctx.arg(EpisodeArg))),
      // Field("users", ListType(User),
      //   arguments = U :: Nil,
      //   resolve = ctx ⇒ ctx.ctx.getUsers(ctx arg U)),
      Field("user", OptionType(User),
        arguments = IdentificationNumber :: Nil,
        // resolve = ctx ⇒ ctx.User(ctx arg IdentificationNumber)),
        resolve = ctx ⇒ ctx.ctx.getUser(ctx arg IdentificationNumber, connection)),
      // Field("flashcards", ListType(Flashcard),
      //   arguments = IdentificationNumber :: Nil,
      //   resolve = ctx ⇒ ctx.ctx.getFlashcards(ctx arg IdentificationNumber)),
      Field("human", OptionType(Human),
        arguments = ID :: Nil,
        resolve = ctx ⇒ ctx.ctx.getHuman(ctx arg ID)),
      Field("droid", Droid,
        arguments = ID :: Nil,
        resolve = Projector((ctx, f) ⇒ ctx.ctx.getDroid(ctx arg ID).get))
    ))

  val FlashcardsSchema = Schema(Query)
}
