package cinema.controller

import cinema.exceptions.*
import cinema.model.*
import cinema.model.PurchaseResponse
import cinema.model.ReturnResponse
import org.springframework.web.bind.annotation.*
import org.springframework.http.*
import java.util.concurrent.ConcurrentHashMap


@RestController
class CinemaController(private val cinema: CinemaRoom) {

    private val tokensMap = ConcurrentHashMap<String, Seat>()

    @GetMapping("/seats")
    fun info(): CinemaRoom {
        return cinema
    }

    @PostMapping("/purchase")
    fun purchase(@RequestBody seatRequest: SeatRequest): PurchaseResponse {
        if (cinema.isOutOfBounds(seatRequest)) {
            throw OutOfBoundsException("The number of a row or a column is out of bounds!")
        } else if (!cinema.isAvailable(seatRequest)) {
            throw NotAvailableException("The ticket has been already purchased!")
        }
        val seat = cinema.occupySeat(seatRequest)
        val token = Token().token.toString()
        tokensMap[token] = seat
        return PurchaseResponse(token, seat)
    }

    @PostMapping("/return")
    fun returnTicket(@RequestBody tokenRequest: TokenRequest): ReturnResponse {
        if (!tokensMap.containsKey(tokenRequest.token)) throw WrongTokenException("Wrong token!")
        val returnedSeat = tokensMap[tokenRequest.token]!!.also {
            cinema.addToAvailable(it)
            tokensMap.remove(tokenRequest.token)
        }
        return ReturnResponse(returnedSeat)
    }

    @GetMapping("/stats")
    fun statistics(@RequestParam password: String?): Statistics {
        val correctPassword = "super_secret"
        if (password.isNullOrEmpty() || password.isBlank() || password != correctPassword) {
            throw WrongPassword("The password is wrong!")
        }
        return cinema.stats()
    }

}