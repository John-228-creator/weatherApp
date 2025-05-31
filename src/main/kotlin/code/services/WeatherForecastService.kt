package code.services

import code.interfaces.WeatherForecastInterface
import code.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL


@Service
class WeatherForecastService (private val timeService: TimeService) : WeatherForecastInterface {
    override fun getForecastData(location: String): WeatherForecastData {
        // ID-ul locaţiei nu trebuie codificat, deoarece este numeric
        val forecastDataURL = URL("https://www.wttr.in/$location?format=j1")

        // preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()

        // parsare obiect JSON primit
        val weatherDataObject = JSONObject(rawResponse)

        // construire şi returnare obiect POJO care încapsulează datele meteo
        return WeatherForecastData(
            location = weatherDataObject.getJSONArray("nearest_area").getJSONObject(0).getJSONArray("areaName").getJSONObject(0).getString("value"),
            date = "",
            weatherState = weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getJSONArray("weatherDesc").getJSONObject(0).getString("value"),
            weatherStateIconURL = weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"),
            windDirection = weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getString("winddir16Point"),
            windSpeed = Integer.parseInt(weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getString("windspeedKmph")),
            currentTemp = Integer.parseInt(weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getString("temp_C")),
            humidity = Integer.parseInt(weatherDataObject.getJSONArray("current_condition").getJSONObject(0).getString("humidity"))
        )
    }
}
