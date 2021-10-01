package com.chekh.sparkservice.model

import com.chekh.sparkservice.dataquality.QualityError

case class PersonDqData(person: Option[Person], metric: Metric)

object PersonDqData {
  def apply(value: Either[QualityError, Person], id: Int): PersonDqData = {
    PersonDqData(value.toOption, Metric(value, id))
  }
}