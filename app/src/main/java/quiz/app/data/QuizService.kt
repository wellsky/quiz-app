package quiz.app.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class QuizService {
    suspend fun getQuestion() {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(ServerAddress + GetQuestionPath)
        println("Response:")
        println(response)
    }
}

private const val ServerAddress = "http://192.168.10.3:8080"

private const val GetQuestionPath = "/question"