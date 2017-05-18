package com.alebit.minilisp;

import com.alebit.minilisp.LISPLexer;
import com.alebit.minilisp.exceptions.UnexpectedTypeException;
import com.alebit.minilisp.object.LISPObject;
import com.alebit.minilisp.LISPParser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class LISPVisitor extends com.alebit.minilisp.LISPParserBaseVisitor<LISPObject> {
    private Scope scope = new Scope(null);

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
        return scope.getVar(ctx.ID().getSymbol().getText());
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
                        throw new UnexpectedTypeException(ctx, "Unexpected type " + contextValue.getObjectType().getName() + ". Expect Integer" );
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
                        throw new UnexpectedTypeException(ctx, "Unexpected type " + contextValue.getObjectType().getName() + ". Expect Integer" );
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
                        throw new UnexpectedTypeException(ctx, "Unexpected type " + contextValue.getObjectType().getName() + ". Expect Integer" );
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
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val1.getObjectType().getName() + ". Expect Integer" );
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val2.getObjectType().getName() + ". Expect Integer" );
                }
                object.setValue((int)val1.getValue() - (int)val2.getValue());
                break;
            case LISPLexer.DIVIDE:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val1.getObjectType().getName() + ". Expect Integer" );
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val2.getObjectType().getName() + ". Expect Integer" );
                }
                object.setValue((int)val1.getValue() / (int)val2.getValue());
                break;
            case LISPLexer.MODULUS:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val1.getObjectType().getName() + ". Expect Integer" );
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val2.getObjectType().getName() + ". Expect Integer" );
                }
                object.setValue((int)val1.getValue() % (int)val2.getValue());
                break;
            case LISPLexer.GREATER:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val1.getObjectType().getName() + ". Expect Integer" );
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val2.getObjectType().getName() + ". Expect Integer" );
                }
                object.setValue((int)val1.getValue() > (int)val2.getValue());
                break;
            case LISPLexer.SMALLER:
                val1 = visit(ctx.exprs(0));
                val2 = visit(ctx.exprs(1));
                if (val1.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val1.getObjectType().getName() + ". Expect Integer" );
                }
                if (val2.getObjectType() != Integer.class) {
                    throw new UnexpectedTypeException(ctx, "Unexpected type " + val2.getObjectType().getName() + ". Expect Integer" );
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
                        throw new UnexpectedTypeException(ctx, "Unexpected type " + contextValue.getObjectType().getName() + ". Expect Boolean");
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
                        throw new UnexpectedTypeException(ctx, "Unexpected type " + contextValue.getObjectType().getName() + ". Expect Boolean");
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
            throw new UnexpectedTypeException(ctx, "Unexpected type " + value.getObjectType().getName() + ". Expect Boolean");
        }
        if ((boolean) value.getValue()) {
            object.setValue(false);
        } else {
            object.setValue(true);
        }
        return object;
    }

    @Override
    public LISPObject visitIfExpr(LISPParser.IfExprContext ctx) {
        LISPObject ifResult = visit(ctx.exprs(0));
        if (ifResult.getObjectType() != Boolean.class) {
            throw new UnexpectedTypeException(ctx, "Unexpected type " + ifResult.getObjectType().getName() + ". Expect Boolean");
        }
        if ((boolean) ifResult.getValue()) {
            return visit(ctx.exprs(1));
        } else {
            return visit(ctx.exprs(2));
        }
    }

    @Override
    public LISPObject visitPNumExpr(LISPParser.PNumExprContext ctx) {
        LISPObject object = visit(ctx.exprs());
        if (object.getObjectType() != Integer.class) {
            throw new UnexpectedTypeException(ctx, "Unexpected type " + object.getObjectType().getName() + ". Expect Integer" );
        }
        System.out.println(object.getValue());
        return new LISPObject();
    }

    @Override
    public LISPObject visitPBExpr(LISPParser.PBExprContext ctx) {
        LISPObject object = visit(ctx.exprs());
        if (object.getObjectType() != Boolean.class) {
            throw new UnexpectedTypeException(ctx, "Unexpected type " + object.getObjectType().getName() + ". Expect Boolean" );
        }
        if ((boolean) object.getValue()) {
            System.out.println(LISPLexer.VOCABULARY.getDisplayName(LISPLexer.TRUE));
        } else {
            System.out.println(LISPLexer.VOCABULARY.getDisplayName(LISPLexer.FALSE));
        }
        return new LISPObject();
    }

    @Override
    public LISPObject visitDef(LISPParser.DefContext ctx) {
        scope.addVar(ctx.ID().getSymbol().getText(), visit(ctx.exprs()));
        return new LISPObject();
    }
}
