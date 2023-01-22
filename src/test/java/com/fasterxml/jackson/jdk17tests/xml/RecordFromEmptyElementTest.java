package com.fasterxml.jackson.jdk17tests.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.jdk17tests.Jdk17TestBase;

// For https://github.com/FasterXML/jackson-dataformat-xml/issues/542
public class RecordFromEmptyElementTest extends Jdk17TestBase
{
    public record Record542(String a) {
        public Record542() {
            this(null);
        }
    }

    private final static XmlMapper XML_MAPPER = new XmlMapper();

    public void testNoArgumentConstructorXml() throws Exception {
        Record542 result = XML_MAPPER.readerFor(Record542.class).readValue("<TEST/>");
        assertNotNull(result);
        assertNull(result.a);
    }
}
