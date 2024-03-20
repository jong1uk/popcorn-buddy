package main.java.com.popcornbuddy.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TmdbApi {

    public static void main(String[] args) throws IOException {
        String apiKey = "2c30821abaa1c963cd182764abf5bcd1";
        String movieTitle = "다크 나이트"; // 검색하고자 하는 영화 이름

        // 영화 이름을 TMDB API에서 검색하기 위한 URL 생성
        String encodedMovieName = URLEncoder.encode(movieTitle, "UTF-8");
        String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + encodedMovieName;

        // URL 연결 및 GET 요청 설정
        URL url = new URL(searchUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답 내용 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // JSON 형식의 응답에서 첫 번째 검색 결과의 영화 ID 추출
            String jsonResponse = response.toString();
            String movieId = jsonResponse.split("\"id\":")[1].split(",")[0];

            // 영화 ID를 사용하여 포스터 이미지 URL을 가져오는 함수 호출
            String posterUrl = getPosterUrl(apiKey, movieId);
            if (posterUrl != null) {
                System.out.println("Poster URL: " + posterUrl);
            } else {
                System.out.println("Failed to get poster URL.");
            }
        } else {
            System.out.println("Failed to get response from TMDB API. Response code: " + responseCode);
        }

        // 연결 해제
        conn.disconnect();
    }

    // 영화 ID를 사용하여 포스터 이미지 URL을 가져오는 함수
    private static String getPosterUrl(String apiKey, String movieId) throws IOException {
        // API 호출을 위한 URL 생성
        String posterUrl = null;
        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;

        // URL 연결 및 GET 요청 설정
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답 내용 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // JSON 형식의 응답에서 포스터 이미지 URL 추출
            String jsonResponse = response.toString();
            posterUrl = "https://image.tmdb.org/t/p/original" + jsonResponse.split("\"poster_path\":\"")[1].split("\"")[0];
        }

        // 연결 해제
        conn.disconnect();

        return posterUrl; // 영화 포스터 url
    }
}
