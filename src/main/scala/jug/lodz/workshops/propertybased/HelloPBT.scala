package jug.lodz.workshops.propertybased

/**
  * Created by pawel on 05.03.16.
  */
object HelloPBT {

  object MathLib{
    //Sum of the first n natural numbers -> SUM=n*(n+1)/2
    def sumN(n:Int)=n*(n+1)/2
  }

  object HelloPBTExercises{
    type Kelvin = Double
    type Celsius = Double
    type Fahrenheit = Double

    val kelvinToCelsius: Kelvin=>Celsius= k => k - 273.15
    val celsiusToFahrenheit:Celsius=>Fahrenheit = c => c * 9/5 + 32.0
    val fahrenheitToKelvin :Fahrenheit=>Kelvin = f => (f+459.67) *5/9
  }

}
