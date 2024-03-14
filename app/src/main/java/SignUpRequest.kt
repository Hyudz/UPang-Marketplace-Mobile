data class SignUpRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val user_type: String,
    val gender: String,
    val birthdate: String,
    val address : String,
    val contactNo : String

)
