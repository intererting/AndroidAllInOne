import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URLDecoder

object ClientManager {

    @JvmField
    val baseUrl = ""

    @JvmField
    val loggingInterceptor = HttpLoggingInterceptor { message ->
        try {
            Log.w("okhttp", URLDecoder.decode(message, "utf-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}