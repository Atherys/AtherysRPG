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
        registerFieldGetter(RPGKeys.DEXTERITY, this::getDexterity);
        registerFieldSetter(RPGKeys.DEXTERITY, this::setDexterity);
        registerKeyValue(RPGKeys.DEXTERITY, this::dexterity);

        registerFieldGetter(RPGKeys.CONSTITUTION, this::getConstitution);
        registerFieldSetter(RPGKeys.CONSTITUTION, this::setConstitution);
        registerKeyValue(RPGKeys.CONSTITUTION, this::constitution);

        registerFieldGetter(RPGKeys.INTELLIGENCE, this::getIntelligence);
        registerFieldSetter(RPGKeys.INTELLIGENCE, this::setIntelligence);
        registerKeyValue(RPGKeys.INTELLIGENCE, this::intelligence);

        registerFieldGetter(RPGKeys.STRENGTH, this::getStrength);
        registerFieldSetter(RPGKeys.STRENGTH, this::setStrength);
        registerKeyValue(RPGKeys.STRENGTH, this::strength);

        registerFieldGetter(RPGKeys.WISDOM, this::getWisdom);
        registerFieldSetter(RPGKeys.WISDOM, this::setWisdom);
        registerKeyValue(RPGKeys.WISDOM, this::wisdom);

        registerFieldGetter(RPGKeys.MAGICAL_RESISTANCE, this::getMagicalResistance);
        registerFieldSetter(RPGKeys.MAGICAL_RESISTANCE, this::setMagicalResistance);
        registerKeyValue(RPGKeys.MAGICAL_RESISTANCE, this::magicalResistance);

        registerFieldGetter(RPGKeys.PHYSICAL_RESISTANCE, this::getPhysicalResistance);
        registerFieldSetter(RPGKeys.PHYSICAL_RESISTANCE, this::setPhysicalResistance);
        registerKeyValue(RPGKeys.PHYSICAL_RESISTANCE, this::physicalResistance);

        registerFieldGetter(RPGKeys.BASE_ARMOR, this::getBaseArmor);
        registerFieldSetter(RPGKeys.BASE_ARMOR, this::setBaseArmor);
        registerKeyValue(RPGKeys.BASE_ARMOR, this::baseArmor);

        registerFieldGetter(RPGKeys.BASE_DAMAGE, this::getBaseDamage);
        registerFieldSetter(RPGKeys.BASE_DAMAGE, this::setBaseDamage);
        registerKeyValue(RPGKeys.BASE_DAMAGE, this::baseDamage);
    }

    public Double getDexterity() {
        return dexterity;
    }

    public void setDexterity(Double dexterity) {
        this.dexterity = dexterity;
    }

    public Value<Double> dexterity() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.DEXTERITY, dexterity);
    }

    public Double getConstitution() {
        return constitution;
    }

    public void setConstitution(Double constitution) {
        this.constitution = constitution;
    }

    public Value<Double> constitution() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.CONSTITUTION, constitution);
    }

    public Double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Double intelligence) {
        this.intelligence = intelligence;
    }

    public Value<Double> intelligence() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.INTELLIGENCE, intelligence);
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public Value<Double> strength() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.STRENGTH, strength);
    }

    public Double getMagicalResistance() {
        return magicalResistance;
    }

    public void setMagicalResistance(Double magicalResistance) {
        this.magicalResistance = magicalResistance;
    }

    public Value<Double> magicalResistance() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.MAGICAL_RESISTANCE, magicalResistance);
    }

    public Double getPhysicalResistance() {
        return physicalResistance;
    }

    public void setPhysicalResistance(Double physicalResistance) {
        this.physicalResistance = physicalResistance;
    }

    public Value<Double> physicalResistance() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.PHYSICAL_RESISTANCE, physicalResistance);
    }

    public Double getWisdom() {
        return wisdom;
    }

    public void setWisdom(Double wisdom) {
        this.wisdom = wisdom;
    }

    public Value<Double> wisdom() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.WISDOM, wisdom);
    }

    public Double getBaseArmor() {
        return baseArmor;
    }

    public void setBaseArmor(Double baseArmor) {
        this.baseArmor = baseArmor;
    }

    public Value<Double> baseArmor() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.BASE_ARMOR, baseArmor);
    }

    public Double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(Double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public Value<Double> baseDamage() {
        return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.BASE_DAMAGE, baseDamage);
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
                break;
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
        container.getDouble(RPGKeys.DEXTERITY.getQuery()).ifPresent(v -> dexterity = v);
        container.getDouble(RPGKeys.CONSTITUTION.getQuery()).ifPresent(v -> constitution = v);
        container.getDouble(RPGKeys.INTELLIGENCE.getQuery()).ifPresent(v -> intelligence = v);
        container.getDouble(RPGKeys.STRENGTH.getQuery()).ifPresent(v -> strength = v);
        container.getDouble(RPGKeys.WISDOM.getQuery()).ifPresent(v -> wisdom = v);
        container.getDouble(RPGKeys.MAGICAL_RESISTANCE.getQuery()).ifPresent(v -> magicalResistance = v);
        container.getDouble(RPGKeys.PHYSICAL_RESISTANCE.getQuery()).ifPresent(v -> physicalResistance = v);
        container.getDouble(RPGKeys.BASE_ARMOR.getQuery()).ifPresent(v -> baseArmor = v);
        container.getDouble(RPGKeys.BASE_DAMAGE.getQuery()).ifPresent(v -> baseDamage = v);
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
                .set(RPGKeys.DEXTERITY.getQuery(), dexterity)
                .set(RPGKeys.CONSTITUTION.getQuery(), constitution)
                .set(RPGKeys.INTELLIGENCE.getQuery(), intelligence)
                .set(RPGKeys.STRENGTH.getQuery(), strength)
                .set(RPGKeys.WISDOM.getQuery(), wisdom)
                .set(RPGKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                .set(RPGKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance)
                .set(RPGKeys.BASE_ARMOR.getQuery(), baseArmor)
                .set(RPGKeys.BASE_DAMAGE.getQuery(), baseDamage);
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
            registerFieldGetter(RPGKeys.DEXTERITY, this::getDexterity);
            registerKeyValue(RPGKeys.DEXTERITY, this::dexterity);

            registerFieldGetter(RPGKeys.CONSTITUTION, this::getConstitution);
            registerKeyValue(RPGKeys.CONSTITUTION, this::constitution);

            registerFieldGetter(RPGKeys.INTELLIGENCE, this::getIntelligence);
            registerKeyValue(RPGKeys.INTELLIGENCE, this::intelligence);

            registerFieldGetter(RPGKeys.STRENGTH, this::getStrength);
            registerKeyValue(RPGKeys.STRENGTH, this::strength);

            registerFieldGetter(RPGKeys.WISDOM, this::getWisdom);
            registerKeyValue(RPGKeys.WISDOM, this::wisdom);

            registerFieldGetter(RPGKeys.WISDOM, this::getWisdom);
            registerKeyValue(RPGKeys.WISDOM, this::wisdom);

            registerFieldGetter(RPGKeys.WISDOM, this::getWisdom);
            registerKeyValue(RPGKeys.WISDOM, this::wisdom);

            registerFieldGetter(RPGKeys.MAGICAL_RESISTANCE, this::getMagicalResistance);
            registerKeyValue(RPGKeys.MAGICAL_RESISTANCE, this::magicalResistance);

            registerFieldGetter(RPGKeys.PHYSICAL_RESISTANCE, this::getPhysicalResistance);
            registerKeyValue(RPGKeys.PHYSICAL_RESISTANCE, this::physicalResistance);

            registerFieldGetter(RPGKeys.BASE_ARMOR, this::getBaseArmor);
            registerKeyValue(RPGKeys.BASE_ARMOR, this::baseArmor);

            registerFieldGetter(RPGKeys.BASE_DAMAGE, this::getBaseDamage);
            registerKeyValue(RPGKeys.BASE_DAMAGE, this::baseDamage);
        }

        public Double getDexterity() {
            return dexterity;
        }

        public ImmutableValue<Double> dexterity() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.DEXTERITY, dexterity).asImmutable();
        }

        public Double getConstitution() {
            return constitution;
        }

        public ImmutableValue<Double> constitution() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.CONSTITUTION, constitution).asImmutable();
        }

        public Double getIntelligence() {
            return intelligence;
        }

        public ImmutableValue<Double> intelligence() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.INTELLIGENCE, intelligence).asImmutable();
        }

        public Double getStrength() {
            return strength;
        }

        public ImmutableValue<Double> strength() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.STRENGTH, strength).asImmutable();
        }

        public Double getWisdom() {
            return wisdom;
        }

        public ImmutableValue<Double> wisdom() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.WISDOM, wisdom).asImmutable();
        }

        public Double getMagicalResistance() {
            return magicalResistance;
        }

        public ImmutableValue<Double> magicalResistance() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.MAGICAL_RESISTANCE, magicalResistance).asImmutable();
        }

        public Double getPhysicalResistance() {
            return physicalResistance;
        }

        public ImmutableValue<Double> physicalResistance() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.PHYSICAL_RESISTANCE, physicalResistance).asImmutable();
        }

        public Double getBaseArmor() {
            return baseArmor;
        }

        public ImmutableValue<Double> baseArmor() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.BASE_ARMOR, baseArmor).asImmutable();
        }

        public Double getBaseDamage() {
            return baseDamage;
        }

        public ImmutableValue<Double> baseDamage() {
            return Sponge.getRegistry().getValueFactory().createValue(RPGKeys.BASE_DAMAGE, baseDamage).asImmutable();
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
                    .set(RPGKeys.DEXTERITY.getQuery(), dexterity)
                    .set(RPGKeys.CONSTITUTION.getQuery(), constitution)
                    .set(RPGKeys.INTELLIGENCE.getQuery(), intelligence)
                    .set(RPGKeys.STRENGTH.getQuery(), strength)
                    .set(RPGKeys.WISDOM.getQuery(), wisdom)
                    .set(RPGKeys.MAGICAL_RESISTANCE.getQuery(), magicalResistance)
                    .set(RPGKeys.PHYSICAL_RESISTANCE.getQuery(), physicalResistance)
                    .set(RPGKeys.BASE_ARMOR.getQuery(), baseArmor)
                    .set(RPGKeys.BASE_DAMAGE.getQuery(), baseDamage);
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
