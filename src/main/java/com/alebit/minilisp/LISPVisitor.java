package com.alebit.minilisp;

import com.alebit.minilisp.LISPLexer;
import com.alebit.minilisp.exceptions.UnexpectedTypeException;
import com.alebit.minilisp.object.Function;
import com.alebit.minilisp.object.LISPObject;
import com.alebit.minilisp.LISPParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class LISPVisitor extends com.alebit.minilisp.LISPParserBaseVisitor<LISPObject> {
    private Scope scope = new Scope(null);

    public LISPVisitor() {

    }

    public LISPVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public LISPObject visitBasicExprs(LISPParser.BasicExprsContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public LISPObject visitNumExprs(LISPParser.NumExprsContext ctx) {
        return new LISPObject(Integer.parseInt(ctx.NUMBER().getSymbol().getText()));
    }

    @Override
    public LISPObject visitBoolExprs(LISPParser.BoolExprsContext ctx) {
        if (ctx.bool().op.getType() == LISPLexer.TRUE) {
            return new LISPObject(true);
        } else {
            return new LISPObject(false);
        }
    }

    @Override
    public LISPObject visitVarExprs(LISPParser.VarExprsContext ctx) {
        return scope.getVar(ctx.ID().getSymbol().getText(), ctx.ID().getSymbol());
    }

    @Override
    public LISPObject visitUOpExpr(LISPParser.UOpExprContext ctx) {
        LISPObject object = new LISPObject();
        switch (ctx.uArgNumOp().op.getType()) {
            case LISPLexer.PLUS:
                int sum = 0;
                for (LISPParser.ExprsContext context: ctx.exprs()) {
                    LISPObject contextValue = visit(context);
                    if (contextValue.getObjectType() != Integer.class) {
                        throw new UnexpectedTypeException(Integer.class, contextValue.getObjectType(), context.getStart());
                    }
                    sum += (int) contextValue.getValue();
                }
                object.setValue(sum);
                break;
            case LISPLexer.MULTIPLY:
                int product = 1;
                for (LISPParser.ExprsContext context: ctx.exprs()) {
                    LISPObject contextValue = visit(context);
                    if (contextValue.getObjectType() != Integer.class) {
                        throw new UnexpectedTypeException(Integer.class, contextValue.getObjectType(), context.getStart());
                    }
                    product *= (int) contextValue.getValue();
                }
                object.setValue(product);
                break;
            case LISPLexer.EQUAL:
                boolean equivalence = true;
                Integer lastValue = null;
                for (LISPParser.ExprsContext context: ctx.exprs()) {
                    LISPObject contextValue = visit(context);
                    if (contextValue.getObjectType() != Integer.class) {
                        throw new UnexpectedTypeException(Integer.class, contextValue.getObjectType(), context.getStart());
                    }
                    if (lastValue == null) {
                        lastValue = (int) contextValue.getValue();
                    } else {
                        if (lastValue != contextValue.getValue()) {
                            equivalence = false;
                        }
                    }
                }
                object.setValue(equivalence);
                break;
        }
        return object;
    }

    @Override
    public LISPObject visitTwoOpExpr(LISPParser.TwoOpExprContext ctx) {
        LISPObject object = new LISPObject();
        LISPObject val1;
        LISPObject val2;
        switch (ctx.twoArgNumOp().op.getType()) {
            case LISPLexer.MINUS:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val1.getObjectType(), ctx.exprs(0).getStart());
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val2.getObjectType(), ctx.exprs(1).getStart());
                }
                object.setValue((int)val1.getValue() - (int)val2.getValue());
                break;
            case LISPLexer.DIVIDE:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val1.getObjectType(), ctx.exprs(0).getStart());
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val2.getObjectType(), ctx.exprs(1).getStart());
                }
                object.setValue((int)val1.getValue() / (int)val2.getValue());
                break;
            case LISPLexer.MODULUS:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val1.getObjectType(), ctx.exprs(0).getStart());
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val2.getObjectType(), ctx.exprs(1).getStart());
                }
                object.setValue((int)val1.getValue() % (int)val2.getValue());
                break;
            case LISPLexer.GREATER:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val1.getObjectType(), ctx.exprs(0).getStart());
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val2.getObjectType(), ctx.exprs(1).getStart());
                }
                object.setValue((int)val1.getValue() > (int)val2.getValue());
                break;
            case LISPLexer.SMALLER:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val1.getObjectType(), ctx.exprs(0).getStart());
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(Integer.class, val2.getObjectType(), ctx.exprs(1).getStart());
                }
                object.setValue((int)val1.getValue() < (int)val2.getValue());
                break;
        }
        return object;
    }

    @Override
    public LISPObject visitULogExpr(LISPParser.ULogExprContext ctx) {
        LISPObject object = new LISPObject();
        boolean result = true;
        switch (ctx.uArgLogOp().op.getType()) {
            case LISPParser.AND:
                for (LISPParser.ExprsContext context: ctx.exprs()) {
                    LISPObject contextValue = visit(context);
                    if (contextValue.getObjectType() != Boolean.class) {
                        throw new UnexpectedTypeException(Boolean.class, contextValue.getObjectType(), context.getStart());
                    }
                    if (!(boolean)contextValue.getValue()) {
                        result = false;
                    }
                }
                break;
            case LISPParser.OR:
                result = false;
                for (LISPParser.ExprsContext context: ctx.exprs()) {
                    LISPObject contextValue = visit(context);
                    if (contextValue.getObjectType() != Boolean.class) {
                        throw new UnexpectedTypeException(Boolean.class, contextValue.getObjectType(), context.getStart());
                    }
                    if ((boolean)contextValue.getValue()) {
                        result = true;
                    }
                }
                break;
        }
        object.setValue(result);
        return object;
    }

    @Override
    public LISPObject visitOneLogExpr(LISPParser.OneLogExprContext ctx) {
        LISPObject object = new LISPObject();
        LISPObject value = visit(ctx.exprs());
        if (value.getObjectType() != Boolean.class) {
            throw new UnexpectedTypeException(Boolean.class, value.getObjectType(), ctx.exprs().getStart());
        }
        if ((boolean) value.getValue()) {
            object.setValue(false);
        } else {
            object.setValue(true);
        }
        return object;
    }

    @Override
    public LISPObject visitPNumExpr(LISPParser.PNumExprContext ctx) {
        LISPObject object = visit(ctx.exprs());
        if (object.getObjectType() != Integer.class) {
            throw new UnexpectedTypeException(Integer.class, object.getObjectType(), ctx.exprs().getStart());
        }
        System.out.println(object.getValue());
        return new LISPObject();
    }

    @Override
    public LISPObject visitPBExpr(LISPParser.PBExprContext ctx) {
        LISPObject object = visit(ctx.exprs());
        if (object.getObjectType() != Boolean.class) {
            throw new UnexpectedTypeException(Boolean.class, object.getObjectType(), ctx.exprs().getStart());
        }
        if ((boolean) object.getValue()) {
            System.out.println(LISPLexer.VOCABULARY.getDisplayName(LISPLexer.TRUE));
        } else {
            System.out.println(LISPLexer.VOCABULARY.getDisplayName(LISPLexer.FALSE));
        }
        return new LISPObject();
    }

    @Override
    public LISPObject visitFuncExpr(LISPParser.FuncExprContext ctx) {
        String[] argsName = new String[ctx.ID().size()];
        for (int i = 0; i < ctx.ID().size(); i++) {
            argsName[i] = ctx.ID(i).getSymbol().getText();
        }
        for (LISPParser.DefContext context: ctx.def()) {
            visit(context);
        }
        Function function = new Function(ctx.exprs(), argsName, scope);
        return new LISPObject(function);
    }

    @Override
    public LISPObject visitFuncWFuncCallExpr(LISPParser.FuncWFuncCallExprContext ctx) {
        String[] argsName = new String[ctx.ID().size()];
        for (int i = 0; i < ctx.ID().size(); i++) {
            argsName[i] = ctx.ID(i).getSymbol().getText();
        }
        for (LISPParser.DefContext context: ctx.def()) {
            visit(context);
        }
        Function function = new Function(ctx.exprs(0), argsName, scope);
        LISPObject[] args = new LISPObject[ctx.exprs().size()-1];
        for (int i = 1; i < ctx.exprs().size(); i++) {
            args[i-1] = visit(ctx.exprs(i));
        }
        Token dbgToken;
        if (ctx.exprs(1) == null) {
            dbgToken = ctx.getParent().stop;
        } else {
            dbgToken = ctx.exprs(1).getStart();
        }
        return function.invoke(args, dbgToken);
    }

    @Override
    public LISPObject visitFuncCallExpr(LISPParser.FuncCallExprContext ctx) {
        LISPObject function = scope.getVar(ctx.ID().getSymbol().getText(), ctx.ID().getSymbol());
        if (function.getObjectType() != Function.class) {
            throw new UnexpectedTypeException(Function.class, function.getObjectType(), ctx.ID().getSymbol());
        }
        LISPObject[] args = new LISPObject[ctx.exprs().size()];
        for (int i = 0; i < ctx.exprs().size(); i++) {
            args[i] = visit(ctx.exprs(i));
        }
        Token dbgToken;
        if (ctx.exprs(0) == null) {
            dbgToken = ctx.getParent().stop;
        } else {
            dbgToken = ctx.exprs(0).getStart();
        }
        return ((Function) function.getValue()).invoke(args, dbgToken);
    }

    @Override
    public LISPObject visitIfExpr(LISPParser.IfExprContext ctx) {
        LISPObject ifResult = visit(ctx.exprs(0));
        if (ifResult.getObjectType() != Boolean.class) {
            throw new UnexpectedTypeException(Boolean.class, ifResult.getObjectType(), ctx.exprs(0).getStart());
        }
        if ((boolean) ifResult.getValue()) {
            return visit(ctx.exprs(1));
        } else {
            return visit(ctx.exprs(2));
        }
    }

    @Override
    public LISPObject visitDef(LISPParser.DefContext ctx) {
        scope.addVar(ctx.ID().getSymbol().getText(), visit(ctx.exprs()));
        return new LISPObject();
    }
}