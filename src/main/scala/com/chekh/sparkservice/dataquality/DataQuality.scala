package com.chekh.sparkservice.dataquality

import scala.annotation.tailrec

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
