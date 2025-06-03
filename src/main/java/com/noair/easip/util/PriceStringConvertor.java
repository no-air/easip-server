package com.noair.easip.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PriceStringConvertor {
    public static String toKoreanPriceString(double price) {
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
}
