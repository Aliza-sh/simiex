package com.aliza.simiex.utils.net

import com.parse.ParseException

open class ParseResponse<T>(private val parseResult: T?, private val parseError: ParseException?) {

    fun generateResponse(): ParseRequest<T> {
        return when {
            parseError != null -> {
                when (parseError.code) {
                    ParseException.OTHER_CAUSE -> ParseRequest.Error("یک خطای ناشناخته رخ داده است.")
                    ParseException.CONNECTION_FAILED -> ParseRequest.Error("اتصال به سرورهای Parse ناموفق بود.")
                    ParseException.OBJECT_NOT_FOUND -> ParseRequest.Error("شماره یا رمزعبور اشتباه است.")
                    ParseException.INVALID_QUERY -> ParseRequest.Error("نوع داده مورد استفاده در کوئری نامعتبر است.")
                    ParseException.INVALID_CLASS_NAME -> ParseRequest.Error("کلاس نامعتبر است.")
                    ParseException.MISSING_OBJECT_ID -> ParseRequest.Error("شناسه شیء مشخص نشده است.")
                    ParseException.INVALID_KEY_NAME -> ParseRequest.Error("نام کلید نامعتبر است.")
                    ParseException.INVALID_POINTER -> ParseRequest.Error("اشاره‌گر نامعتبر است.")
                    ParseException.INVALID_JSON -> ParseRequest.Error("JSON دریافت شده نادرست است.")
                    ParseException.COMMAND_UNAVAILABLE-> ParseRequest.Error("دستور در دسترس نیست.")
                    ParseException.NOT_INITIALIZED -> ParseRequest.Error("لطفاً ابتدا Parse.initialize را فراخوانی کنید.")
                    ParseException.INCORRECT_TYPE -> ParseRequest.Error("نوع داده‌ای تنظیم شده ناسازگار است.")
                    ParseException.INVALID_CHANNEL_NAME -> ParseRequest.Error("نام کانال نامعتبر است.")
                    ParseException.PUSH_MISCONFIGURED -> ParseRequest.Error("پیکربندی Push نادرست است.")
                    ParseException.OBJECT_TOO_LARGE -> ParseRequest.Error("اندازه شیء بیش از حد بزرگ است.")
                    ParseException.OPERATION_FORBIDDEN -> ParseRequest.Error("این عملیات برای کلاینت‌ها مجاز نیست.")
                    ParseException.CACHE_MISS -> ParseRequest.Error("نتیجه در کش یافت نشد.")
                    ParseException.INVALID_NESTED_KEY -> ParseRequest.Error("کلید تو در تو نامعتبر است.")
                    ParseException.INVALID_FILE_NAME -> ParseRequest.Error("نام فایل نامعتبر است.")
                    ParseException.INVALID_ACL -> ParseRequest.Error("ACL نامعتبر است.")
                    ParseException.TIMEOUT -> ParseRequest.Error("درخواست تایم اوت شد.")
                    ParseException.INVALID_EMAIL_ADDRESS -> ParseRequest.Error("آدرس ایمیل نامعتبر است.")
                    ParseException.MISSING_REQUIRED_FIELD_ERROR -> ParseRequest.Error("فیلد ضروری وجود ندارد.")
                    ParseException.DUPLICATE_VALUE -> ParseRequest.Error("مقدار فیلد یکتا تکراری است.")
                    ParseException.INVALID_ROLE_NAME -> ParseRequest.Error("نام نقش نامعتبر است.")
                    ParseException.EXCEEDED_QUOTA -> ParseRequest.Error("سهمیه برنامه بیش از حد شده است.")
                    ParseException.SCRIPT_ERROR -> ParseRequest.Error("اجرای اسکریپت کلاود کد ناموفق بود.")
                    ParseException.VALIDATION_ERROR -> ParseRequest.Error("اعتبارسنجی کلاود کد ناموفق بود.")
                    ParseException.FILE_DELETE_ERROR -> ParseRequest.Error("حذف فایل ناموفق بود.")
                    ParseException.REQUEST_LIMIT_EXCEEDED -> ParseRequest.Error("حد درخواست‌ها بیش از حد شده است.")
                    ParseException.INVALID_EVENT_NAME -> ParseRequest.Error("نام رویداد نامعتبر است.")
                    ParseException.USERNAME_MISSING -> ParseRequest.Error("نام کاربری وارد نشده است.")
                    ParseException.PASSWORD_MISSING -> ParseRequest.Error("پسورد وارد نشده است.")
                    ParseException.USERNAME_TAKEN -> ParseRequest.Error("نام کاربری قبلاً استفاده شده است.")
                    ParseException.EMAIL_TAKEN -> ParseRequest.Error("ایمیل قبلاً استفاده شده است.")
                    ParseException.EMAIL_MISSING -> ParseRequest.Error("ایمیل وارد نشده است.")
                    ParseException.EMAIL_NOT_FOUND -> ParseRequest.Error("کاربری با این ایمیل پیدا نشد.")
                    ParseException.SESSION_MISSING -> ParseRequest.Error("توکن جلسه وارد نشده است.")
                    ParseException.MUST_CREATE_USER_THROUGH_SIGNUP -> ParseRequest.Error("فقط از طریق ثبت‌نام می‌توانید کاربر ایجاد کنید.")
                    ParseException.ACCOUNT_ALREADY_LINKED -> ParseRequest.Error("حساب کاربری موردنظر قبلاً لینک شده است.")
                    ParseException.INVALID_SESSION_TOKEN -> ParseRequest.Error("توکن جلسه نامعتبر است.")
                    ParseException.LINKED_ID_MISSING -> ParseRequest.Error("شناسه لینک شده وارد نشده است.")
                    ParseException.INVALID_LINKED_SESSION -> ParseRequest.Error("جلسه لینک شده نامعتبر است.")
                    ParseException.UNSUPPORTED_SERVICE -> ParseRequest.Error("سرویس لینک شده پشتیبانی نمی‌شود.")
                    else -> ParseRequest.Error(parseError.localizedMessage ?: "یک خطای ناشناخته رخ داد.")

                }
            }
            parseResult != null -> ParseRequest.Success(parseResult)
            else -> ParseRequest.Error("Unknown error")
        }
    }
}