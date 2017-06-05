package com.alebit.minilisp.object;

import com.alebit.minilisp.LISPVisitor;
import com.alebit.minilisp.Scope;
import com.alebit.minilisp.exceptions.FunctionArgumentsNotMatchException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class Function {
    private ParseTree ctx;
    private String[] argsName;
    private Scope defScope;

    public Function(ParseTree ctx, String[] argsName, Scope defScope) {
        this.ctx = ctx;
        this.argsName = argsName;
        this.defScope = defScope;
    }

    public LISPObject invoke(LISPObject[] args, Token dbgToken) {
        Scope scope = defScope.clone();
        if (args.length != argsName.length) {
            throw new FunctionArgumentsNotMatchException(argsName.length, args.length, dbgToken);
        }
        for (int i = 0; i < args.length; i++) {
            scope.addVar(argsName[i], args[i]);
        }
        LISPVisitor visitor = new LISPVisitor(scope);
        return visitor.visit(ctx);
    }
}
