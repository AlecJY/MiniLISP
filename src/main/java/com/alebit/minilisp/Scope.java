package com.alebit.minilisp;

import com.alebit.minilisp.exceptions.VariableNotFoundException;
import com.alebit.minilisp.exceptions.VariableReDefineException;
import com.alebit.minilisp.object.LISPObject;

import java.util.HashMap;

class Scope {
    private Scope parent;
    private HashMap<String, LISPObject> variables = new HashMap<>();

    Scope(Scope parent) {
        this.parent = parent;
    }

    void addVar(String name, LISPObject value) {
        if (variables.containsKey(name)) {
            variables.replace(name, value);
        }
        variables.put(name, value);
    }

    LISPObject getVar(String name) {
        if (!variables.containsKey(name)) {
            throw new VariableNotFoundException();
        }
        return variables.get(name);
    }
}
