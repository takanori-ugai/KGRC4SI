import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Serializable
data class OpenAIResponse(
    val id: String,
    val `object`: String,
    val created: Int,
    val model: String,
    val choices: List<OpenAIChoice>,
    val usage: OpenAIUsage? = null,
)

@Serializable
data class OpenAIChoice(
    val text: String,
    val index: Int,
    val logprobs: OpenAILogProbs? = null,
    val finish_reason: String? = null,
)

@Serializable
data class OpenAILogProbs(
    val tokens: List<String>,
    val token_logprobs: List<Double>,
    val top_logprobs: List<Double>,
    val text_offset: List<Int>,
    val text: String,
)

@Serializable
data class OpenAIUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int,
)

fun main() {
    val endpoint = "https://api.openai.com/v1/engines/davinci/completions"
    val apiKey = "PUT YOUR API KEY HERE"

    // HTTPクライアントを作成
    val httpClient = HttpClient.newHttpClient()

    // HTTPリクエストに送信するデータを作成
    val requestBody =
        """
        {
            "prompt": "Finding some food is risky for older people because it is to do something to objects in a high place. What is a safer way to achieve their objectives?",
            "max_tokens": 500,
            "temperature": 0.5,
            "n": 1,
            "stop": "."
        }
        """.trimIndent()

    // HTTPリクエストを作成
    val request =
        HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $apiKey")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

    // HTTPリクエストを実行して、HTTPレスポンスを受信
    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    // HTTPレスポンスのステータスコードを確認
    if (response.statusCode() == 200) {
        // HTTPレスポンスのボディを取得
        val responseBody = response.body()
        println(responseBody.toString())

        // HTTPレスポンスのボディをKotlinのオブジェクトに変換
        val openaiResponse = Json.decodeFromString<OpenAIResponse>(responseBody)

        // OpenAIの応答を表示
        println(openaiResponse.choices[0].text)
    } else {
        println("HTTP error: ${response.statusCode()}")
    }
}

class OpenAi
