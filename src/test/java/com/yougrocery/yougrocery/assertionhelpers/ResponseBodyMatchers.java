package com.yougrocery.yougrocery.assertionhelpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatchers {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }

    public <T> ResultMatcher containsObjectAsJson(Object expectedObject,
                                           Class<T> targetClass) {

        return mvcResult -> {
            T actualObject = getActualObject(mvcResult, targetClass);

            assertThat(actualObject).usingRecursiveComparison()
                    .isEqualTo(expectedObject);
        };
    }

    private <T> T getActualObject(MvcResult mvcResult, Class<T> targetClass) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(
                getJson(mvcResult),
                targetClass);
    }

    private static String getJson(MvcResult mvcResult) throws UnsupportedEncodingException {
        String content = mvcResult.getResponse().getContentAsString();

        if (content.isEmpty()) {
            throw new NullPointerException("Mvc result is empty. Make sure to stub used Mocks correctly in the test.");
        }

        return content;
    }
}
