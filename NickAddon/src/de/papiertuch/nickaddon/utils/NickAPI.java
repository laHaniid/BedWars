package de.papiertuch.nickaddon.utils;

import de.papiertuch.nickaddon.NickAddon;
import net.haoshoku.nick.NickPlugin;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Leon on 17.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class NickAPI {

    private Player player;
    private UUID uuid;

    public NickAPI(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public void setAutoNick(boolean value) {
        if (value) {
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.autoNickEnable"));
            NickAddon.getInstance().getMySQL().setNick(uuid, true);
            setRandomNickName();
        } else {
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.autoNickDisable"));
            NickAddon.getInstance().getMySQL().setNick(uuid, false);
        }
    }

    public void setNick(Boolean value) {
        if (value) {
            String name;
            if (NickAddon.getInstance().getNickConfig().getBoolean("autoNick.randomName")) {
                name = getRandomNickName();
            } else {
                name = getNickName();
            }
            NickPlugin.getPlugin().getAPI().nick(player, name, true, null, null);
            NickPlugin.getPlugin().getAPI().setGameProfile(player, name);
            NickPlugin.getPlugin().getAPI().refreshPlayer(player);
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.nick")
                    .replace("%nick%", name));
            if (!NickAddon.getInstance().getNickPlayers().contains(uuid)) {
                NickAddon.getInstance().getNickPlayers().add(uuid);
            }
        } else {
            NickPlugin.getPlugin().getAPI().unnick(player);
            NickPlugin.getPlugin().getAPI().resetGameProfile(player);
            player.setPlayerListName(player.getName());
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.unNick"));
            NickAddon.getInstance().getNickPlayers().remove(uuid);
        }
    }

    public boolean getAutoNickState() {
        return NickAddon.getInstance().getMySQL().getState(uuid);
    }

    public String getRandomNickName() {
        Random r = new Random();
        String name = NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").get(r.nextInt(NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").size()));
        return name;
    }

    private void setRandomNickName() {
        Random r = new Random();
        String name = NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").get(r.nextInt(NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").size()));
        NickAddon.getInstance().getMySQL().setName(uuid, name);
    }

    public void disableNick() {
        NickAddon.getInstance().getMySQL().setNick(uuid, false);
    }

    private String getNickName() {
        return NickAddon.getInstance().getMySQL().getName(uuid);
    }

    public void setRandomNick(Boolean value) {
        if (value) {
            Random r = new Random();
            String nick = NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").get(r.nextInt(NickAddon.getInstance().getNickConfig().getConfiguration().getStringList("nicks").size()));
            NickPlugin.getPlugin().getAPI().nick(player, nick, true, null, null);
            NickPlugin.getPlugin().getAPI().setGameProfile(player, nick);
            NickPlugin.getPlugin().getAPI().refreshPlayer(player);
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.nick")
                    .replace("%nick%", nick));
            if (!NickAddon.getInstance().getNickPlayers().contains(uuid)) {
                NickAddon.getInstance().getNickPlayers().add(uuid);
            }
        } else {
            NickPlugin.getPlugin().getAPI().unnick(player);
            NickPlugin.getPlugin().getAPI().resetGameProfile(player);
            player.setPlayerListName(NickAddon.getInstance().getMySQL().getRealName(uuid));
            player.sendMessage(NickAddon.getInstance().getNickConfig().getString("message.unNick"));
            NickAddon.getInstance().getNickPlayers().remove(uuid);
        }
    }

}
