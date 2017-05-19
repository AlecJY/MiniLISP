package com.alebit.minilisp;

import com.alebit.minilisp.exceptions.VariableNotFoundException;
import com.alebit.minilisp.exceptions.VariableReDefineException;
import com.alebit.minilisp.object.LISPObject;

import java.util.HashMap;

public class Scope {
    private Scope parent;
    private HashMap<String, LISPObject> variables = new HashMap<>();

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public void addVar(String name, LISPObject value) {
        if (variables.containsKey(name)) {
            variables.replace(name, value);
        } else {
            variables.put(name, value);
        }
    }

    public LISPObject getVar(String name) {
        if (!variables.containsKey(name)) {
            if (parent != null) {
                return parent.getVar(name);
            } else {
                throw new VariableNotFoundException();
            }
        }
        return variables.get(name);
    }
}
