package com.atherys.rpg.skill.description;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.skill.DescriptionArgument;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

public class ExpressionDescriptionArgument implements DescriptionArgument {

    protected String expression;

    public ExpressionDescriptionArgument(String expression) {
        this.expression = expression;
    }

    @Override
    public TextRepresentable apply(Living living) {
        return Text.of(AtherysRPG.getInstance().getExpressionService().evalExpression(living, expression).doubleValue());
    }
}
