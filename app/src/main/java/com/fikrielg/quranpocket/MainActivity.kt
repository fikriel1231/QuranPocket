package com.fikrielg.quranpocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fikrielg.quranpocket.ui.screen.GlobalViewModel
import com.fikrielg.quranpocket.ui.screen.NavGraphs
import com.fikrielg.quranpocket.ui.theme.QuranPocketTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuranPocketTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(viewModel<GlobalViewModel>(this@MainActivity))
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuranPocketTheme {
        Greeting("Android")
    }
}