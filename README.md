# weatherApp
This application is built using Kotlin, the Spring Boot framework, and Maven as the build tool. It retrieves weather data from an external API and exposes it through a RESTful endpoint.
For example, accessing /getforecast/paris returns raw weather data (in JSON format) for Paris.
The application also maintains a blacklist of restricted cities and countries. If a request is made to /getforecast/{location} where the location is on the blacklist, the application returns an error message instead of weather data.
