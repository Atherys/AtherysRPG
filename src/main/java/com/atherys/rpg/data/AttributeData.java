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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttributeData extends AbstractData<AttributeData, AttributeData.Immutable> {

    private Double dexterity = 0.0d;

    private Double constitution = 0.0d;

    private Double intelligence = 0.0d;

    private Double strength = 0.0d;

    private Double wisdom = 0.0d;

    private Double magicalResistance = 0.0d;

    private Double physicalResistance = 0.0d;

    private Double baseArmor = 0.0d;

    private Double baseDamage = 0.0d;

    public AttributeData() {
        registerGettersAndSetters();
    }

    AttributeData(Double dexterity, Double constitution, Double intelligence, Double strength, Double wisdom, Double magicalResistance, Double physicalResistance, Double baseArmor, Double baseDamage) {
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.strength = strength;
        this.wisdom = wisdom;
        this.physicalResistance = physicalResistance;
        this.magicalResistance = magicalResistance;
        this.baseArmor = baseArmor;
        this.baseDamage = baseDamage;
        registerGettersAndSetters();
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(AttributeKeys.DEXTERITY, this::getDexterity);
        registerFieldSetter(AttributeKeys.DEXTERITY, this::setDexterity);
        registerKeyValue(AttributeKeys.DEXTERITY, this::dexterity);

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

        registerFieldGetter(AttributeKeys.BASE_ARMOR, this::getBaseArmor);
        registerFieldSetter(AttributeKeys.BASE_ARMOR, this::setBaseArmor);
        registerKeyValue(AttributeKeys.BASE_ARMOR, this::baseArmor);

        registerFieldGetter(AttributeKeys.BASE_DAMAGE, this::getBaseDamage);
        registerFieldSetter(AttributeKeys.BASE_DAMAGE, this::setBaseDamage);
        registerKeyValue(AttributeKeys.BASE_DAMAGE, this::baseDamage);
    }

    public Double getDexterity() {
        return dexterity;
    }

    public void setDexterity(Double dexterity) {
        this.dexterity = dexterity;
    }

    public Value<Double> dexterity() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.DEXTERITY, dexterity);
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

    public Double getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(Double baseArmor) {
        this.baseArmor = baseArmor;
    }

    public Value<Double> baseArmor() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.BASE_ARMOR, baseArmor);
    }

    public Double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(Double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public Value<Double> baseDamage() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.BASE_DAMAGE, baseDamage);
    }

    public Double getAttribute(AttributeType type) {
        switch (type.getShortName()) {
            case "dex":
                return getDexterity();
            case "con":
                return getConstitution();
            case "int":
                return getIntelligence();
            case "str":
                return getStrength();
            case "wis":
                return getWisdom();
            case "magicres":
                return getMagicalResistance();
            case "physres":
                return getPhysicalResistance();
            case "armor":
                return getBaseArmor();
            case "dmg":
                return getBaseDamage();
            default:
                return 0.0d;
        }
    }

    public void setAttribute(AttributeType type, Double value) {
        switch (type.getShortName()) {
            case "dex":
                setDexterity(value);
                break;
            case "con":
                setConstitution(value);
                break;
            case "int":
                setIntelligence(value);
                break;
            case "str":
                setStrength(value);
                break;
            case "wis":
                setWisdom(value);
                break;
            case "magicres":
                setMagicalResistance(value);
                break;
            case "physres":
                setPhysicalResistance(value);
                break;
            case "armor":
                setBaseArmor(value);
            case "dmg":
                setBaseDamage(value);
        }
    }

    public Map<AttributeType, Double> asMap() {
        Map<AttributeType, Double> map = new HashMap<>();

        map.put(AttributeTypes.DEXTERITY, dexterity);
        map.put(AttributeTypes.CONSTITUTION, constitution);
        map.put(AttributeTypes.INTELLIGENCE, intelligence);
        map.put(AttributeTypes.STRENGTH, strength);
        map.put(AttributeTypes.WISDOM, wisdom);
        map.put(AttributeTypes.MAGICAL_RESISTANCE, magicalResistance);
        map.put(AttributeTypes.PHYSICAL_RESISTANCE, physicalResistance);
        map.put(AttributeTypes.BASE_ARMOR, baseArmor);
        map.put(AttributeTypes.BASE_DAMAGE, baseDamage);

        return map;
    }

    @Override
    public Optional<AttributeData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(AttributeData.class).ifPresent(that -> {
            AttributeData data = overlap.merge(this, that);
            this.dexterity = data.dexterity;
            this.constitution = data.constitution;
            this.intelligence = data.intelligence;
            this.strength = data.strength;
            this.wisdom = data.wisdom;
            this.magicalResistance = data.magicalResistance;
            this.physicalResistance = data.physicalResistance;
            this.baseArmor = data.baseArmor;
            this.baseDamage = data.baseDamage;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<AttributeData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<AttributeData> from(DataView container) {
        container.getDouble(AttributeKeys.DEXTERITY.getQuery()).ifPresent(v -> dexterity = v);
        container.getDouble(AttributeKeys.CONSTITUTION.getQuery()).ifPresent(v -> constitution = v);
        container.getDouble(AttributeKeys.INTELLIGENCE.getQuery()).ifPresent(v -> intelligence = v);
        container.getDouble(AttributeKeys.STRENGTH.getQuery()).ifPresent(v -> strength = v);
        container.getDouble(AttributeKeys.WISDOM.getQuery()).ifPresent(v -> wisdom = v);
        container.getDouble(AttributeKeys.MAGICAL_RESISTANCE.getQuery()).ifPresent(v -> magicalResistance = v);
        container.getDouble(AttributeKeys.PHYSICAL_RESISTANCE.getQuery()).ifPresent(v -> physicalResistance = v);
        container.getDouble(AttributeKeys.BASE_ARMOR.getQuery()).ifPresent(v -> baseArmor = v);
        container.getDouble(AttributeKeys.BASE_DAMAGE.getQuery()).ifPresent(v -> baseDamage = v);
        return Optional.of(this);
    }

    @Override
    public AttributeData copy() {
        return new AttributeData(dexterity, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance, baseArmor, baseDamage);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(dexterity, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance, baseArmor, baseDamage);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(AttributeKeys.DEXTERITY.getQuery(), dexterity)
                .set(AttributeKeys.CONSTITUTION.getQuery(), constitution)
                .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                .set(AttributeKeys.STRENGTH.getQuery(), strength)
                .set(AttributeKeys.WISDOM.getQuery(), wisdom)
                .set(AttributeKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                .set(AttributeKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance)
                .set(AttributeKeys.BASE_ARMOR.getQuery(), baseArmor)
                .set(AttributeKeys.BASE_DAMAGE.getQuery(), baseDamage);
    }

    public static class Immutable extends AbstractImmutableData<Immutable, AttributeData> {

        private Double dexterity = 0.0d;

        private Double constitution = 0.0d;

        private Double intelligence = 0.0d;

        private Double strength = 0.0d;

        private Double wisdom = 0.0d;

        private Double magicalResistance = 0.0d;

        private Double physicalResistance = 0.0d;

        private Double baseArmor = 0.0d;

        private Double baseDamage = 0.0d;

        Immutable() {
            registerGetters();
        }

        Immutable(Double dexterity, Double constitution, Double intelligence, Double strength, Double wisdom, Double magicalResistance, Double physicalResistance, Double baseArmor, Double baseDamage) {
            this.dexterity = dexterity;
            this.constitution = constitution;
            this.intelligence = intelligence;
            this.strength = strength;
            this.wisdom = wisdom;
            this.magicalResistance = magicalResistance;
            this.physicalResistance = physicalResistance;
            this.baseArmor = baseArmor;
            this.baseDamage = baseDamage;
            registerGetters();
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(AttributeKeys.DEXTERITY, this::getDexterity);
            registerKeyValue(AttributeKeys.DEXTERITY, this::dexterity);

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

            registerFieldGetter(AttributeKeys.MAGICAL_RESISTANCE, this::getMagicalResistance);
            registerKeyValue(AttributeKeys.MAGICAL_RESISTANCE, this::magicalResistance);

            registerFieldGetter(AttributeKeys.PHYSICAL_RESISTANCE, this::getPhysicalResistance);
            registerKeyValue(AttributeKeys.PHYSICAL_RESISTANCE, this::physicalResistance);

            registerFieldGetter(AttributeKeys.BASE_ARMOR, this::getBaseArmor);
            registerKeyValue(AttributeKeys.BASE_ARMOR, this::baseArmor);

            registerFieldGetter(AttributeKeys.BASE_DAMAGE, this::getBaseDamage);
            registerKeyValue(AttributeKeys.BASE_DAMAGE, this::baseDamage);
        }

        public Double getDexterity() {
            return dexterity;
        }

        public ImmutableValue<Double> dexterity() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.DEXTERITY, dexterity).asImmutable();
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

        public Double getBaseArmor() {
            return baseArmor;
        }

        public ImmutableValue<Double> baseArmor() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.BASE_ARMOR, baseArmor).asImmutable();
        }

        public Double getBaseDamage() {
            return baseDamage;
        }

        public ImmutableValue<Double> baseDamage() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.BASE_DAMAGE, baseDamage).asImmutable();
        }

        @Override
        public AttributeData asMutable() {
            return new AttributeData(dexterity, constitution, intelligence, strength, wisdom, magicalResistance, physicalResistance, baseArmor, baseDamage);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(AttributeKeys.DEXTERITY.getQuery(), dexterity)
                    .set(AttributeKeys.CONSTITUTION.getQuery(), constitution)
                    .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                    .set(AttributeKeys.STRENGTH.getQuery(), strength)
                    .set(AttributeKeys.WISDOM.getQuery(), wisdom)
                    .set(AttributeKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                    .set(AttributeKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance)
                    .set(AttributeKeys.BASE_ARMOR.getQuery(), baseArmor)
                    .set(AttributeKeys.BASE_DAMAGE.getQuery(), baseDamage);
        }

    }

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
