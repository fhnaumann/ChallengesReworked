package wand555.github.io.challengesreworked.punishments.randomeffect;

import wand555.github.io.challengesreworked.punishments.Punishment;
import wand555.github.io.challengesreworked.punishments.PunishmentCommon;

public interface RandomEffectPunishment extends Punishment {

    @Override
    RandomEffectPunishmentCommon getCommon();
}
