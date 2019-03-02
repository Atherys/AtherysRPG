package com.atherys.rpg.data;

import com.google.common.reflect.TypeToken;
import javax.annotation.Generated;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2019-03-02T15:51:24.813Z")
public class AttributeKeys {

    private AttributeKeys() {}

    private final static TypeToken<Value<Double>> DOUBLE_TOKEN = new TypeToken<Value<Double>>(){};

    public final static Key<Value<Double>> AGILITY = Key.builder()
            .id("agility")
            .name("Agility")
            .query(DataQuery.of("Agility"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> CONSTITUTION = Key.builder()
            .id("constitution")
            .name("Constitution")
            .query(DataQuery.of("Constitution"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> CHARISMA = Key.builder()
            .id("charisma")
            .name("Charisma")
            .query(DataQuery.of("Charisma"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> DEFENSE = Key.builder()
            .id("defense")
            .name("Defense")
            .query(DataQuery.of("Defense"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> INTELLIGENCE = Key.builder()
            .id("intelligence")
            .name("Intelligence")
            .query(DataQuery.of("Intelligence"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> LUCK = Key.builder()
            .id("luck")
            .name("Luck")
            .query(DataQuery.of("Luck"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> PERCEPTION = Key.builder()
            .id("perception")
            .name("Perception")
            .query(DataQuery.of("Perception"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> STRENGTH = Key.builder()
            .id("strength")
            .name("Strength")
            .query(DataQuery.of("Strength"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> WILLPOWER = Key.builder()
            .id("willpower")
            .name("Willpower")
            .query(DataQuery.of("Willpower"))
            .type(DOUBLE_TOKEN)
            .build();

    public final static Key<Value<Double>> WISDOM = Key.builder()
            .id("wisdom")
            .name("Wisdom")
            .query(DataQuery.of("Wisdom"))
            .type(DOUBLE_TOKEN)
            .build();
}
