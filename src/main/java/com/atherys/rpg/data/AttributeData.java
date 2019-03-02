package com.atherys.rpg.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

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

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.723Z")
public class AttributeData extends AbstractData<AttributeData, AttributeData.Immutable> {

    private Double agility = 0.0d;

    private Double constitution = 0.0d;

    private Double charisma = 0.0d;

    private Double defense = 0.0d;

    private Double intelligence = 0.0d;

    private Double luck = 0.0d;

    private Double perception = 0.0d;

    private Double strength = 0.0d;

    private Double willpower = 0.0d;

    private Double wisdom = 0.0d;

    public AttributeData() {
        registerGettersAndSetters();
    }

    AttributeData(Double agility, Double constitution, Double charisma, Double defense, Double intelligence, Double luck, Double perception, Double strength, Double willpower, Double wisdom) {
        this.agility = agility;
        this.constitution = constitution;
        this.charisma = charisma;
        this.defense = defense;
        this.intelligence = intelligence;
        this.luck = luck;
        this.perception = perception;
        this.strength = strength;
        this.willpower = willpower;
        this.wisdom = wisdom;
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

        registerFieldGetter(AttributeKeys.CHARISMA, this::getCharisma);
        registerFieldSetter(AttributeKeys.CHARISMA, this::setCharisma);
        registerKeyValue(AttributeKeys.CHARISMA, this::charisma);

        registerFieldGetter(AttributeKeys.DEFENSE, this::getDefense);
        registerFieldSetter(AttributeKeys.DEFENSE, this::setDefense);
        registerKeyValue(AttributeKeys.DEFENSE, this::defense);

        registerFieldGetter(AttributeKeys.INTELLIGENCE, this::getIntelligence);
        registerFieldSetter(AttributeKeys.INTELLIGENCE, this::setIntelligence);
        registerKeyValue(AttributeKeys.INTELLIGENCE, this::intelligence);

        registerFieldGetter(AttributeKeys.LUCK, this::getLuck);
        registerFieldSetter(AttributeKeys.LUCK, this::setLuck);
        registerKeyValue(AttributeKeys.LUCK, this::luck);

        registerFieldGetter(AttributeKeys.PERCEPTION, this::getPerception);
        registerFieldSetter(AttributeKeys.PERCEPTION, this::setPerception);
        registerKeyValue(AttributeKeys.PERCEPTION, this::perception);

        registerFieldGetter(AttributeKeys.STRENGTH, this::getStrength);
        registerFieldSetter(AttributeKeys.STRENGTH, this::setStrength);
        registerKeyValue(AttributeKeys.STRENGTH, this::strength);

        registerFieldGetter(AttributeKeys.WILLPOWER, this::getWillpower);
        registerFieldSetter(AttributeKeys.WILLPOWER, this::setWillpower);
        registerKeyValue(AttributeKeys.WILLPOWER, this::willpower);

        registerFieldGetter(AttributeKeys.WISDOM, this::getWisdom);
        registerFieldSetter(AttributeKeys.WISDOM, this::setWisdom);
        registerKeyValue(AttributeKeys.WISDOM, this::wisdom);
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

    public Double getCharisma() {
        return charisma;
    }

    public void setCharisma(Double charisma) {
        this.charisma = charisma;
    }

    public Value<Double> charisma() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.CHARISMA, charisma);
    }

    public Double getDefense() {
        return defense;
    }

    public void setDefense(Double defense) {
        this.defense = defense;
    }

    public Value<Double> defense() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.DEFENSE, defense);
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

    public Double getLuck() {
        return luck;
    }

    public void setLuck(Double luck) {
        this.luck = luck;
    }

    public Value<Double> luck() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.LUCK, luck);
    }

    public Double getPerception() {
        return perception;
    }

    public void setPerception(Double perception) {
        this.perception = perception;
    }

    public Value<Double> perception() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.PERCEPTION, perception);
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

    public Double getWillpower() {
        return willpower;
    }

    public void setWillpower(Double willpower) {
        this.willpower = willpower;
    }

    public Value<Double> willpower() {
        return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.WILLPOWER, willpower);
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
        switch(type.getId()) {
            case "agility":
                return getAgility();
            case "charisma":
                return getCharisma();
            case "constitution":
                return getConstitution();
            case "defense":
                return getDefense();
            case "intelligence":
                return getIntelligence();
            case "luck":
                return getLuck();
            case "perception":
                return getPerception();
            case "strength":
                return getStrength();
            case "willpower":
                return getWillpower();
            case "wisdom":
                return getWisdom();
            default:
                return 0.0d;
        }
    }

    public void setAttribute(AttributeType type, Double value) {
        switch(type.getId()) {
            case "agility":
                setAgility(value);
                break;
            case "charisma":
                setCharisma(value);
                break;
            case "constitution":
                setConstitution(value);
                break;
            case "defense":
                setDefense(value);
                break;
            case "intelligence":
                setIntelligence(value);
                break;
            case "luck":
                setLuck(value);
                break;
            case "perception":
                setPerception(value);
                break;
            case "strength":
                setStrength(value);
                break;
            case "willpower":
                setWillpower(value);
                break;
            case "wisdom":
                setWisdom(value);
                break;
        }
    }

    public Map<AttributeType, Double> asMap() {
        Map<AttributeType, Double> map = new HashMap<>();

        map.put(AttributeTypes.AGILITY, agility);
        map.put(AttributeTypes.CHARISMA, charisma);
        map.put(AttributeTypes.CONSTITUTION, constitution);
        map.put(AttributeTypes.DEFENSE, defense);
        map.put(AttributeTypes.INTELLIGENCE, intelligence);
        map.put(AttributeTypes.LUCK, luck);
        map.put(AttributeTypes.PERCEPTION, perception);
        map.put(AttributeTypes.STRENGTH, strength);
        map.put(AttributeTypes.WILLPOWER, willpower);
        map.put(AttributeTypes.WISDOM, wisdom);

        return map;
    }

    @Override
    public Optional<AttributeData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(AttributeData.class).ifPresent(that -> {
                AttributeData data = overlap.merge(this, that);
                this.agility = data.agility;
                this.constitution = data.constitution;
                this.charisma = data.charisma;
                this.defense = data.defense;
                this.intelligence = data.intelligence;
                this.luck = data.luck;
                this.perception = data.perception;
                this.strength = data.strength;
                this.willpower = data.willpower;
                this.wisdom = data.wisdom;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<AttributeData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<AttributeData> from(DataView container) {
        container.getObject(AttributeKeys.AGILITY.getQuery(), Double.class).ifPresent(v -> agility = v);
        container.getObject(AttributeKeys.CONSTITUTION.getQuery(), Double.class).ifPresent(v -> constitution = v);
        container.getObject(AttributeKeys.CHARISMA.getQuery(), Double.class).ifPresent(v -> charisma = v);
        container.getObject(AttributeKeys.DEFENSE.getQuery(), Double.class).ifPresent(v -> defense = v);
        container.getObject(AttributeKeys.INTELLIGENCE.getQuery(), Double.class).ifPresent(v -> intelligence = v);
        container.getObject(AttributeKeys.LUCK.getQuery(), Double.class).ifPresent(v -> luck = v);
        container.getObject(AttributeKeys.PERCEPTION.getQuery(), Double.class).ifPresent(v -> perception = v);
        container.getObject(AttributeKeys.STRENGTH.getQuery(), Double.class).ifPresent(v -> strength = v);
        container.getObject(AttributeKeys.WILLPOWER.getQuery(), Double.class).ifPresent(v -> willpower = v);
        container.getObject(AttributeKeys.WISDOM.getQuery(), Double.class).ifPresent(v -> wisdom = v);
        return Optional.of(this);
    }

    @Override
    public AttributeData copy() {
        return new AttributeData(agility, constitution, charisma, defense, intelligence, luck, perception, strength, willpower, wisdom);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(agility, constitution, charisma, defense, intelligence, luck, perception, strength, willpower, wisdom);
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
                .set(AttributeKeys.CHARISMA.getQuery(), charisma)
                .set(AttributeKeys.DEFENSE.getQuery(), defense)
                .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                .set(AttributeKeys.LUCK.getQuery(), luck)
                .set(AttributeKeys.PERCEPTION.getQuery(), perception)
                .set(AttributeKeys.STRENGTH.getQuery(), strength)
                .set(AttributeKeys.WILLPOWER.getQuery(), willpower)
                .set(AttributeKeys.WISDOM.getQuery(), wisdom);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.795Z")
    public static class Immutable extends AbstractImmutableData<Immutable, AttributeData> {

        private Double agility = 0.0d;

        private Double constitution = 0.0d;

        private Double charisma = 0.0d;

        private Double defense = 0.0d;

        private Double intelligence = 0.0d;

        private Double luck = 0.0d;

        private Double perception = 0.0d;

        private Double strength = 0.0d;

        private Double willpower = 0.0d;

        private Double wisdom = 0.0d;

        Immutable() {
            registerGetters();
        }

        Immutable(Double agility, Double constitution, Double charisma, Double defense, Double intelligence, Double luck, Double perception, Double strength, Double willpower, Double wisdom) {
            this.agility = agility;
            this.constitution = constitution;
            this.charisma = charisma;
            this.defense = defense;
            this.intelligence = intelligence;
            this.luck = luck;
            this.perception = perception;
            this.strength = strength;
            this.willpower = willpower;
            this.wisdom = wisdom;
            registerGetters();
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(AttributeKeys.AGILITY, this::getAgility);
            registerKeyValue(AttributeKeys.AGILITY, this::agility);

            registerFieldGetter(AttributeKeys.CONSTITUTION, this::getConstitution);
            registerKeyValue(AttributeKeys.CONSTITUTION, this::constitution);

            registerFieldGetter(AttributeKeys.CHARISMA, this::getCharisma);
            registerKeyValue(AttributeKeys.CHARISMA, this::charisma);

            registerFieldGetter(AttributeKeys.DEFENSE, this::getDefense);
            registerKeyValue(AttributeKeys.DEFENSE, this::defense);

            registerFieldGetter(AttributeKeys.INTELLIGENCE, this::getIntelligence);
            registerKeyValue(AttributeKeys.INTELLIGENCE, this::intelligence);

            registerFieldGetter(AttributeKeys.LUCK, this::getLuck);
            registerKeyValue(AttributeKeys.LUCK, this::luck);

            registerFieldGetter(AttributeKeys.PERCEPTION, this::getPerception);
            registerKeyValue(AttributeKeys.PERCEPTION, this::perception);

            registerFieldGetter(AttributeKeys.STRENGTH, this::getStrength);
            registerKeyValue(AttributeKeys.STRENGTH, this::strength);

            registerFieldGetter(AttributeKeys.WILLPOWER, this::getWillpower);
            registerKeyValue(AttributeKeys.WILLPOWER, this::willpower);

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

        public Double getCharisma() {
            return charisma;
        }

        public ImmutableValue<Double> charisma() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.CHARISMA, charisma).asImmutable();
        }

        public Double getDefense() {
            return defense;
        }

        public ImmutableValue<Double> defense() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.DEFENSE, defense).asImmutable();
        }

        public Double getIntelligence() {
            return intelligence;
        }

        public ImmutableValue<Double> intelligence() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.INTELLIGENCE, intelligence).asImmutable();
        }

        public Double getLuck() {
            return luck;
        }

        public ImmutableValue<Double> luck() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.LUCK, luck).asImmutable();
        }

        public Double getPerception() {
            return perception;
        }

        public ImmutableValue<Double> perception() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.PERCEPTION, perception).asImmutable();
        }

        public Double getStrength() {
            return strength;
        }

        public ImmutableValue<Double> strength() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.STRENGTH, strength).asImmutable();
        }

        public Double getWillpower() {
            return willpower;
        }

        public ImmutableValue<Double> willpower() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.WILLPOWER, willpower).asImmutable();
        }

        public Double getWisdom() {
            return wisdom;
        }

        public ImmutableValue<Double> wisdom() {
            return Sponge.getRegistry().getValueFactory().createValue(AttributeKeys.WISDOM, wisdom).asImmutable();
        }

        @Override
        public AttributeData asMutable() {
            return new AttributeData(agility, constitution, charisma, defense, intelligence, luck, perception, strength, willpower, wisdom);
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
                    .set(AttributeKeys.CHARISMA.getQuery(), charisma)
                    .set(AttributeKeys.DEFENSE.getQuery(), defense)
                    .set(AttributeKeys.INTELLIGENCE.getQuery(), intelligence)
                    .set(AttributeKeys.LUCK.getQuery(), luck)
                    .set(AttributeKeys.PERCEPTION.getQuery(), perception)
                    .set(AttributeKeys.STRENGTH.getQuery(), strength)
                    .set(AttributeKeys.WILLPOWER.getQuery(), willpower)
                    .set(AttributeKeys.WISDOM.getQuery(), wisdom);
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
