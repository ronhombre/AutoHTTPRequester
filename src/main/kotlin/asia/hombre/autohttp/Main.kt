/*
* Copyright 2023 Ron Lauren Hombre
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package asia.hombre.autohttp

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date

fun main() {
    val currentDirectory = System.getProperty("user.dir") //Jar location directory

    val options = readFile(currentDirectory + File.separatorChar + "http_options.json")

    val jsonOptions = JsonParser.parseString(options).asJsonObject

    val url = URL(jsonOptions.get("url").asString)
    val method = jsonOptions.get("method").asString.uppercase()
    val headers = jsonOptions.get("headers").asJsonObject
    val data = jsonOptions.get("data").asJsonObject

    val connection = url.openConnection() as HttpURLConnection

    connection.doOutput = true
    connection.requestMethod = method //TODO: Check if valid request method

    for(header in headers.keySet()) {
        connection.setRequestProperty(header, headers.get(header).asString)
    }

    val newData = JsonObject()
    for(d in data.keySet()) {
        val value = data.get(d).asString
        //TODO: Improve and use replaceAll
        if(value.contains("\$ipv4")) {
            val newJsonElement = JsonPrimitive(value.replace("\$ipv4", getIPV4()))
            newData.add(d, newJsonElement)
        } else if(value.contains("\$ipv6")) {
            val newJsonElement = JsonPrimitive(value.replace("\$ipv6", getIPV6()))
            newData.add(d, newJsonElement)
        } else if(value.contains("\$time")) {
            val newJsonElement = JsonPrimitive(value.replace("\$time", getTime()))
            newData.add(d, newJsonElement)
        } else
            newData.add(d, data.get(d))
    }

    connection.outputStream.write(newData.toString().toByteArray())

    //TODO: Customize output
    println(String(connection.inputStream.readAllBytes()))
}

//TODO: Handle IOException?
fun readFile(filename: String): String {
    val fileInputStream = FileInputStream(File(filename))
    return String(fileInputStream.readAllBytes())
}

//TODO: Customizable variables
//TODO: Cache values
fun getIPV4(): String {
    val url = URL("https://checkip.amazonaws.com/")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "GET"

    connection.connect()

    return String(connection.inputStream.readAllBytes())
}

fun getIPV6(): String {
    val url = URL("http://checkipv6.dyndns.com/")
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "GET"

    connection.connect()

    val rawBytes = connection.inputStream.readAllBytes()

    //Removes "<html><head><title>Current IP Check</title></head><body>Current IP Address: " and "</body></html>"
    val trimmedBytes = rawBytes.copyOfRange(76, rawBytes.size - 16)

    return String(trimmedBytes)
}

fun getTime(): String {
    val formatter = SimpleDateFormat("MMMM dd, yyyy HH:mm:ss:SS") //December 21, 2023 21:32:57:92

    return formatter.format(Date())
}