package io.github.boguszpawlowski.composecalendar.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Stable
public data class DayEvent(
  val day: LocalDate,
  val eventCountList: List<Int>
)

@Stable
public class EventState(
  initialEventList: List<DayEvent>
) {
  private var _eventList by mutableStateOf(initialEventList)

  public var eventList: List<DayEvent>
    get() = _eventList
    set(value) {
      if (value != eventList) {
        _eventList = List(value.size) { value[it] }
      }
    }

  public fun getEventsByDate(date: LocalDate): List<Int> = eventList.firstOrNull{ it.day == date }?.eventCountList ?: listOf()

  public companion object {
    @Suppress("FunctionName", "UNCHECKED_CAST")
    // Factory function
    public fun Saver(): Saver<EventState, Any> = Saver(
        save = {
          var saveString = ""
          it.eventList.forEach { event ->
            saveString += "${event.day}\t${event.eventCountList}\n"
          }
          if (saveString.isNotEmpty()) saveString = saveString.dropLast(1)
          saveString
        },
        restore = { restored ->
          val eventList = (restored as? String).orEmpty().lines()
          val dayEvent = eventList.map { event ->
            val eventL = event.replace('\t', '\n').lines()
            DayEvent(
              day = LocalDate.parse(eventL[0]),
              eventCountList = eventL[1].lines().map { eventType -> eventType.toInt() }
            )
          }
          EventState(
            initialEventList = dayEvent
          )
        }
      )
  }
}






//@Suppress("FunctionName") // Factory function
//public fun EventState(initialEventList: List<DayEvent>): EventState = EventStateImpl(initialEventList)
//
//@Stable
//public interface EventState {
//  public var eventList: List<DayEvent>
//
//  public companion object {
//    @Suppress("FunctionName") // Factory function
//    public fun Saver(): Saver<EventState, String> = Saver(
//      save = { it.toString() },
//      restore = { EventState(emptyList()) } // correct it in future
//    )
//  }
//}
//
//@Stable
//private class EventStateImpl(
//  initialEventList: List<DayEvent>,
//) : EventState {
//
//  private val _currentEventList = mutableStateListOf<DayEvent>()
//
//  init {
//    _currentEventList.clear()
//    _currentEventList.addAll(initialEventList)
//  }
//
//  override var eventList: List<DayEvent>
//    get() = _currentEventList
//    set(value) {
//      _currentEventList.clear()
//      _currentEventList.addAll(value)
//    }
//}
//