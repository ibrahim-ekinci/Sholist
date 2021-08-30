package com.gloorystudio.sholist.data.api

enum class ErrorCode(val code: Int) {
    DEFAULT_100(100),
    INVALID_JSON_400(400),
    INVALID_EMAIL_PW_401(401),
    INVALID_PERMISSION_403(403),
    INVALID_REQUEST_404(404),
    EXIST_DATA_422(422),
    UNCONFIRMED_USERNAME_412(412),
    UNCONFIRMED_MAIL_205(205),
    SERVER_ERROR_500(500);

    companion object {
        fun getErrorCode(code: Int): ErrorCode {
            return when (code) {
                INVALID_JSON_400.code -> INVALID_JSON_400
                INVALID_EMAIL_PW_401.code -> INVALID_EMAIL_PW_401
                INVALID_PERMISSION_403.code -> INVALID_PERMISSION_403
                INVALID_REQUEST_404.code -> INVALID_REQUEST_404
                SERVER_ERROR_500.code -> SERVER_ERROR_500
                EXIST_DATA_422.code -> EXIST_DATA_422
                UNCONFIRMED_MAIL_205.code -> UNCONFIRMED_MAIL_205
                UNCONFIRMED_USERNAME_412.code -> UNCONFIRMED_USERNAME_412
                else -> DEFAULT_100
            }
        }
    }
}