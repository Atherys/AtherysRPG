package com.atherys.rpg.model.attribute.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.ImmutableMappedData;
import org.spongepowered.api.data.manipulator.mutable.MappedData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractMappedData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.event.cause.entity.damage.DamageType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DamageBonusData extends AbstractMappedData<DamageType,Double,DamageBonusData.Immutable,DamageBonusData.Builder> {

    protected DamageBonusData(Map<DamageType, Double> value, Key<? extends BaseValue<Map<DamageType, Double>>> usedKey) {
        super(value, usedKey);
    }

    @Override
    public Optional<Double> get(DamageType key) {
        return Optional.empty();
    }

    @Override
    public Set<DamageType> getMapKeys() {
        return asMap().keySet();
    }

    @Override
    public Immutable put(DamageType key, Double value) {
        return null;
    }

    @Override
    public Immutable putAll(Map<? extends DamageType, ? extends Double> map) {
        return null;
    }

    @Override
    public Immutable remove(DamageType key) {
        return null;
    }

    @Override
    public Optional<Immutable> fill(DataHolder dataHolder, MergeFunction overlap) {
        return Optional.empty();
    }

    @Override
    public Optional<Immutable> from(DataContainer container) {
        return Optional.empty();
    }

    @Override
    public Immutable copy() {
        return null;
    }

    @Override
    public Builder asImmutable() {
        return null;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    public static class Immutable implements MappedData<DamageType, Double, Immutable, Builder> {

        @Override
        public Optional<Double> get(DamageType key) {
            return Optional.empty();
        }

        @Override
        public Set<DamageType> getMapKeys() {
            return null;
        }

        @Override
        public MapValue<DamageType, Double> getMapValue() {
            return null;
        }

        @Override
        public Immutable put(DamageType key, Double value) {
            return null;
        }

        @Override
        public Immutable putAll(Map<? extends DamageType, ? extends Double> map) {
            return null;
        }

        @Override
        public Immutable remove(DamageType key) {
            return null;
        }

        @Override
        public Optional<Immutable> fill(DataHolder dataHolder, MergeFunction overlap) {
            return Optional.empty();
        }

        @Override
        public Optional<Immutable> from(DataContainer container) {
            return Optional.empty();
        }

        @Override
        public <E> Immutable set(Key<? extends BaseValue<E>> key, E value) {
            return null;
        }

        @Override
        public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
            return Optional.empty();
        }

        @Override
        public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
            return Optional.empty();
        }

        @Override
        public boolean supports(Key<?> key) {
            return false;
        }

        @Override
        public Immutable copy() {
            return null;
        }

        @Override
        public Set<Key<?>> getKeys() {
            return null;
        }

        @Override
        public Set<ImmutableValue<?>> getValues() {
            return null;
        }

        @Override
        public Builder asImmutable() {
            return null;
        }

        @Override
        public int getContentVersion() {
            return 0;
        }

        @Override
        public DataContainer toContainer() {
            return null;
        }
    }

    public static class Builder implements ImmutableMappedData<DamageType, Double, Builder, Immutable> {

        @Override
        public Optional<Double> get(DamageType key) {
            return Optional.empty();
        }

        @Override
        public Set<DamageType> getMapKeys() {
            return null;
        }

        @Override
        public ImmutableMapValue<DamageType, Double> getMapValue() {
            return null;
        }

        @Override
        public Immutable asMutable() {
            return null;
        }

        @Override
        public int getContentVersion() {
            return 0;
        }

        @Override
        public DataContainer toContainer() {
            return null;
        }

        @Override
        public <E> Optional<E> get(Key<? extends BaseValue<E>> key) {
            return Optional.empty();
        }

        @Override
        public <E, V extends BaseValue<E>> Optional<V> getValue(Key<V> key) {
            return Optional.empty();
        }

        @Override
        public boolean supports(Key<?> key) {
            return false;
        }

        @Override
        public Set<Key<?>> getKeys() {
            return null;
        }

        @Override
        public Set<ImmutableValue<?>> getValues() {
            return null;
        }
    }

}
