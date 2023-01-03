package wand555.github.io.challengesreworked.punishments.health;

import wand555.github.io.challengesreworked.challenges.Challenge;
import wand555.github.io.challengesreworked.challenges.nodamage.NoDamageChallenge;
import wand555.github.io.challengesreworked.punishments.Punishment;

import java.util.Collection;
import java.util.List;

public interface HealthPunishment extends Punishment {
    @Override
    HealthPunishmentCommon getCommon();

    @Override
    default Collection<Class<? extends Challenge>> getIncompatibleChallenges() {
        return List.of(NoDamageChallenge.class);
    }

    default int getHealthAmount() {
        return getCommon().getHealthAmount();
    }

    /*
    @Override
    public void enforcePunishment(Player causer) {
        super.enforcePunishment(causer);
        switch(affectType) {
            case CAUSER -> {
                damagePlayer(causer);
                ChatLogger.log("punishments.health.affecttype.causer",
                        Map.of(PlaceHolderHandler.PLAYER_PLACEHOLDER, causer,
                                PlaceHolderHandler.AMOUNT_PLACEHOLDER, healthAmount
                        ));
            }
            case ALL -> {
                ChallengeManager.getInstance().getPlayers().stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(this::damagePlayer);
                ChatLogger.log("punishments.health.affecttype.all",
                        Map.of(PlaceHolderHandler.AMOUNT_PLACEHOLDER, healthAmount));
            }
        }
    }

    private void damagePlayer(Player player) {
        player.damage(healthAmount);
    }

 */
}
