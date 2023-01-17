package quiz.app.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    MainScreen(
        state = state,
        onAnswerClick = viewModel::onAnswerClick,
        onNextQuestionClick = viewModel::nextQuestion,
        onDismissDialog = viewModel::onDismissDialog,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreen(
    state: MainViewState,
    onAnswerClick: (Long) -> Unit,
    onNextQuestionClick: () -> Unit,
    onDismissDialog: () -> Unit,
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
                text = state.questionText,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            state.answers.forEach {
                Answer(text = it.text, onClick = { onAnswerClick(it.id) })
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNextQuestionClick,
            ) {
                Text("Следуюший вопрос")
            }

        }

        LaunchedEffect(key1 = state.error) {
            if (state.error) {
                snackbarHostState.showSnackbar(
                    message = "Ошибка при обращении к серверу",
                )
            }
        }

        if (state.dialogSuccess != null) {
            AlertDialog(
                onDismissRequest = onDismissDialog,
                title = {
                    Text(
                        text = "Dialog",
                    )
                },
                text = {
                    Text(
                        text = "Dialog text",
                    )
                },
                confirmButton = {
                    TextButton(
                        content = {
                            Text(
                                text = "Confirm",
                            )
                        },
                        onClick = onDismissDialog,
                    )
                },
            )
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
