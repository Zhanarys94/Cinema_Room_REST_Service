package cinema.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.stereotype.Component

@Component
class CinemaRoom {
    val total_rows = 9
    val total_columns = 9
    @JsonIgnore
    val indexMapOfSeats = LinkedHashMap<Seat, Int> ()
    @JsonIgnore
    var income = 0
    val available_seats = mutableListOf<Seat>().apply {
        var index = 0
        for (row in 1..total_rows) {
            for (col in 1..total_columns) {
                val price = if (row <= 4) 10 else 8
                val seat = Seat(row, col, price)
                indexMapOfSeats[seat] = index
                add(seat)
                index++
            }
        }
    }

    fun isAvailable(seatRequest: SeatRequest): Boolean = available_seats
        .find { it.row == seatRequest.row && it.column == seatRequest.column } != null

    fun isOutOfBounds(seatRequest: SeatRequest): Boolean {
        return seatRequest.row < 1 || seatRequest.row > 9 || seatRequest.column < 1 || seatRequest.column > 9
    }

    fun occupySeat(seatRequest: SeatRequest): Seat {
        val seat = available_seats.find { it.row == seatRequest.row && it.column == seatRequest.column }!!
        income += seat.price
        available_seats.remove(seat)
        return seat
    }

    fun addToAvailable(seat: Seat) {
        val index = indexMapOfSeats[seat]!!
        income -= seat.price
        available_seats.add(index, seat)
    }

    fun stats(): Statistics {
        return Statistics(
            income, available_seats.size, total_rows * total_columns - available_seats.size
        )
    }

}