package com.kodeco.android.devscribe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme

@Composable
fun UpdateNoteLocationView(
    onNoteLocationChange: (String) -> Unit,
    previousOption: String = ""
) {
    val previousOptions = listOf(previousOption)
    val options = listOf("Room Database")
    var selectedOption by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Select note location"
        )

        options.forEach {
            Row(
                modifier = Modifier
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedOption == it || previousOptions.contains(it)),
                    onClick = {
                        selectedOption = it
                        onNoteLocationChange(it)
                    }
                )
                Text(text = it)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UpdateNoteLocationViewPreview() {
    DevScribeTheme {
        UpdateNoteLocationView(
            onNoteLocationChange = { }
        )
    }
}