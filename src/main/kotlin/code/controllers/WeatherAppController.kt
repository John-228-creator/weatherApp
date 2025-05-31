package code.controllers

import code.interfaces.BlackListInterface
import code.interfaces.WeatherForecastInterface
import code.pojo.WeatherForecastData
import code.services.TimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*


@Controller
class WeatherAppController {

    @Autowired
    private lateinit var weatherForecastService: WeatherForecastInterface

    @Autowired
    private lateinit var blackListService: BlackListInterface

    @Autowired
    private lateinit var timeService: TimeService

    // la apelarea "/secured_countries", se afiseaza tarile blocate

    @RequestMapping("/secured_countries", method = [RequestMethod.GET])
    @ResponseBody
    fun getBlacklistLocations() : Array<String> {
        return blackListService.getBlacklistCountries()
    }


    @RequestMapping("/getforecast/{location}", method = [RequestMethod.GET])
    @ResponseBody
    fun getForecast(@PathVariable location: String): String {

        // daca se gasesc tari din blacklist-ul furnizat, se returneaza mesaj de eroare
        val blacklistCountries = blackListService.getBlacklistCountries()

        if(location.lowercase( Locale.getDefault() ) in blacklistCountries)
        {
            return "Datele pentru aceasta regiune sunt blocate de catre server."
        }

        // daca nu, se creeaza obiectul de tip WeatherForecastData, cu datele din API, pe baza locatiei din URL.
        val rawForecastData: WeatherForecastData = weatherForecastService.getForecastData(location)

        // se completeaza obiectul cu data curenta
        rawForecastData.date = timeService.getCurrentTime()

        // fiind obiect POJO, funcţia toString() este suprascrisă pentru o afişare mai prietenoasă
        return rawForecastData.toString()
    }
}