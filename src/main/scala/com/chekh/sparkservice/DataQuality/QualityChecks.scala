package com.chekh.sparkservice.DataQuality

import scala.util.{Failure, Success, Try}

object QualityChecks {
  val isLetters: String => Either[QualityError, String] = (string: String) => {
    if (string.forall(_.isLetter))
      Right(string)
    else
      Left(QualityError("Not all is letter symbols: " + string))
  }
  val startsWithCapital: String => Either[QualityError, String] = (string: String) => {
    if (string.head.isUpper)
      Right(string)
    else
      Left(QualityError("Not starts with capital"))
  }
  val castToInt: String => Either[QualityError, Int] = (string: String) => {
    Try(string.toInt) match {
      case Success(value) => Right(value)
      case Failure(exception) => Left(QualityError("Can't cast to Int"))
    }
  }
  val isPositiveInt: Int => Either[QualityError, Int] = (int: Int) => {
    if (int >= 0)
      Right(int)
    else
      Left(QualityError("Can't be a negative number"))
  }
}
