package poligon.cats.data

import cats.Id
import cats.data.{Kleisli, Reader}

object ReaderExample {

  import cats.syntax.applicative._

  type DBReader[A] = Reader[Db, A]

  def main(args: Array[String]): Unit = {

  }

  case class Db(
                 usernames: Map[Int, String],
                 passwords: Map[String, String]
               )


  def findUsername(userId: Int): DBReader[Option[String]] =
    Reader(db => db.usernames.get(userId))

  def checkPassword(
                     username: String,
                     password: String): DBReader[Boolean] =

    Reader(db => db.passwords.get(username).contains(password))

    def checkLogin(
                    userId: Int,
                    password: String): DBReader[Boolean] =
      for{
        username <- findUsername(userId)
        usernameOk <- username.map(checkPassword(_,password)).getOrElse(false.pure[DBReader])
      } yield usernameOk

}

