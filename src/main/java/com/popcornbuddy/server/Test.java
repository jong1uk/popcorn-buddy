package main.java.com.popcornbuddy.server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;

public class Test {


    public static void main(String[] args) {
        String SERVICE_KEY = "&dcd9b945316b40ebcd705d121d5d0d49";
        String SERVICE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";

        //JSON 결과값 저장할 변수
        String result = "";
        try{
//            URL객체 생성
            URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?&key=dcd9b945316b40ebcd705d121d5d0d49&targetDt=20240318");
            //URL url = new URL(SERVICE_URL+SERVICE_KEY+"&targetDt=20240318");

////            원본데이터 가져오기, 데이터를 모두 버퍼에 저장하여 하나의 데이터로 만든다. -> Scanner 보다 빠른 속도로 데이터 처리 가능
//              URL에서 제공되는 메소드인 openStream()사용하기 위해 InputStreamReader를 속성으로 사용
//            읽어온 Buffer 데이터를 readLine() 메서드를 사용해서 한줄씩 읽어 result 변수에 저장
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8" ));
            result = bf.readLine();
//            System.out.println(result);// ->정리되지 않은 JSON 데이터를 불러옴

            //result 에 있는 값은 String 이므로 Json으로 인식하도록 바꾸기 위해 Json 라이브러리 사용
            /*JSONParser를 사용해서 String 값을 Json 객체로 변경
             * 만들어진 JSON객체는 JSONObject클래스를 사용해서 저장
             * 만들어진 JSONObject에서 key가 movieInfoResult 인 value를 추출하기 위해 get을 사용
             *
             * */
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject boxOfficeResult = (JSONObject) jsonObject.get("boxOfficeResult");
//            System.out.println(boxOfficeResult);

            JSONArray movieList = (JSONArray) boxOfficeResult.get("dailyBoxOfficeList");
            System.out.println(movieList);
            JSONObject movie = (JSONObject) movieList.get(0);
            System.out.println(movie);
//            JSONArray dailyBoxOfficeList = boxOfficeResult.

            //get()을 사용하여 출력
            //key와 value 안에 Jsom은 JsonArray를 사용해서 내부 데이터를 Array
            //형태로 바꿔주고, 바뀐 Array 안의 index마다 Jsonobject로 변환
//            System.out.println("영화 순위: " +movies_movieNm.get("rank"));
//            System.out.println("영화 이름: " +movies_movieNm.get("movieNm"));
//            System.out.println("개봉일: " +movies_movieNm.get("openDt"));

        }catch (Exception e){
            e.printStackTrace();;
        }




    }

}