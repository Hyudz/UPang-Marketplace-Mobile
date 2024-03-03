data class Order(
    val created_at: String,
    val id: Int,
    val order_id: Int,
    val payment_id: Int,
    val status: String,
    val total_amount: String,
    val updated_at: String,
    val user_id: Int
)