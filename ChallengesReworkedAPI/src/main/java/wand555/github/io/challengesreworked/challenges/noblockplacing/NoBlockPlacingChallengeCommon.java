package wand555.github.io.challengesreworked.challenges.noblockplacing;

import org.bukkit.Material;
import wand555.github.io.challengesreworked.Common;
import wand555.github.io.challengesreworked.challenges.PunishableChallengeCommon;
import wand555.github.io.challengesreworked.challenges.nocrafting.NoCraftingChallengeCommon;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NoBlockPlacingChallengeCommon extends PunishableChallengeCommon {

    private Set<Material> allowedToPlace;

    public NoBlockPlacingChallengeCommon() {
        this(
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public NoBlockPlacingChallengeCommon(Collection<PunishmentCommon> punishmentCommons, Set<Material> allowedToPlace) {
        super(punishmentCommons);
        this.allowedToPlace = allowedToPlace;
    }

    public Set<Material> getAllowedToPlace() {
        return allowedToPlace;
    }

    public void setAllowedToPlace(Set<Material> allowedToPlace) {
        this.allowedToPlace = allowedToPlace;
    }

    @Override
    public NoBlockPlacingChallengeCommon copy() {
        return new NoBlockPlacingChallengeCommon(getPunishmentCommons(), getAllowedToPlace());
    }
}
