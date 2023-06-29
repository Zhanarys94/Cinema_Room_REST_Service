package cinema.model

data class PurchaseResponse(val token: String, val ticket: Seat)

data class ReturnResponse(val returned_ticket: Seat)