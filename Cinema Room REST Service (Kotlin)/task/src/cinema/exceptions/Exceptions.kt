package cinema.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class CustomErrorMessage(val error: String)

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(NotAvailableException::class)
    fun handleNotAvailable(e: NotAvailableException): ResponseEntity<CustomErrorMessage> {
        val body = CustomErrorMessage(e.error)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(OutOfBoundsException::class)
    fun handleOutOfBounds(e: OutOfBoundsException): ResponseEntity<CustomErrorMessage> {
        val body = CustomErrorMessage(e.error)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WrongTokenException::class)
    fun handleWrongToken(e: WrongTokenException) : ResponseEntity<CustomErrorMessage> {
        val body = CustomErrorMessage(e.error)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(WrongPassword::class)
    fun handleWrongPassword(e: WrongPassword) : ResponseEntity<CustomErrorMessage> {
        val body = CustomErrorMessage(e.error)
        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }
}

class OutOfBoundsException(val error: String) : RuntimeException(error)

class NotAvailableException(val error: String) : RuntimeException(error)

class WrongTokenException(val error: String) : RuntimeException(error)

class WrongPassword(val error: String) : RuntimeException(error)
