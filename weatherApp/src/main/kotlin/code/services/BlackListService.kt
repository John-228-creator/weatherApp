package code.services

import code.interfaces.BlackListInterface
import org.springframework.stereotype.Service
import org.springframework.core.io.ClassPathResource
import org.json.JSONObject

@Service
class BlackListService: BlackListInterface {
    override fun getBlacklistCountries(): Array<String> {
        val inputStream = ClassPathResource("blacklist.json").inputStream
        val jsonText = inputStream.bufferedReader().use { it.readText() }
        val arr = JSONObject(jsonText).getJSONArray("blacklisted_regions")
        return Array(arr.length()) { arr.getString(it) }
    }
}