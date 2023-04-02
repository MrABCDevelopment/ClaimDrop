package me.dreamdevs.github.claimdrop.managers;

import lombok.Getter;
import me.dreamdevs.github.claimdrop.DropOption;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ClaimDropManager {

    private Map<UUID, DropOption> playersOptions;

    public ClaimDropManager() {
        playersOptions = new HashMap<>();
    }

    public DropOption getOption(UUID uuid) {
        return playersOptions.get(uuid);
    }

}