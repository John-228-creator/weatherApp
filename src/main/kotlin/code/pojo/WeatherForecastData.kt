package code.pojo

data class WeatherForecastData (
    var location: String,
    var date: String,
    var weatherState: String,
    var weatherStateIconURL: String,
    var windDirection: String,
    var windSpeed: Int, // km/h
    var currentTemp: Int,
    var humidity: Int // procent
)
