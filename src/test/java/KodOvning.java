import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class KodOvning {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (var url : List.of("https://en.wikipedia.org/wiki/Main_Page", "https://ja.wikipedia.org/wiki/%E3%83%A1%E3%82%A4%E3%83%B3%E3%83%9A%E3%83%BC%E3%82%B8")) {
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var responseString = response.body();
            System.out.println(url);
            for (var charset : new Charset[]{StandardCharsets.UTF_8, StandardCharsets.UTF_16, Charset.forName("UTF-32")}){
                var length = responseString.getBytes(charset).length;
                System.out.println(charset.name() + ": " + length);
            }
        }
    }
}
