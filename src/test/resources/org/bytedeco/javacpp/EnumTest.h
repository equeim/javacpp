enum class BoolEnum : bool {
    BOOL_ENUM = 1
};

enum class CharEnum : char {
    CHAR_ENUM = 123
};

enum class ShortEnum : short {
    SHORT_ENUM = 123
};

enum /* no class */ IntEnum : int {
    INT_ENUM = 123
};

enum /* no class */ LongEnum : long long {
    LONG_ENUM = 123
};

BoolEnum Char2Bool(CharEnum e) {
    return (BoolEnum)e;
}

ShortEnum Char2Short(CharEnum e) {
    return (ShortEnum)e;
}

LongEnum Int2Long(IntEnum e) {
    return (LongEnum)e;
}

LongEnum enumCallback(LongEnum (*f)(CharEnum e)) {
    return f(CharEnum::CHAR_ENUM);
}
