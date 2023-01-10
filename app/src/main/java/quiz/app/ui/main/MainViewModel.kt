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
            questionText = "Text from viewModel",
            answers = emptyList(),
        )
    )

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        nextQuestion()
    }

    fun onAnswerClick(answerId: Long) {
        println("Answer clicked " + answerId)
    }

    fun nextQuestion() {
        viewModelScope.launch {
            try {
                val question = service.getRandomQuestion()

                _state.value = MainViewState(
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