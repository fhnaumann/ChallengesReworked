package wand555.github.io.challengesreworked;

import io.github.wand555.challengesreworkedapi.challenges.PunishableChallengeCommon;
import io.github.wand555.challengesreworkedapi.challenges.PunishableChallenge;
import io.github.wand555.challengesreworkedapi.punishments.Punishment;
import wand555.github.io.challengesreworked.punishment.PluginPunishment;

import java.util.Collection;

public abstract class PluginPunishableChallenge extends PluginChallenge implements PunishableChallenge {

    public PluginPunishableChallenge(PunishableChallengeCommon common) {
        super(common);

    }

    /**
     * Casts the punishments to plugin punishments.
     * @return the cast punishments
     */
    @Override
    public Collection<PluginPunishment> getPunishments() {
        return (Collection<PluginPunishment>) getCommon().getPunishments();
    }

    @Override
    public void setPunishments(Collection<? extends Punishment> punishments) {
        getCommon().setPunishments(punishments);
    }

    @Override
    public PunishableChallengeCommon getCommon() {
        return (PunishableChallengeCommon) super.getCommon();
    }
}
