package wand555.github.io.challengesreworked.punishments;

import wand555.github.io.challengesreworked.Commonable;
import wand555.github.io.challengesreworked.Storable;

public interface Punishment extends Commonable, Storable {

    @Override
    PunishmentCommon getCommon();
}
