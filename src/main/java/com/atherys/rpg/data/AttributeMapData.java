package com.atherys.rpg.data;

import com.atherys.rpg.api.stat.AttributeType;
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
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.mutable.MapValue;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2020-11-29T10:49:53.562Z")
public class AttributeMapData extends AbstractData<AttributeMapData, AttributeMapData.Immutable> {

    private Map<AttributeType, Double> attributes = new HashMap<>();

    {
        registerGettersAndSetters();
    }

    public AttributeMapData() {
    }

    AttributeMapData(Map<AttributeType, Double> attributes) {
        this.attributes = attributes;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(RPGKeys.ATTRIBUTES, this::getAttributes);
        registerFieldSetter(RPGKeys.ATTRIBUTES, this::setAttributes);
        registerKeyValue(RPGKeys.ATTRIBUTES, this::attributes);
    }

    public Map<AttributeType, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<AttributeType, Double> attributes) {
        this.attributes = attributes;
    }

    public MapValue<AttributeType, Double> attributes() {
        return Sponge.getRegistry().getValueFactory().createMapValue(RPGKeys.ATTRIBUTES, attributes);
    }

    @Override
    public Optional<AttributeMapData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(AttributeMapData.class).ifPresent(that -> {
            AttributeMapData data = overlap.merge(this, that);
            this.attributes = data.attributes;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<AttributeMapData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<AttributeMapData> from(DataView container) {
        Optional<? extends Map<?, ?>> map = container.getMap(RPGKeys.ATTRIBUTES.getQuery());

        if (!map.isPresent()) {
            return Optional.of(this);
        }

        map.get().forEach((k,v) -> {
            Optional<AttributeType> attributeType = Sponge.getRegistry().getType(AttributeType.class, (String) k);

            if (!attributeType.isPresent()) {
                return;
            }

            attributes.put(attributeType.get(), (Double) v);
        });

        return Optional.of(this);
    }

    @Override
    public AttributeMapData copy() {
        return new AttributeMapData(attributes);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(attributes);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(RPGKeys.ATTRIBUTES.getQuery(), attributes);
    }

    public void setAttribute(AttributeType attributeType, Double amount) {
        attributes.put(attributeType, amount);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2020-11-29T10:49:53.574Z")
    public static class Immutable extends AbstractImmutableData<Immutable, AttributeMapData> {

        private Map<AttributeType, Double> attributes;
        {
            registerGetters();
        }

        Immutable() {
        }

        Immutable(Map<AttributeType, Double> attributes) {
            this.attributes = attributes;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(RPGKeys.ATTRIBUTES, this::getAttributes);
            registerKeyValue(RPGKeys.ATTRIBUTES, this::attributes);
        }

        public Map<AttributeType, Double> getAttributes() {
            return attributes;
        }

        public ImmutableMapValue<AttributeType, Double> attributes() {
            return Sponge.getRegistry().getValueFactory().createMapValue(RPGKeys.ATTRIBUTES, attributes).asImmutable();
        }

        @Override
        public AttributeMapData asMutable() {
            return new AttributeMapData(attributes);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer().set(RPGKeys.ATTRIBUTES.getQuery(), attributes);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2020-11-29T10:49:53.576Z")
    public static class Builder extends AbstractDataBuilder<AttributeMapData> implements DataManipulatorBuilder<AttributeMapData, Immutable> {

        public Builder() {
            super(AttributeMapData.class, 1);
        }

        @Override
        public AttributeMapData create() {
            return new AttributeMapData();
        }

        @Override
        public Optional<AttributeMapData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<AttributeMapData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }

    }
}
