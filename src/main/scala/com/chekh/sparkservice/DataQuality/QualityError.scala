package com.chekh.sparkservice.DataQuality

case class QualityError(message: String) extends Exception(message)
