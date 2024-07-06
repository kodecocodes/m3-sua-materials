package com.kodeco.android.devscribe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodeco.android.devscribe.R

@Composable
fun SpinnerView(
    onPrioritySelected: (String) -> Unit,
    previousSelectedItem: String  = "",
    isEditNote: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val priorities = listOf("High", "Medium", "Low")
    var selectedIndex by remember {
        mutableIntStateOf(0 )}


    var selectedValueLabel by remember { mutableStateOf(priorities[selectedIndex]) }

    LaunchedEffect(previousSelectedItem) {
        if (isEditNote) {
            selectedIndex = priorities.indexOf(previousSelectedItem)
            selectedValueLabel = previousSelectedItem
        } else {
            selectedIndex = 0
            expanded = false
            selectedValueLabel = priorities[selectedIndex]
            onPrioritySelected(selectedValueLabel)
        }
    }

    Column {
        Text(
            text = "Select Priority"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .wrapContentSize(Alignment.TopStart)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = true }),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = selectedValueLabel,
                    fontSize = 16.sp,
                    color = if (selectedIndex == 0) Color.Gray else Color.Black,
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.cd_drop_down_icon)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                priorities.forEachIndexed { index, value ->
                    DropdownMenuItem(
                        text = {
                            Text(text = value, fontSize = 16.sp)
                        },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                            selectedValueLabel = value
                            onPrioritySelected(value)
                        },
                    )
                }
            }
        }
    }
}