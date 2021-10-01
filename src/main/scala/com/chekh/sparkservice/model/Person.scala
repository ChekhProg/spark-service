package com.chekh.sparkservice.model

import com.chekh.sparkservice.dataquality.QualityError

import java.util.logging.Logger

case class Person(name: String, age: Int)

object Person {
  import com.chekh.sparkservice.dataquality.DataQuality._
  import com.chekh.sparkservice.dataquality.QualityChecks._

  def fromStrings(inName: String, inAge: Int): Either[QualityError, Person] = {
    val log    = Logger.getLogger("DataQuality")
    val name   = getValue(Right(inName), List(isLetters, startsWithCapital))
    val age    = getValue(Right(inAge), List(isPositiveInt))
    val person = for { x <- name; y <- age } yield Person(x, y)
    person
  }
}
