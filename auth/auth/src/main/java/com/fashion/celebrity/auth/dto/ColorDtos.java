package com.fashion.celebrity.auth.dto;

import lombok.Data;

public class ColorDtos {
    @Data
    public static class Response {
        private String colorCode;
        private String colorHex;
        private String colorDesc;
    }
}
