package wand555.github.io.challengesreworked.punishment;

import java.util.Collection;

public interface Punishable {

    public Collection<Punishment> getPunishments();
    public void setPunishments(Collection<Punishment> punishments);
}
