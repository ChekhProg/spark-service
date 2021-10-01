package com.chekh.sparkservice.model

import com.chekh.sparkservice.dataquality.QualityError

import java.time.Instant

case class Metric(id: Long, status: Boolean, error: String, time: Instant)

object Metric {
  def apply(value: Either[QualityError, Person], id: Int): Metric = {
    value match {
      case Left(error) => Metric(id, status = false, error.toString, Instant.now())
      case Right(person) => Metric(id, status = true, "", Instant.now())
    }
  }
}

