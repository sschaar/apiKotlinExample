import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.File

// Function to load the token from the text file
fun loadTokenFromFile(filePath: String): String? {
    return try {
        File(filePath).readText().trim()
    } catch (e: Exception) {
        println("Error loading the token: ${e.message}")
        null
    }
}

suspend fun main() {

    val tokenFilePath = "rsc/token.txt"
    val token = loadTokenFromFile(tokenFilePath)

    val city = "Vienna"

    val client = HttpClient(CIO)

    val response: HttpResponse = client.get("http://api.weatherapi.com/v1/current.json?key=$token&q=$city")
    val body: String = response.body()
    val json = Json.decodeFromString<JsonObject>(body)

    // To print the whole JSON-Object
    println(json)

    // To print a specific part of the JSON-Object
    println(json["location"]?.jsonObject!!["name"])

    client.close()
}