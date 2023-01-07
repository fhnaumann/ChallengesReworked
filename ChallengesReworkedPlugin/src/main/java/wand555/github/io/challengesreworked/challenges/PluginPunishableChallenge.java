package wand555.github.io.challengesreworked.challenges;

import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.Wrapper;
import wand555.github.io.challengesreworked.punishments.PluginPunishment;

import java.util.Collection;

public abstract class PluginPunishableChallenge extends PluginChallenge implements PunishableChallenge {

    private Collection<PluginPunishment> punishments;

    public PluginPunishableChallenge(PunishableChallengeCommon common) {
        super(common);
        this.punishments = common.getPunishmentCommons().stream().map(punishmentCommon -> (PluginPunishment) Wrapper.wrap(punishmentCommon)).toList();
        //getPunishments()[0].getCommon() is the same as getCommon().getPunishmentCommons()[0]
    }

    @Override
    public Collection<PluginPunishment> getPunishments() {
        return punishments;
    }

    @Override
    public void setPunishments(Collection<? extends Punishment> punishments) {
        this.punishments = (Collection<PluginPunishment>) punishments; //safe cast (I hope)
    }

    @Override
    public PunishableChallengeCommon getCommon() {
        return (PunishableChallengeCommon) super.getCommon();
    }
}
