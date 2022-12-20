package wand555.github.io.challengesreworked;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class NoCraftingChallengeTest {
    private ServerMock server;
    private ChallengesReworked plugin;
    private PlayerMock player;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(ChallengesReworked.class);
        player = server.addPlayer();
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
