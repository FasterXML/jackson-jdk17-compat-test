package com.fasterxml.jackson.jdk16tests.jdktypes;

import com.fasterxml.jackson.annotation.*;

import com.fasterxml.jackson.databind.*;

import com.fasterxml.jackson.jdk16tests.Jdk16TestBase;

// for [databind#1794]
public class StackTraceElementTest extends Jdk16TestBase
{
    public static class ErrorObject {

        public String throwable;
        public String message;

//        @JsonDeserialize(contentUsing = StackTraceElementDeserializer.class)
        public StackTraceElement[] stackTrace;

        ErrorObject() {}

        public ErrorObject(Throwable throwable) {
            this.throwable = throwable.getClass().getName();
            message = throwable.getMessage();
            stackTrace = throwable.getStackTrace();
        }
    }

    // for [databind#1794] where extra `declaringClass` is serialized from private field.
    public void testCustomStackTraceDeser() throws Exception
    {
        ObjectMapper mapper = newJsonMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String json = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(new ErrorObject(new Exception("exception message")));

        ErrorObject result = mapper.readValue(json, ErrorObject.class);
        assertNotNull(result);
    }
}
