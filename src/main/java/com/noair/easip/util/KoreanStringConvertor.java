package com.noair.easip.util;

import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class KoreanStringConvertor {
    public static String toPriceString(double price) {
        long value = (long) price; // 소수점 버림

        if (value == 0) {
            return "0원";
        }

        StringBuilder sb = new StringBuilder();

        long eok = value / 100_000_000;
        long man = (value % 100_000_000) / 10_000;
        long chun = (value % 10_000) / 1_000;
        long baek = (value % 1_000) / 100;

        if (eok > 0) {
            sb.append(eok).append("억");
        }
        if (man > 0) {
            sb.append(man).append("만");
        }
        if (chun > 0) {
            sb.append(chun).append("천");
        }
        if (baek > 0) {
            sb.append(baek).append("백");
        }
        return sb.toString();
    }

    public static String toCompactAddress(String address) {
        // 공백 전부 제거
        address = address.replace(" ", "");

        // 주소에서 서울 관련 부분 제거
        String compactAddress = address.replace("서울특별시", "").replace("서울시", "").replace("서울", "").trim();

        // 자치구 ~ 지번까지만 남기고, 나머지 부분 제거
        Pattern pattern = Pattern.compile("^(.*?)(\\d+-\\d+|\\d+)");
        Matcher matcher = pattern.matcher(compactAddress);
        if (matcher.find()) {
            compactAddress = matcher.group(1) + matcher.group(2);
        }

        return compactAddress;
    }

    public static String extractAddress(String str) {
        String noWhiteText = str.trim().replaceAll("\\s+", "");

        // "□ 주택위치 : 서울특별시 은평구 대조동 2-9" 등의 주소 유형의 본문에서 주소만 분리하는 정규식
        Pattern pattern = Pattern.compile("^[■□]?주택위치[\\s:：·-]*([^()]+?)(?:\\s*\\((.+)\\))?$");
        Matcher matcher = pattern.matcher(noWhiteText);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return null;
    }
}
