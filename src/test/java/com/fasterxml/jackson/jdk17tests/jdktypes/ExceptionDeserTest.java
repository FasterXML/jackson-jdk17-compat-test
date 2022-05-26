package com.fasterxml.jackson.jdk17tests.jdktypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.jdk17tests.Jdk17TestBase;

public class ExceptionDeserTest extends Jdk17TestBase
{
    // for [databind#3275]
    public void testIssue3275() throws Exception
    {
        final ObjectMapper mapper = JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE)
                .build();

        String jsonString = "{\"message\":\"This is my runtime exception message\"}";
        Exception ex = mapper.readValue(jsonString, Exception.class);
        assertNotNull(ex);
    }
}
