package wand555.github.io.challengesreworked.challenges.randomdrops;

import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import wand555.github.io.challengesreworked.challenges.ChallengeCommon;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomDropsChallengeCommon extends ChallengeCommon {

    private Map<Material, Material> randomized;

    private boolean randomBlockDrops;
    private boolean randomMobDrops;
    private boolean randomCraftingDrops;
    private boolean randomFurnaceDrops;

    public RandomDropsChallengeCommon() {
        this(randomize(), true, false, false, false);
    }

    private RandomDropsChallengeCommon(Map<Material, Material> randomized, boolean randomBlockDrops, boolean randomMobDrops, boolean randomCraftingDrops, boolean randomFurnaceDrops) {
        this.randomized = randomized;
        this.randomBlockDrops = randomBlockDrops;
        this.randomMobDrops = randomMobDrops;
        this.randomCraftingDrops = randomCraftingDrops;
        this.randomFurnaceDrops = randomFurnaceDrops;
    }

    public void rerollRandomizations() {
        randomized = randomize();
    }


    private static Map<Material, Material> randomize() {
        List<Material> obtainableItems = Stream.of(Material.values())
                .filter(Material::isItem)
                .filter(Predicate.not(Material::isAir))
                .filter(Predicate.not(Material::isLegacy))
                .toList();
        List<Material> copyObtainableItems = new ArrayList<>(obtainableItems);

        return obtainableItems.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        material -> getRandomFrom(copyObtainableItems))
                );
    }

    private static Material getRandomFrom(List<Material> materials) {
        return materials.remove(ThreadLocalRandom.current().nextInt(materials.size()));
    }

    public Material getRandomized(Material material) {
        Material randomMapped = randomized.get(material);
        if(randomMapped == null) {
            throw new RuntimeException("Material %s is not mapped to anything".formatted(material));
        }
        return randomMapped;
    }

    public Map<Material, Material> getRandomizedMaterials() {
        return Collections.unmodifiableMap(randomized);
    }

    @Override
    public RandomDropsChallengeCommon copy() {
        return new RandomDropsChallengeCommon(
                getRandomizedMaterials(),
                isRandomBlockDrops(),
                isRandomMobDrops(),
                isRandomCraftingDrops(),
                isRandomFurnaceDrops());
    }

    public boolean isRandomBlockDrops() {
        return randomBlockDrops;
    }

    public void setRandomBlockDrops(boolean randomBlockDrops) {
        this.randomBlockDrops = randomBlockDrops;
    }

    public boolean isRandomMobDrops() {
        return randomMobDrops;
    }

    public void setRandomMobDrops(boolean randomMobDrops) {
        this.randomMobDrops = randomMobDrops;
    }

    public boolean isRandomCraftingDrops() {
        return randomCraftingDrops;
    }

    public void setRandomCraftingDrops(boolean randomCraftingDrops) {
        this.randomCraftingDrops = randomCraftingDrops;
    }

    public boolean isRandomFurnaceDrops() {
        return randomFurnaceDrops;
    }

    public void setRandomFurnaceDrops(boolean randomFurnaceDrops) {
        this.randomFurnaceDrops = randomFurnaceDrops;
    }

    public static final TypeAdapter<RandomDropsChallengeCommon> adapter = new TypeAdapter<RandomDropsChallengeCommon>() {
        @NotNull
        @Override
        public Map<Object, Object> serialize(@NotNull RandomDropsChallengeCommon randomDropsChallengeCommon) {
            return Map.of(
                    "randomized", randomDropsChallengeCommon.getRandomizedMaterials(),
                    "randomBlockDrops", randomDropsChallengeCommon.isRandomBlockDrops(),
                    "randomMobDrops", randomDropsChallengeCommon.isRandomMobDrops(),
                    "randomCraftingDrops", randomDropsChallengeCommon.isRandomCraftingDrops(),
                    "randomFurnaceDrops", randomDropsChallengeCommon.isRandomFurnaceDrops()
            );
        }

        @NotNull
        @Override
        public RandomDropsChallengeCommon deserialize(@NotNull Map<Object, Object> map) {
            return new RandomDropsChallengeCommon(
                    (Map<Material, Material>) map.get("randomized"),
                    (boolean) map.get("randomBlockDrops"),
                    (boolean) map.get("randomMobDrops"),
                    (boolean) map.get("randomCraftingDrops"),
                    (boolean) map.get("randomFurnaceDrops")
            );
        }
    };
}
