package ocwebserver

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

import ocwebserver.extensions.formatHTML

fun main(args: Array<String>) {
    val server = embeddedServer(
        Netty,
        port = 8080,
        module = Application::mymodule
        ).apply { start(wait = true) }
}

fun Application.mymodule() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }
    routing {
        get("/") {
            var bienVenue : String = "Welcome to OpenClassrooms brand new server !"
            bienVenue =bienVenue.formatHTML()
            call.respondText(bienVenue, ContentType.Text.Html)
        }
        get("/course/top") {
            call.respond(Cours(1, "How to troll", 5, true))
        }
        get("course/{id}") {

            val num = call.parameters["id"]

            val cours : Any = when(num) {
                "1" -> Cours(1, "How to troll", 5, true)
                "2" -> Cours(2, "Kotlin for troll", 1, true)
                "3" -> Cours(3, "Ktor the troll", 3)
                else -> Message()
            }

            call.respond(cours)
        }
    }
}

data class Cours(val id : Int, val title : String, val level : Int, var isActive : Boolean = false)

class Message(val status : Int = 404, val message : String = "Sorry! No course were found...")


