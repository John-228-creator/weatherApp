package code.interfaces

import code.pojo.WeatherForecastData

interface WeatherForecastInterface {
    fun getForecastData(location: String): WeatherForecastData
}