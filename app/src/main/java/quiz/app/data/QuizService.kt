package quiz.app.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import quiz.app.model.Question

class QuizService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getQuestion(id: Long): Question {
        val response: HttpResponse = client.get("$ServerAddress$GetQuestionPath/$id")
        return response.body()
    }

    suspend fun getRandomQuestion(): Question {
        val response: HttpResponse = client.get("$ServerAddress$GetRandomQuestionPath")
        return response.body()
    }
}

private const val ServerAddress = "http://192.168.10.3:8080"

private const val GetQuestionPath = "/question"
private const val GetRandomQuestionPath = "/randomquestion"