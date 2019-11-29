package com.atherys.rpg.data;

import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.api.stat.AttributeTypes;
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

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.723Z")
public class AttributeData extends AbstractData<AttributeData, AttributeData.Immutable> {

    private Double agility = 0.0d;

    private Double constitution = 0.0d;

    private Double intelligence = 0.0d;

    private Double strength = 0.0d;

    private Double wisdom = 0.0d;

    private Double magicalResistance = 0.0d;

    private Double physicalResistance = 0.0d;

    public AttributeData() {
        registerGettersAndSetters();
    }

    AttributeData(Double agility, Double constitution, Double intelligence, Double strength, Double wisdom, Double magicalResistance, Double physicalResistance) {
        this.agility = agility;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.strength = strength;
        this.wisdom = wisdom;
        this.physicalResistance = physicalResistance;
        this.magicalResistance = magicalResistance;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(AttributeKeys.AGILITY, this::getAgility);
        registerFieldSetter(AttributeKeys.AGILITY, this::setAgility);
        registerKeyValue(AttributeKeys.AGILITY, this::agility);

        registerFieldGetter(AttributeKeys.CONSTITUTION, this::getConstitution);
        registerFieldSetter(AttributeKeys.CONSTITUTION, this::setConstitution);
        registerKeyValue(AttributeKeys.CONSTITUTION, this::constitution);

        registerFieldGetter(AttributeKeys.INTELLIGENCE, this::getIntelligence);
        registerFieldSetter(AttributeKeys.INTELLIGENCE, this::setIntelligence);
        registerKeyValue(AttributeKeys.INTELLIGENCE, this::intelligence);

        registerFieldGetter(AttributeKeys.STRENGTH, this::getStrength);
        registerFieldSetter(AttributeKeys.STRENGTH, this::setStrength);
        registerKeyValue(AttributeKeys.STRENGTH, this::strength);

        registerFieldGetter(AttributeKeys.WISDOM, this::getWisdom);
        registerFieldSetter(AttributeKeys.WISDOM, this::setWisdom);
        registerKeyValue(AttributeKeys.WISDOM, this::wisdom);

        registerFieldGetter(AttributeKeys.MAGICAL_RESISTANCE, this::getMagicalResistance);
        registerFieldSetter(AttributeKeys.MAGICAL_RESISTANCE, this::setMagicalResistance);
        registerKeyValue(AttributeKeys.MAGICAL_RESISTANCE, this::magicalResistance);

        registerFieldGetter(AttributeKeys.PHYSICAL_RESISTANCE, this::getPhysicalResistance);
        registerFieldSetter(AttributeKeys.PHYSICAL_RESISTANCE, this::setPhysicalResistance);
        registerKeyValue(AttributeKeys.PHYSICAL_RESISTANCE, this::physicalResistance);
    }

    public Double getAgility() {
        return agility;
    }

    public void setAgility(Double agility) {
        this.agility = agility;
    }

    public Value<Double> agility() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.AGILITY, agility);
    }

    public Double getConstitution() {
        return constitution;
    }

    public void setConstitution(Double constitution) {
        this.constitution = constitution;
    }

    public Value<Double> constitution() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.CONSTITUTION, constitution);
    }

    public Double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Double intelligence) {
        this.intelligence = intelligence;
    }

    public Value<Double> intelligence() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.INTELLIGENCE, intelligence);
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public Value<Double> strength() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.STRENGTH, strength);
    }

    public Double getMagicalResistance() {
        return magicalResistance;
    }

    public void setMagicalResistance(Double magicalResistance) {
        this.magicalResistance = magicalResistance;
    }

    public Value<Double> magicalResistance() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.MAGICAL_RESISTANCE, magicalResistance);
    }

    public Double getPhysicalResistance() {
        return physicalResistance;
    }

    public void setPhysicalResistance(Double physicalResistance) {
        this.wisdom = wisdom;
    }

    public Value<Double> physicalResistance() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.PHYSICAL_RESISTANCE, physicalResistance);
    }

    public Double getWisdom() {
        return wisdom;
    }

    public void setWisdom(Double wisdom) {
        this.wisdom = wisdom;
    }

    public Value<Double> wisdom() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.WISDOM, wisdom);
    }

    public Double getAttribute(AttributeType type) {
        switch (type.getId()) {
            case "agility":
                return getAgility();
            case "constitution":
                return getConstitution();
            case "intelligence":
                return getIntelligence();
            case "perception":
                return getStrength();
            case "wisdom":
                return getWisdom();
            case "magical-resistance":
                return getMagicalResistance();
            case "physical-resistance":
                return getPhysicalResistance();
            default:
                return 0.0d;
        }
    }

    public void setAttribute(AttributeType type, Double value) {
        switch (type.getId()) {
            case "agility":
                setAgility(value);
                break;
            case "constitution":
                setConstitution(value);
                break;
            case "intelligence":
                setIntelligence(value);
                break;
            case "strength":
                setStrength(value);
                break;
            case "wisdom":
                setWisdom(value);
                break;
            case "magical-resistance":
                setMagicalResistance(value);
                break;
            case "physical-resistance":
                setPhysicalResistance(value);
                break;
        }
    }

    public Map<AttributeType, Double> asMap() {
        Map<AttributeType, Double> map = new HashMap<>();

        map.put(AttributeTypes.AGILITY, agility);
        map.put(AttributeTypes.CONSTITUTION, constitution);
        map.put(AttributeTypes.INTELLIGENCE, intelligence);
        map.put(AttributeTypes.STRENGTH, strength);
        map.put(AttributeTypes.WISDOM, wisdom);

        return map;
    }

    @Override
    public Optional<AttributeData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(AttributeData.class).ifPresent(that -> {
            AttributeData data = overlap.merge(this, that);
            this.agility = data.agility;
            this.constitution = data.constitution;
            this.intelligence = data.intelligence;
            this.strength = data.strength;
            this.wisdom = data.wisdom;
            this.magicalResistance = data.magicalResistance;
            this.physicalResistance = data.physicalResistance;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<AttributeData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<AttributeData> from(DataView container) {
        container.getDouble(AttributeKeys.AGILITY.getQuery()).ifPresent(v -> agility = v);
        container.getDouble(AttributeKeys.CONSTITUTION.getQuery()).ifPresent(v -> constitution = v);
        container.getDouble(AttributeKeys.INTELLIGENCE.getQuery()).ifPresent(v -> intelligence = v);
        container.getDouble(AttributeKeys.STRENGTH.getQuery()).ifPresent(v -> strength = v);
        container.getDouble(AttributeKeys.WISDOM.getQuery()).ifPresent(v -> wisdom = v);
        container.getDouble(AttributeKeys.MAGICAL_RESISTANCE.getQuery()).ifPresent(v -> magicalResistance = v);
        container.getDouble(AttributeKeys.PHYSICAL_RESISTANCE.getQuery()).ifPresent(v -> physicalResistance = v);
        return Optional.of(this);
    }

    @Override
    public AttributeData copy() {
        return new AttributeData(agility, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(agility, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(AttributeKeys.AGILITY.getQuery(), agility)
                .set(AttributeKeys.CONSTITUTION.getQuery(), constitution)
                .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                .set(AttributeKeys.STRENGTH.getQuery(), strength)
                .set(AttributeKeys.WISDOM.getQuery(), wisdom)
                .set(AttributeKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                .set(AttributeKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.795Z")
    public static class Immutable extends AbstractImmutableData<Immutable, AttributeData> {

        private Double agility = 0.0d;

        private Double constitution = 0.0d;

        private Double intelligence = 0.0d;

        private Double strength = 0.0d;

        private Double wisdom = 0.0d;

        private Double magicalResistance = 0.0d;

        private Double physicalResistance = 0.0d;

        Immutable() {
            registerGetters();
        }

        Immutable(Double agility, Double constitution, Double intelligence, Double strength, Double wisdom, Double magicalResistance, Double physicalResistance) {
            this.agility = agility;
            this.constitution = constitution;
            this.intelligence = intelligence;
            this.strength = strength;
            this.wisdom = wisdom;
            this.magicalResistance = magicalResistance;
            this.physicalResistance = physicalResistance;
            registerGetters();
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(AttributeKeys.AGILITY, this::getAgility);
            registerKeyValue(AttributeKeys.AGILITY, this::agility);

            registerFieldGetter(AttributeKeys.CONSTITUTION, this::getConstitution);
            registerKeyValue(AttributeKeys.CONSTITUTION, this::constitution);

            registerFieldGetter(AttributeKeys.INTELLIGENCE, this::getIntelligence);
            registerKeyValue(AttributeKeys.INTELLIGENCE, this::intelligence);

            registerFieldGetter(AttributeKeys.STRENGTH, this::getStrength);
            registerKeyValue(AttributeKeys.STRENGTH, this::strength);

            registerFieldGetter(AttributeKeys.WISDOM, this::getWisdom);
            registerKeyValue(AttributeKeys.WISDOM, this::wisdom);

            registerFieldGetter(AttributeKeys.WISDOM, this::getWisdom);
            registerKeyValue(AttributeKeys.WISDOM, this::wisdom);

            registerFieldGetter(AttributeKeys.WISDOM, this::getWisdom);
            registerKeyValue(AttributeKeys.WISDOM, this::wisdom);
        }

        public Double getAgility() {
            return agility;
        }

        public ImmutableValue<Double> agility() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.AGILITY, agility).asImmutable();
        }

        public Double getConstitution() {
            return constitution;
        }

        public ImmutableValue<Double> constitution() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.CONSTITUTION, constitution).asImmutable();
        }

        public Double getIntelligence() {
            return intelligence;
        }

        public ImmutableValue<Double> intelligence() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.INTELLIGENCE, intelligence).asImmutable();
        }

        public Double getStrength() {
            return strength;
        }

        public ImmutableValue<Double> strength() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.STRENGTH, strength).asImmutable();
        }

        public Double getWisdom() {
            return wisdom;
        }

        public ImmutableValue<Double> wisdom() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.WISDOM, wisdom).asImmutable();
        }

        public Double getMagicalResistance() {
            return magicalResistance;
        }

        public ImmutableValue<Double> magicalResistance() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.MAGICAL_RESISTANCE, magicalResistance).asImmutable();
        }

        public Double getPhysicalResistance() {
            return physicalResistance;
        }

        public ImmutableValue<Double> physicalResistance() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.PHYSICAL_RESISTANCE, physicalResistance).asImmutable();
        }

        @Override
        public AttributeData asMutable() {
            return new AttributeData(agility, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(AttributeKeys.AGILITY.getQuery(), agility)
                    .set(AttributeKeys.CONSTITUTION.getQuery(), constitution)
                    .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                    .set(AttributeKeys.STRENGTH.getQuery(), strength)
                    .set(AttributeKeys.WISDOM.getQuery(), wisdom)
                    .set(AttributeKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                    .set(AttributeKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.811Z")
    public static class Builder extends AbstractDataBuilder<AttributeData> implements DataManipulatorBuilder<AttributeData, Immutable> {

        public Builder() {
            super(AttributeData.class, 1);
        }

        @Override
        public AttributeData create() {
            return new AttributeData();
        }

        @Override
        public Optional<AttributeData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<AttributeData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }

    }
}
