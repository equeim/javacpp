/*
 * Copyright (C) 2018-2020 Samuel Audet
 *
 * Licensed either under the Apache License, Version 2.0, or (at your option)
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation (subject to the "Classpath" exception),
 * either version 2, or any later version (collectively, the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     http://www.gnu.org/licenses/
 *     http://www.gnu.org/software/classpath/license.html
 *
 * or as provided in the LICENSE.txt file that accompanied this code.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bytedeco.javacpp;

import java.io.File;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.tools.Builder;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

/**
 * Test cases for using Java enum types as C++ enum classes. Uses various classes from JavaCPP.
 *
 * @author Samuel Audet
 */
@Platform(compiler = "cpp11", include = "EnumTest.h")
public class EnumTest {

    public static enum BoolEnum {
        WRONG(false),
        BOOL(true);

        public final boolean value;
        private BoolEnum(boolean v) { this.value = v; }
    }

    @Name("CharEnum") public static enum ByteEnum {
        WRONG((byte) 42),
        BYTE((byte)123);

        public final byte value;
        private ByteEnum(byte v) { this.value = v; }
    }

    public static enum ShortEnum {
        WRONG((short)42),
        SHORT((short)123);

        public final short value;
        private ShortEnum(short v) { this.value = v; }
    }

    public static enum IntEnum {
        WRONG(42),
        INT(123);

        public final int value;
        private IntEnum(int v) { this.value = v; }
    }

    public static enum LongEnum {
        WRONG(42),
        LONG(123);

        public final long value;
        private LongEnum(long v) { this.value = v; }
    }

    public static class EnumCallback extends FunctionPointer {
        static { Loader.load(); }
        protected EnumCallback() { allocate(); }
        private native void allocate();

        public LongEnum call(ByteEnum e) {
            assertEquals(ByteEnum.BYTE, e);
            return LongEnum.LONG;
        }
    }

    public static native BoolEnum Char2Bool(ByteEnum e);
    public static native ShortEnum Char2Short(ByteEnum e);
    public static native LongEnum Int2Long(IntEnum e);
    public static native LongEnum enumCallback(EnumCallback f);

    @BeforeClass public static void setUpClass() throws Exception {
        System.out.println("Builder");
        Class c = EnumTest.class;
        Builder builder = new Builder().classesOrPackages(c.getName());
        File[] outputFiles = builder.build();

        System.out.println("Loader");
        Loader.load(c);
    }

    @Test public void testEnum() {
        System.out.println("Enum");

        assertEquals(BoolEnum.BOOL, Char2Bool(ByteEnum.BYTE));
        assertEquals(ShortEnum.SHORT, Char2Short(ByteEnum.BYTE));
        assertEquals(LongEnum.LONG, Int2Long(IntEnum.INT));
        assertEquals(LongEnum.LONG, enumCallback(new EnumCallback()));

        try {
            Int2Long(null);
            fail("NullPointerException should have been thrown.");
        } catch (NullPointerException e) {
            //System.out.println(e);
        }
    }
}
