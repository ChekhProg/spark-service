package com.chekh.sparkservice.model

import com.chekh.sparkservice.dataquality.QualityError

case class Data (person: Option[Person], metric: Metric)

object Data {
  def apply(value: Either[QualityError, Person], id: Int): Data = {
    Data(value.toOption, Metric(value, id))
  }
}