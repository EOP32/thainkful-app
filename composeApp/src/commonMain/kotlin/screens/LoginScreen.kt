package screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import navigation.HomeComponent

@Composable
fun LoginScreen(component: HomeComponent) {
    Row {
        Text("LOGIN")
        Button(onClick = { component.onEvent() }) {
            Text("Navigate")
        }
    }
}