package com.atherys.rpg.attribute;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class DamageBonusesAttribute extends AbstractAttribute<DamageEntityEvent> {

    private Map<DamageType,Double> bonuses = new HashMap<>();

    public DamageBonusesAttribute() {
        super("atherys:damage_bonuses", "Damage Bonuses", DamageEntityEvent.class);
    }

    public Double getBonus(DamageType type) {
        return bonuses.getOrDefault(type, 0.0d);
    }

    @Override
    public <C extends AttributeCarrier> void attachTo(C carrier) {
        super.attachTo(carrier);
    }

    @Override
    protected void apply(DamageEntityEvent event, AttributeCarrier carrier) {

    }

    @Override
    public Text toText() {
        Text.Builder builder = Text.builder();
        bonuses.forEach( (k,v) -> builder.append(Text.of("Bonus to ", k.getName(), " damage: ", v, "%", Text.NEW_LINE)));
        return builder.build();
    }

    @Override
    public Attribute copy() {
        return new DamageBonusesAttribute();
    }
}
