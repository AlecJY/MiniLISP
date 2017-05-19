package com.alebit.minilisp;

import com.alebit.minilisp.exceptions.VariableNotFoundException;
import com.alebit.minilisp.object.LISPObject;
import org.antlr.v4.runtime.Token;

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

    public LISPObject getVar(String name, Token token) {
        if (!variables.containsKey(name)) {
            if (parent != null) {
                return parent.getVar(name, token);
            } else {
                throw new VariableNotFoundException(name, token);
            }
        }
        return variables.get(name);
    }
}
