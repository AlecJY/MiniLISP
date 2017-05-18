package com.alebit.minilisp.object;

public class LISPObject {
    private Object value;

    public LISPObject() {
    }

    public LISPObject(Object value) {
        this.value = value;
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
