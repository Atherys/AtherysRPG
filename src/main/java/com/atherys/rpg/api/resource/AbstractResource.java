package com.atherys.rpg.api.resource;

import com.atherys.rpg.utils.MathUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public abstract class AbstractResource implements Resource {

    private TextColor color;
    private String id;
    private String name;
    private double max;
    private double current;

    protected AbstractResource(TextColor color, String id, String name, double max, double current) {
        this.color = color;
        this.id = id;
        this.name = name;
        this.max = max;
        this.current = MathUtils.clamp(current, 0.0d, max);
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public double getCurrent() {
        return current;
    }

    private void change(double delta) {
        this.current = MathUtils.clamp(current + delta, 0.0d, max);
    }

    @Override
    public void drain(double amount) {
        this.change(-amount);
    }

    @Override
    public void fill(double amount) {
        this.change(amount);
    }

    @Override
    public TextColor getColor() {
        return color;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Text toText() {
        return Text.builder()
                .append(Text.of(this.getColor(), this.getName()))
                .onHover(TextActions.showText(Text.of(this.getCurrent(), this.getColor(), "/", TextColors.RESET, this.getMax())))
                .build();
    }
}
