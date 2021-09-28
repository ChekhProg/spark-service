package com.chekh.sparkservice.Metric

import com.chekh.sparkservice.DataQuality.QualityError
import com.chekh.sparkservice.Person.Person

import java.time.Instant

case class Metric(id: Long, status: Boolean, error: String, time: Instant)

object Metric {
  def apply(value: Either[QualityError, Person], id: Int): Metric = {
    value match {
      case Left(error) => Metric(id, false, error.toString, Instant.now())
      case Right(person) => Metric(id, true, "", Instant.now())
    }
  }
}

