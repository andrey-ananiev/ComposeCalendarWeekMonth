package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.states.ModeState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.states.DayEvent
import java.time.LocalDate

internal val dayEventList = listOf(
  DayEvent(LocalDate.now(), listOf(3)),
  DayEvent(LocalDate.now().minusDays(3L), listOf(30)),
  DayEvent(LocalDate.now().minusDays(5L), listOf(7)),
  DayEvent(LocalDate.now().minusDays(7L), listOf(6)),
  DayEvent(LocalDate.now().plusDays(3L), listOf(9)),
  DayEvent(LocalDate.now().plusDays(7L), listOf(7)),
)

@Composable
fun SelectableCalendarSample() {

  val calendarState = rememberSelectableCalendarState(
    initialEventList = dayEventList
  )

  Column(
    Modifier.verticalScroll(rememberScrollState())
  ) {
    ModeControls(modeState = calendarState.modeState)
    SelectableCalendar(calendarState = calendarState)
    SelectionControls(selectionState = calendarState.selectionState)
  }
}

@Composable
fun ModeControls(
  modeState: ModeState
) {
  Row(modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = modeState.mode == ModeState.MODE_MONTH,
      onClick = {
        modeState.mode = when (modeState.mode) {
          ModeState.MODE_MONTH -> ModeState.MODE_WEEK
          ModeState.MODE_WEEK -> ModeState.MODE_OFF
          else -> ModeState.MODE_MONTH
        }
      }
    )
    Text(text = "Month mode")
  }
}

@Composable
private fun SelectionControls(
  selectionState: DynamicSelectionState,
) {
  Text(
    text = "Calendar Selection Mode",
    style = MaterialTheme.typography.h5,
  )
  SelectionMode.values().forEach { selectionMode ->
    Row(modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      RadioButton(
        selected = selectionState.selectionMode == selectionMode,
        onClick = { selectionState.selectionMode = selectionMode }
      )
      Text(text = selectionMode.name)
      Spacer(modifier = Modifier.height(4.dp))
    }
  }

  Text(
    text = "Selection: ${selectionState.selection.joinToString { it.toString() }}",
    style = MaterialTheme.typography.h6,
  )
}
