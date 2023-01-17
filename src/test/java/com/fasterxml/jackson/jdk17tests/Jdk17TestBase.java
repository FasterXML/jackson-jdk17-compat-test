package com.fasterxml.jackson.jdk17tests;

import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public abstract class Jdk17TestBase
    extends junit.framework.TestCase
{
    /*
    /**********************************************************************
    /* Factory methods (simple)
    /**********************************************************************
     */

    protected static ObjectMapper newJsonMapper() {
        return new JsonMapper();
    }

    protected static JsonMapper.Builder jsonMapperBuilder() {
        return JsonMapper.builder();
    }

    /*
    /**********************************************************************
    /* Assertion methods
    /**********************************************************************
     */

    public static void verifyException(Throwable e, String... matches)
    {
        String msg = e.getMessage();
        String lmsg = (msg == null) ? "" : msg.toLowerCase();
        for (String match : matches) {
            String lmatch = match.toLowerCase();
            if (lmsg.indexOf(lmatch) >= 0) {
                return;
            }
        }
        fail("Expected an exception with one of substrings ("
                +Arrays.asList(matches)+"): got one (of type "+e.getClass().getName()
                +") with message \""+msg+"\"");
    }
    
    /*
    /**********************************************************************
    /* Other helper methods
    /**********************************************************************
     */

    public String q(String str) {
        return '"'+str+'"';
    }

    protected static String a2q(String json) {
        return json.replace("'", "\"");
    }
}
