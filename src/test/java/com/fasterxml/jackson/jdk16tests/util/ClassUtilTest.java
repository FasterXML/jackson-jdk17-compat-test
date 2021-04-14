package com.fasterxml.jackson.jdk16tests.util;

import java.util.*;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.jdk16tests.Jdk16TestBase;

public class ClassUtilTest extends Jdk16TestBase
{
    /*
    /**********************************************************************
    /* Test classes, enums
    /**********************************************************************
     */

    enum TestEnum {
        A,
        B {
            @Override
            public String toString() {
                return "TestEnum{B}";
            }
        }
    }

    /*
    /**********************************************************************
    /* Test methods (with JDK 16 issues)
    /**********************************************************************
     */

    public void testFindEnumTypeNonJdk()
    {
        assertEquals(TestEnum.class, ClassUtil.findEnumType(TestEnum.A));
        assertEquals(TestEnum.class, ClassUtil.findEnumType(TestEnum.B));
    }

    // Some trouble with JDK 16+
    public void testFindEnumSetTypeJDK()
    {
        // different codepaths for empty and non-empty EnumSets...
        assertEquals(TestEnum.class, ClassUtil.findEnumType(EnumSet.allOf(TestEnum.class)));
        assertEquals(TestEnum.class, ClassUtil.findEnumType(EnumSet.noneOf(TestEnum.class)));
    }

    // Some trouble with JDK 16+
    public void testFindEnumMapTypeJDK()
    {
        assertEquals(TestEnum.class, ClassUtil.findEnumType(new EnumMap<TestEnum,Integer>(TestEnum.class)));
    }
}
