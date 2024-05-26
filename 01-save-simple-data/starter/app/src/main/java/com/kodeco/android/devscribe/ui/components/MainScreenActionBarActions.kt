package com.kodeco.android.devscribe.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.kodeco.android.devscribe.R

@Composable
fun MainScreenActionBarActions(
  onFilterChange: (String) -> Unit,
  filter: String
) {
  var showFilterManu by remember { mutableStateOf(false) }
  var selectedFilter by remember { mutableStateOf(filter) }
  val previousFilter = listOf(filter)
  val filterOptions = listOf("All", "High", "Medium", "Low")

  IconButton(
    onClick = {
      showFilterManu = !showFilterManu
    },
    content = {
      Icon(
        painter = painterResource(id = R.drawable.baseline_filter_list_24),
        contentDescription = "Filter",
        tint = Color.White
      )
      DropdownMenu(
        expanded = showFilterManu,
        onDismissRequest = {
          showFilterManu = false
        },
        content = {
         repeat(filterOptions.size){ position ->
           DropdownMenuItem(
             text = {
               Row (
                 verticalAlignment = Alignment.CenterVertically
               ){
                 Checkbox(
                   checked = (selectedFilter == filterOptions[position]) || previousFilter.contains(filterOptions[position]),
                   onCheckedChange ={
                     selectedFilter = filterOptions[position]
                     onFilterChange(selectedFilter)
                     showFilterManu = false
                   }
                 )
                 Text(
                   text = filterOptions[position]
                 )
               }
             },
             onClick = {}
           )
         }
        }
      )
    }
  )
}