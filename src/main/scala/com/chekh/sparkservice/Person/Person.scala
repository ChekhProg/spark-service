package com.chekh.sparkservice.Person

import com.chekh.sparkservice.DataQuality.QualityError

import java.util.logging.Logger

case class Person(name: String, age: Int)

object Person {
  import com.chekh.sparkservice.DataQuality.QualityChecks._
  import com.chekh.sparkservice.DataQuality.DataQuality._

//  def fromStrings(inName: String, inAge: Int): Option[Person] = {
//    val log    = Logger.getLogger("DataQuality")
//    val name   = getValue(Right(inName), List(isLetters, startsWithCapital))
//    val age    = getValue(Right(inAge), List(isPositiveInt))
//    val person = for { x <- name; y <- age } yield Person(x, y)
//    person.toOption match {
//      case None => {
//        log.warning(
//          "Can't create object Person(name, age):\n" +
//            s"name: $name\n" +
//            s"age: $age\n"
//        )
//        None
//      }
//      case Some(value) =>
//        log.info(
//          "Object Person(name, age) created:\n" +
//            s"name: $name\n" +
//            s"age: $age\n"
//        )
//        Some(value)
//    }
//  }

  def fromStrings(inName: String, inAge: Int): Either[QualityError, Person] = {
    val log    = Logger.getLogger("DataQuality")
    val name   = getValue(Right(inName), List(isLetters, startsWithCapital))
    val age    = getValue(Right(inAge), List(isPositiveInt))
    val person = for { x <- name; y <- age } yield Person(x, y)
    person
  }
}
