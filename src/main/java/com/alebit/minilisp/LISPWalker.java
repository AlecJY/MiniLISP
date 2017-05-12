package com.alebit.minilisp;

import com.alebit.minilisp.LISPParserBaseListener;
import com.alebit.minilisp.LISPParser;

public class LISPWalker extends LISPParserBaseListener {
    public void enterProg(LISPParser.ProgContext ctx) {
        for (LISPParser.StatContext sctx: ctx.stat()) {
            System.out.println(sctx.getText());
        }
    }
}
