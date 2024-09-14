package me.kairomc.architecture.utils;

import java.util.UUID;

public class PlayerUtils {
    private final UUID uuid;
    private final String name;
    private int money;

    public PlayerUtils(UUID uuid, String name, int money) {
        this.uuid = uuid;
        this.name = name;
        this.money = money;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
