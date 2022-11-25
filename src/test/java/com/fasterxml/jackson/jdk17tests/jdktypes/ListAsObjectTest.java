package com.fasterxml.jackson.jdk17tests.jdktypes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.jdk17tests.Jdk17TestBase;

// Test extracted from XML "ListAsObjectTest", Databind "CollectionFormatShapeTest"
// (but relies on fix for [databind#3682] added in 2.15)
public class ListAsObjectTest extends Jdk17TestBase
{
    static class Value {
        public String v;
    
        public String getV() { return v; }
    
        public void setV(final String v) { this.v = v; }
    }

    @SuppressWarnings("serial")
    @JsonFormat(shape=JsonFormat.Shape.OBJECT)
    static class Values extends LinkedList<Value>
    {
        @JsonProperty("type")
        private String type;

        List<Value> values = new ArrayList<Value>();

        public String getType() { return type; }

        void setType(final String type) { this.type = type; }

        public List<Value> getValues() { return values; }

        void setValues(final List<Value> values) { this.values = values; }

        public void addValue(String s) {
            Value v = new Value();
            v.setV(s);
            values.add(v);
        }
    }

    /*
    /*********************************************************************
    /* Test methods
    /*********************************************************************
     */
    
    private final ObjectMapper MAPPER = newJsonMapper();

    public void testListAsObjectDeser() throws Exception
    {
        final Values values = MAPPER.readValue(a2q(
                "{'type':'array',\n"+
                "'values':[\n"+
                "  {'v':'c'},\n" +
                "  {'v':'d'}\n"+
                "]}"),
                Values.class);
        assertEquals(2, values.getValues().size(), 2);
        assertEquals("c", values.getValues().get(0).getV());
        assertEquals("d", values.getValues().get(1).getV());
    
        assertEquals("array", values.getType());
    }

    // Fails for "getFirst()" issue, handle deser first
    /*
    public void testListAsObjectSer() throws Exception
    {
        // This was passing already but double-check
        Values input = new Values();
        input.addValue("test");

        String json = MAPPER.writeValueAsString(input);
        assertEquals(json, a2q(
                "{'type':'array','values':[{'v':'test'}]}"
                ));
    }
    */
}
