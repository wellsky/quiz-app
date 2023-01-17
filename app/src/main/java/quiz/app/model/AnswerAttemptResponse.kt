package quiz.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerAttemptResponse (
    @SerialName("question_id")
    val questionId: Long,
    @SerialName("answer_id")
    val answerId: Long,
    @SerialName("correct")
    val correct: Boolean,
)