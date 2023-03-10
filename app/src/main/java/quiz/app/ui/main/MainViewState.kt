package quiz.app.ui.main

import androidx.compose.runtime.Immutable

@Immutable
data class MainViewState(
    val questionId: Long,
    val questionText: String = "",
    val answers: List<AnswerViewState>,
    val dialogSuccess: Boolean? = null,
    val error: Boolean = false,
)

@Immutable
data class AnswerViewState (
    val id: Long,
    val text: String,
)