package lv.edreams.bdc.core.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
class Location(var latitude: Double = 0d, var longitude: Double = 0d) {
  def this() {
    this(0d, 0d)
  }
}