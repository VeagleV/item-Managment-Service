package com.crm.item.core.handlers;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EanHandler {

    private final static Random rand = new Random();

    public boolean isValidEan(String ean) {
        if(ean == null || (ean.length() != 13 && ean.length() != 8) || !ean.matches("\\d+")) return false;
        String lastEanDigit = String.valueOf(ean.charAt(ean.length()-1));
        String checkDigit = generateCheckDigit(ean.substring(0, ean.length()-1));
        return checkDigit.equals(lastEanDigit);
    }

    public String generateEan(Integer length){
        if (length != 8 && length != 13) throw new IllegalArgumentException("Длина кода должна быть 8 или 13");
        String baseCode = generateBaseCode(length-1);
        String checkDigit = generateCheckDigit(baseCode);
        return baseCode + checkDigit;
    }

    public String generateBaseCode(Integer length){
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < length; i++){
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String generateCheckDigit(String baseCode){
        int sum = 0;
        for (int i = 0; i < baseCode.length(); i++) {
            int digit = Character.getNumericValue(baseCode.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return String.valueOf(checksum);
    }
}
