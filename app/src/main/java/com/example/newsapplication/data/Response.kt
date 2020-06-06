package com.tracki.data.model.response


import java.io.Serializable

class AppliedByHistory {

    var email: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var mobile: String? = null
    var userId: String? = null
}




enum class LeaveStatus {
    ALL, PENDING, APPROVED, REJECTED, CANCELLED, PRESENT, ABSENT, DAY_OFF, HOLIDAY, ON_LEAVE
}



