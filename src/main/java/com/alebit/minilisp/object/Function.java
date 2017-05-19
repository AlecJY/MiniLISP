package com.alebit.minilisp.object;

import com.alebit.minilisp.LISPVisitor;
import com.alebit.minilisp.Scope;
import com.alebit.minilisp.exceptions.FunctionArgumentsNotMatchException;
import org.antlr.v4.runtime.tree.ParseTree;

public class Function {
    private ParseTree ctx;
    private String[] argsName;
    private Scope parentScope;

    public Function(ParseTree ctx, String[] argsName, Scope parentScope) {
        this.ctx = ctx;
        this.argsName = argsName;
        this.parentScope = parentScope;
    }

    public LISPObject invoke(LISPObject[] args) {
        Scope scope = new Scope(parentScope);
        if (args.length != argsName.length) {
            throw new FunctionArgumentsNotMatchException();
        }
        for (int i = 0; i < args.length; i++) {
            scope.addVar(argsName[i], args[i]);
        }
        LISPVisitor visitor = new LISPVisitor(scope);
        return visitor.visit(ctx);
    }
}