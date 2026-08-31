package io.github.ahmedktarii.digitalsingage.Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtility {

    public static String generateRandomSlug(){

        return RandomStringUtils.randomAlphanumeric(8);
    }
}
