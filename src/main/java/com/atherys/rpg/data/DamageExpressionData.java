package com.atherys.rpg.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class DamageExpressionData extends AbstractData<DamageExpressionData, DamageExpressionData.Immutable> {

    private String damageExpression;

    protected DamageExpressionData() {

    }

    public DamageExpressionData (String damageExpression) {
        this.damageExpression = damageExpression;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RPGKeys.DAMAGE_EXPRESSION, this::getDamageExpression);
        registerFieldSetter(RPGKeys.DAMAGE_EXPRESSION, this::setDamageExpression);
        registerKeyValue(RPGKeys.DAMAGE_EXPRESSION, this::damageExpression);
    }

    public String getDamageExpression() {
        return damageExpression;
    }

    public void setDamageExpression(String damageExpression) {
        this.damageExpression = damageExpression;
    }

    public Value<String> damageExpression() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.DAMAGE_EXPRESSION, damageExpression);
    }


    @Override
    public Optional<DamageExpressionData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(DamageExpressionData.class).ifPresent(that -> {
            DamageExpressionData data = overlap.merge(this, that);
            this.damageExpression = data.damageExpression;
        });

        return Optional.of(this);
    }

    @Override
    public Optional<DamageExpressionData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<DamageExpressionData> from(DataView container) {
        container.getString(RPGKeys.DAMAGE_EXPRESSION.getQuery()).ifPresent(v -> damageExpression = v);
        return Optional.of(this);
    }

    @Override
    public DamageExpressionData copy() {
        return new DamageExpressionData(damageExpression);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(damageExpression);
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(RPGKeys.DAMAGE_EXPRESSION, damageExpression);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    public static class Immutable extends AbstractImmutableData<Immutable, DamageExpressionData> {

        String damageExpression;

        public Immutable(String damageExpression) {
            this.damageExpression = damageExpression;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(RPGKeys.DAMAGE_EXPRESSION, this::getDamageExpression);
            registerKeyValue(RPGKeys.DAMAGE_EXPRESSION, this::damageExpression);
        }

        public String getDamageExpression() {
            return damageExpression;
        }

        public ImmutableValue<String> damageExpression() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.DAMAGE_EXPRESSION, damageExpression).asImmutable();
        }

        @Override
        public DamageExpressionData asMutable() {
            return new DamageExpressionData(damageExpression);
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer().set(RPGKeys.DAMAGE_EXPRESSION, damageExpression);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }
    }

    public static class Builder extends AbstractDataBuilder<DamageExpressionData> implements DataManipulatorBuilder<DamageExpressionData, Immutable> {

        public Builder() {
            super(DamageExpressionData.class, 1);
        }

        @Override
        public DamageExpressionData create() {
            return new DamageExpressionData();
        }

        @Override
        public Optional<DamageExpressionData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<DamageExpressionData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }
    }
}
