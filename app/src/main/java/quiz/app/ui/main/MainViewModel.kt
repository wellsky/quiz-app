package quiz.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import quiz.app.data.QuizService

class MainViewModel(
    private val service: QuizService = QuizService()
) : ViewModel() {
    private val _state = MutableStateFlow(
        MainViewState(
            questionId = 0,
            questionText = "Text from viewModel",
            answers = emptyList(),
        )
    )

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        nextQuestion()
    }

    fun onDismissDialog() {
        _state.value = _state.value.copy(
            dialogSuccess = null,
        )
    }

    fun onAnswerClick(answerId: Long) {
        viewModelScope.launch {
            try {
                val answerResult = service.answer(
                    questionId = _state.value.questionId,
                    answerId = answerId,
                )

                if (answerResult.correct) {
                    _state.value = _state.value.copy(
                        dialogSuccess = true,
                    )
                } else {
                    _state.value = _state.value.copy(
                        dialogSuccess = false,
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = true,
                )
            }
        }

    }

    fun nextQuestion() {
        viewModelScope.launch {
            try {
                val question = service.getRandomQuestion()

                _state.value = MainViewState(
                    questionId = question.id,
                    questionText = question.text,
                    answers = question.answers.map {
                        AnswerViewState(
                            id = it.id,
                            text = it.text,
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = true,
                )
            }
        }
    }
}