package quiz.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerAttemptRequest (
    @SerialName("question_id")
    val questionId: Long,

    @SerialName("answer_id")
    val answerId: Long,
)