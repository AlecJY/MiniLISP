package com.alebit.minilisp.object;

public class LISPObject {
    public static int LISPLexer = 1;
    private Object value;
    private int type;

    public LISPObject() {
    }

    public LISPObject(Object value) {
        this.value = value;
    }

    public LISPObject(Object value, int type) {
        this.value = value;
        this.type = type;
    }

    public Class<? extends Object> getObjectType() {
        if (value == null) {
            return null;
        }
        return value.getClass();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
