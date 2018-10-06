package com.atherys.rpg.model.attribute;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.attribute.AttributeCarrier;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.text.Text;

public class DamageResistsAttribute extends AbstractAttribute<DamageEntityEvent> {

    private DamageType type;
    private double bonus;

    public DamageResistsAttribute(DamageType type, double bonus) {
        super("atherys:damage_resistance", "Damage Resistance", DamageEntityEvent.class);
        this.type = type;
        this.bonus = bonus;
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
        return Text.of("Bonus to ", type.getName(), " Damage Resistance: ", bonus, "%");
    }

    @Override
    public Attribute copy() {
        return new DamageResistsAttribute(type, bonus);
    }
}
