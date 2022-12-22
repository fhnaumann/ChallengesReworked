package io.github.wand555.challengesreworkedapi.punishments;

import io.github.wand555.challengesreworkedapi.Common;
import io.github.wand555.challengesreworkedapi.Commonable;
import io.github.wand555.challengesreworkedapi.Storable;
import org.bukkit.entity.Player;

public interface Punishment extends Commonable, Storable {

    @Override
    PunishmentCommon getCommon();
}
