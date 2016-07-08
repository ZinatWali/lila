package lila.soclog

import org.joda.time.DateTime

case class OAuth(
    _id: String, // random
    provider: String,
    tokens: AccessToken,
    profile: Profile,
    createdAt: DateTime,
    updatedAt: DateTime) {

  def id = _id
}

object OAuth {

  def makeId(provider: Provider, profile: Profile) =
    s"${provider.name}:${profile.userId}"

  def make(provider: Provider, profile: Profile, tokens: AccessToken) = OAuth(
    _id = ornicar.scalalib.Random nextStringUppercase 10,
    provider = provider.name,
    profile = profile,
    tokens = tokens,
    createdAt = DateTime.now,
    updatedAt = DateTime.now)

  object BSONHandlers {
    import lila.db.BSON
    import lila.db.dsl._
    import reactivemongo.bson._
    implicit val AccessTokenHandler = Macros.handler[AccessToken]
    implicit val ProfileHandler = Macros.handler[Profile]
    implicit val OAuthHandler = Macros.handler[OAuth]
  }
}