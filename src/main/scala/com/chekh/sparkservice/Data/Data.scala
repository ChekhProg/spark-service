package com.chekh.sparkservice.Data

import com.chekh.sparkservice.DataQuality.QualityError
import com.chekh.sparkservice.Metric.Metric
import com.chekh.sparkservice.Person.Person

case class Data (person: Option[Person], metric: Metric)

object Data {
  def apply(value: Either[QualityError, Person], id: Int): Data = {
    Data(value.toOption, Metric(value, id))
  }
}