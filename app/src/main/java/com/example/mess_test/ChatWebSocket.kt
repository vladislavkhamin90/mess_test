import com.example.mess_test.model.Message
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocket {

    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket
    private val gson = Gson()

    fun connect(token: String, onMessage: (Message) -> Unit) {

        val request = Request.Builder()
            .url("wss://to-do-0bm7.onrender.com/ws/chat?token=$token")
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {
                override fun onMessage(ws: WebSocket, text: String) {
                    onMessage(gson.fromJson(text, Message::class.java))
                }
            }
        )
    }

    fun send(message: Message) {
        webSocket.send(Gson().toJson(message))
    }

    fun close() {
        webSocket.close(1000, null)
    }
}
