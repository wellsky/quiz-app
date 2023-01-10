package quiz.app.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    MainScreen(
        questionText = state.questionText,
        answers = state.answers,
        onAnswerClick = viewModel::onAnswerClick,
        onNextQuestionClick = viewModel::nextQuestion,
        error = state.error,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    questionText: String,
    answers: List<AnswerViewState>,
    onAnswerClick: (Long) -> Unit,
    onNextQuestionClick: () -> Unit,
    error: Boolean,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = questionText,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            answers.forEach {
                Answer(text = it.text, onClick = { onAnswerClick(it.id) } )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNextQuestionClick,
            ) {
                Text("Следуюший вопрос")
            }

        }

        LaunchedEffect(key1 = error) {
            if (error) {
                snackbarHostState.showSnackbar(
                    message = "Ошибка при обращении к серверу",
                )
            }
        }
    }
}

@Composable
private fun Answer(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}
