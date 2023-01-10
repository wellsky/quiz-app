package quiz.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer (
    val id: Long,
    val questionId: Long,
    val text: String,
)