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

public class LootableData extends AbstractData<LootableData, LootableData.Immutable> {

    private String lootableId;

    public LootableData() {

    }

    public LootableData(String lootableId) {
        this.lootableId = lootableId;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RPGKeys.LOOTABLE_ID, this::getLootableId);
        registerFieldSetter(RPGKeys.LOOTABLE_ID, this::setLootableId);
        registerKeyValue(RPGKeys.LOOTABLE_ID, this::lootableId);
    }

    public String getLootableId() {
        return lootableId;
    }

    public void setLootableId(String lootableId) {
        this.lootableId = lootableId;
    }

    public Value<String> lootableId() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.LOOTABLE_ID, lootableId);
    }


    @Override
    public Optional<LootableData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(LootableData.class).ifPresent(that -> {
            LootableData data = overlap.merge(this, that);
            this.lootableId = data.lootableId;
        });

        return Optional.of(this);
    }

    @Override
    public Optional<LootableData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<LootableData> from(DataView container) {
        container.getString(RPGKeys.LOOTABLE_ID.getQuery()).ifPresent(v -> lootableId = v);
        return Optional.of(this);
    }

    @Override
    public LootableData copy() {
        return new LootableData(lootableId);
    }

    @Override
    public LootableData.Immutable asImmutable() {
        return new LootableData.Immutable(lootableId);
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(RPGKeys.LOOTABLE_ID, lootableId);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    public static class Immutable extends AbstractImmutableData<LootableData.Immutable, LootableData> {

        String lootableId;

        public Immutable(String lootableId) {
            this.lootableId = lootableId;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(RPGKeys.LOOTABLE_ID, this::getLootableId);
            registerKeyValue(RPGKeys.LOOTABLE_ID, this::lootableId);
        }

        public String getLootableId() {
            return lootableId;
        }

        public ImmutableValue<String> lootableId() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.LOOTABLE_ID, lootableId).asImmutable();
        }

        @Override
        public LootableData asMutable() {
            return new LootableData(lootableId);
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer().set(RPGKeys.LOOTABLE_ID, lootableId);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }
    }

    public static class Builder extends AbstractDataBuilder<LootableData> implements DataManipulatorBuilder<LootableData, LootableData.Immutable> {

        public Builder() {
            super(LootableData.class, 1);
        }

        @Override
        public LootableData create() {
            return new LootableData();
        }

        @Override
        public Optional<LootableData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<LootableData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }
    }
}