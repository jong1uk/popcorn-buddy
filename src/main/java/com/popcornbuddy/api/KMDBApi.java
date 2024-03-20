package main.java.com.popcornbuddy.api;

import java.net.URLEncoder;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class KMDBApi {
    public static void main(String[] args) {
        // KMDB 영화 이름으로 검색하기


        try {
            String apiKey = "0KPOCJ0658J72050NE32";
            String movieName = URLEncoder.encode("해리포터", "UTF-8");
//            URL url = new URL("http://api.kmdb.or.kr/v1/search/movie.json?key=" + apiKey + "&title=" + movieName);
            URL url = new URL("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&title="+movieName+"&ServiceKey="+apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject movie = jsonObject.getJSONArray("Data").getJSONObject(0);

            // 원하는 정보 추출
//            String[] plots = movie.getJSONArray("plot");
            String posterUrl = movie.getString("posters"); // 포스터 이미지
            String rating = movie.getString("rating"); // 관람 연령
            String genre = movie.getString("genre"); // 장르
            String runtime = movie.getString("runtime"); // 상영 시간

            // 추출된 정보 사용
//            System.out.println("Plot: " + plot);
            System.out.println("Poster URL: " + posterUrl);
            System.out.println("Rating: " + rating);
            System.out.println("Genre: " + genre);
            System.out.println("Runtime: " + runtime);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}