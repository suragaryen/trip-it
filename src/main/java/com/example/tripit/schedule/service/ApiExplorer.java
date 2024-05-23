package com.example.tripit.schedule.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class ApiExplorer {

    public ResponseEntity<Object> apiTest(String metroId) throws IOException { //throws IOException -> IO 작업 중 발생할 수 있는 예외를 던진다
        String urlEndPoint = "https://apis.data.go.kr/B551011/KorService1/";
        String apiKey = "2Yq147AHzw7RELqQbw8mBFIO24qYRSmJDPNo6U6tbgdKEZbEG5Jeo14JXirYpgzfN6n7%2Bf0NO016YigMyNSTWQ%3D%3D";
        int numOfRows = 8;
        int pageNo = 1;
        String lastUrl = "&MobileOS=ETC&MobileApp=AppTest&arrange=A";
        String areaCode = "&areaCode=";
        String type = "&_type=json";

        String apiUrl = urlEndPoint + "areaBasedSyncList1?serviceKey=" + apiKey + "&numOfRows=" + numOfRows
                + "&pageNo=" + pageNo + lastUrl + "&areaCode=" + metroId + type;

        URL url = new URL(apiUrl); //apiUrl을 url 객체로 만든다
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //url 객체를 이용해 HTTP 연결을 열음
        connection.setRequestMethod("GET"); //HTTP 요청을 GET으로 설정
        connection.setRequestProperty("Content-type", "application/json"); //요청 헤더에 Content-type을 json으로 설정

        BufferedReader rd; //응답 데이터를 읽기 위한 BufferedReader 객체 선언

        if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300) {  //HTTP 응답 코드를 가져오는데 코드가 200~300 사이면
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream())); //성공으로 InputStream을 사용하여 데이터를 읽는다
        } else {
            rd = new BufferedReader((new InputStreamReader(connection.getErrorStream()))); //실패라면 ErrorStream을 사용하여 오류 메시지를 읽는다
        }

        StringBuilder sb = new StringBuilder(); //응답 데이터를 저장할 StringBuilder 객체를 만든다
        String line; //응답 데이터를 저장할 변수
        while ((line = rd.readLine()) != null) { //응답 데이터를 한줄씩 반복해서 StringBuilder에 저장한다
            sb.append(line);
        }
        rd.close(); //BufferedReader 닫기
        connection.disconnect(); //HTTP 연결 닫기
        System.out.println(sb.toString()); //콘솔 출력
        return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
    }
}