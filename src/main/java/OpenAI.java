import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class OpenAI {
    private static final String API_URL = "https://api.openai.com/v1/completions";
    private static final String API_HEADER = "Bearer %s";

    private final String apiKey;
    private final OkHttpClient httpClient;

    public OpenAI(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
    }

    public String generateResponse(String prompt) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model","text-davinci-003");
            requestBody.put("prompt", prompt);
            requestBody.put("temperature", 0);
            requestBody.put("max_tokens", 1024);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(requestBody.toString(), mediaType);

            Request request = new Request.Builder()
                    .url(API_URL)
                    //.get()
                    .post(body)
                    .addHeader("Authorization", String.format(API_HEADER, apiKey))
                    .build();

            Response response = httpClient.newCall(request).execute();
            int statusCode = response.code();
            String responseBody = response.body().string();
            response.close();

            if (statusCode >= 200 && statusCode < 300) {
                JSONObject json = new JSONObject(responseBody);
                return json.getJSONArray("choices").getJSONObject(0).getString("text");
            } else {
                System.err.println("Error in API response. Status code: " + statusCode);
                System.err.println("Response body: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
