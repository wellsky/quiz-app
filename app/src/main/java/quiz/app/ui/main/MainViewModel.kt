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
            text = "Text from viewModel"
        )
    )

    val state: StateFlow<MainViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val question = service.getQuestion(2)
            _state.value = MainViewState(
                text = question.text
            )
        }
    }
}