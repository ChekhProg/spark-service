package com.chekh.sparkservice.dataquality

case class QualityError(message: String) extends Exception(message)
