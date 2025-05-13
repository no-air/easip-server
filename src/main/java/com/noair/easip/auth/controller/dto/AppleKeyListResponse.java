package com.noair.easip.auth.controller.dto;

import java.util.Arrays;

public record AppleKeyListResponse(
        AppleKeyResponse[] keys
) {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof AppleKeyListResponse applekeylistresponse
                && Arrays.equals(applekeylistresponse.keys, keys);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(keys);
    }

    @Override
    public String toString() {
        return "AppleKeyListResponse{" +
                "keys=" + Arrays.toString(keys) +
                '}';
    }
}
