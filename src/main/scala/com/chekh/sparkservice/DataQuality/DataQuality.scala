package com.chekh.sparkservice.DataQuality

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object DataQuality {
  @tailrec
  def getValue[T](value: Either[QualityError, T],
                  checks: List[T => Either[QualityError, T]])
                  : Either[QualityError, T] = {
    value match {
      case Left(exception) => Left(exception)
      case Right(value) =>
        checks match {
          case Nil => Right(value)
          case x :: xs => getValue(x(value), xs)
        }
    }
  }
}
