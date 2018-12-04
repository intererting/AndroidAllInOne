package com.lqd.commonimp.extend

import android.util.Log
import com.lqd.commonimp.R
import com.lqd.commonimp.client.BaseApplication
import com.lqd.commonimp.client.SP_RESOURCES_URL
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

///********************************格式化************************///
/**
 * 你没有选择
 */
fun String?.formatStringWithNoSelect(): String {
    return if (this.isNullOrBlank()) "" else String.format(R.string.no_select_temp.getString(), this)
}

/**
 * 你没有输入
 */
fun String?.formatStringWithNoInput(): String {
    return if (this.isNullOrBlank()) "" else String.format(R.string.no_input_temp.getString(), this)
}

/**
 * 格式化时间
 */
@Synchronized
fun Any?.formatDateWithPattern(pattern: String): String = SimpleDateFormat(pattern, Locale.CHINA).format(this)

/**
 * 解析时间
 */
@Synchronized
fun String?.parseDateWithPattern(pattern: String): Date = SimpleDateFormat(pattern, Locale.CHINA).parse(this)

/**
 * 隐藏用户名
 */
fun String?.hideUserName(): String {
    if (this.isNullOrBlank()) {
        return "***"
    } else {
        if (this.length == 1) {
            return "${this}***"
        } else {
            return "${this.first()}***${this.last()}"
        }
    }
}

/**
 * 格式化成Double
 */
fun String?.parseDouble(): Double {
    if (this.isNullOrBlank()) {
        return 0.0
    }
    return if (this.isNumber()) this.toDouble() else 0.0
}

/**
 * 格式化成Float
 */
fun String?.parseFloat(): Float {
    if (this.isNullOrBlank()) {
        return 0f
    }
    return if (this.isNumber()) this.toFloat() else 0f
}

/**
 * 格式化成Int
 */
fun String?.parseInt(): Int {
    if (this.isNullOrBlank()) {
        return 0
    }
    return if (this.isNumber()) this.toInt() else 0
}

/**
 * 格式化成Long
 */
fun String?.parseLong(): Long {
    if (this.isNullOrBlank()) {
        return 0
    }
    return if (this.isNumber()) this.toLong() else 0
}

/**
 *格式化电话号码
 */
fun String?.formateUserPhone(): String {
    if (this.isNullOrBlank() || !this.isMobileNO()) {
        return "**"
    }
    return "${this.substring(0, 3)}****${this.substring(this.length - 4)}"
}

/**
 * 格式化Url
 */
fun String?.urlFormat(spName: String? = null, spResourceName: String? = SP_RESOURCES_URL): String {
    if (this.isNullOrBlank()) {
        return ""
    }
    if (this.startsWith("http://") || this.startsWith("https://")) {
        return this
    }
    return "${BaseApplication.provideInstance().getSharedPreferences(spName).getString(spResourceName, "")}${this}"
}

///********************************运算************************///

/**
 * 除法运算
 */
fun String?.divide(number: String?): Double {
    if (this.isNullOrBlank() || number.isNullOrBlank()) {
        return 0.0
    }
    return BigDecimal(this).divide(BigDecimal(number), 1, RoundingMode.HALF_UP).toDouble()
}

/**
 * MD5加密
 */
fun String?.md5Encode32(): String {
    if (this.isNullOrBlank()) {
        return ""
    }
    val md5: MessageDigest?
    try {
        md5 = MessageDigest.getInstance("MD5")
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }

    val charArray = this.toCharArray()
    val byteArray = ByteArray(charArray.size)

    for (i in charArray.indices)
        byteArray[i] = charArray[i].toByte()

    val md5Bytes = md5!!.digest(byteArray)

    val hexValue = StringBuilder()

    for (md5Byte in md5Bytes) {
        val result = md5Byte.toInt() and 0xff
        if (result < 16) hexValue.append("0")
        hexValue.append(Integer.toHexString(result))
    }
    return hexValue.toString()
}

///********************************格式验证************************///

fun String?.isEmail(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    val str = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    return Pattern.compile(str).matcher(this).matches()
}

fun String?.isMobileNO(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    val p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[013,5-8])|(18[0-9])|(19[8,9]))\\d{8}$")
    return p.matcher(this).matches()
}

val String?.isMobileNum: Boolean
    get() {
        if (this.isNullOrBlank()) {
            return false
        }
        val p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[013,5-8])|(18[0-9])|(19[8,9]))\\d{8}$")
        return p.matcher(this).matches()
    }

/**
 * 是否是数字
 */
fun String?.isNumber(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    val pattern = java.util.regex.Pattern.compile("^-?(([1-9](\\d*))|0)(\\.(\\d+))?$")
    return pattern.matcher(this).matches()
}

fun String?.isPwd(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    return Pattern.compile("^[0-9a-zA-Z]{6,18}$").matcher(this).matches()
}

/**
 * 验证是否是密码格式数据 6-18位 字母和数字都必须包含
 */
fun String?.isPwdTypeB(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    val pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$")
    val match = pattern.matcher(this)
    return match.matches()
}

/**
 * 验证字母
 *
 * @return 如果是符合格式的字符串,返回 **true **,否则为 **false **
 */
fun String.isLetter(): Boolean {
    val regex = Regex("^[A-Z]+", RegexOption.IGNORE_CASE) //忽略大小写
    return this.matches(regex)
}

fun String?.timestamp2DateStr(pattern: String = "yyyy-MM-dd"): String {
    if (this == null) return ""
    return try {
        Date(this.toLong()).formatDateWithPattern(pattern)
    } catch (e: Error) {
        Log.e("timestamp2DateStr", e.message)
        ""
    }
}