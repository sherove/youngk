package com.young.in.youngk.login;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;


    @GetMapping("")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri;
        model.addAttribute("location", location);
        return "login";
    }

    @GetMapping("/oauth")
    public String oauthRedirect(@RequestParam("code") String code, Model model) {
        try {
            String accessToken = getAccessToken(code);
            String userInfo = getUserInfo(accessToken);

            // JSON 파싱 후 사용자 정보를 이용하여 회원가입 또는 로그인 처리
            // 예시: JSON 파싱
            Map<String, Object> userMap = parseUserInfo(userInfo);
            String userId = (String) userMap.get("id");
            String userName = (String) ((Map<String, Object>) userMap.get("properties")).get("nickname");

            model.addAttribute("userId", userId);
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
            model.addAttribute("userName", userName);

            return "loginSuccess"; // 로그인 성공 페이지로 리디렉션
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to login with Kakao");
            return "loginError";
        }
    }

    private Map<String, Object> parseUserInfo(String userInfo) {
        // 간단한 JSON 파싱 예제 (Gson 또는 Jackson 사용을 권장)
        Map<String, Object> userMap = new HashMap<>();
        // 실제로는 JSON 파서를 사용하여 userInfo 문자열을 Map으로 변환합니다.
        // 여기서는 예시로 빈 맵을 반환합니다.
        return userMap;
    }

    private String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("redirect_uri", redirectUri);
        params.put("code", code);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, String.class);

        // JSON 파싱하여 access_token 추출 (간단한 예시, 실제로는 JSON 파싱 필요)
        return extractAccessToken(response.getBody());
    }

    private String getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    private String extractAccessToken(String responseBody) {
        // 간단한 예시로 access_token 값을 추출합니다. (실제로는 JSON 파싱 라이브러리를 사용하세요)
        String tokenPrefix = "\"access_token\":\"";
        int startIndex = responseBody.indexOf(tokenPrefix) + tokenPrefix.length();
        int endIndex = responseBody.indexOf("\"", startIndex);
        return responseBody.substring(startIndex, endIndex);
    }
}