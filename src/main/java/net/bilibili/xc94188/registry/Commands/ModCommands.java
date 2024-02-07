package net.bilibili.xc94188.registry.Commands;

import net.bilibili.xc94188.PaoMo;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

import java.util.*;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static final Set<UUID> vanishedPlayers = new HashSet<>();

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, isReload) -> {
            registerGmCommand(dispatcher);
            registerSudoCommand(dispatcher);
            registerVCommand(dispatcher);
            // 在这里添加更多的命令注册函数
        });
        PaoMo.LOGGER.info("Registering Mod Commands for " + PaoMo.MOD_ID);
    }

    private static void registerGmCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("gm").requires(source -> source.hasPermissionLevel(4))  // 只有权限等级为4的玩家（即op）才能执行此命令
                .then(argument("mode", StringArgumentType.string()).suggests((context, builder) -> CommandSource.suggestMatching(new String[]{"0", "1", "2", "3", "s", "c", "a", "sp"}, builder)).executes(context -> {
                    String mode = StringArgumentType.getString(context, "mode");
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (!mode.equals("0") && !mode.equals("1") && !mode.equals("2") && !mode.equals("3") && !mode.equals("s") && !mode.equals("c") && !mode.equals("a") && !mode.equals("sp")) {
                        if (player != null) {
                            player.sendMessage(Text.translatable("command.paomo.gmerrorer.tip").formatted(Formatting.RED), false);
                        }
                    } else {
                        changeGameMode(player, mode);
                    }
                    return 1;
                }).then(argument("target", EntityArgumentType.players()).executes(context -> {
                    String mode = StringArgumentType.getString(context, "mode");
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    Collection<ServerPlayerEntity> targetPlayers = EntityArgumentType.getPlayers(context, "target");
                    if (!mode.equals("0") && !mode.equals("1") && !mode.equals("2") && !mode.equals("3") && !mode.equals("s") && !mode.equals("c") && !mode.equals("a") && !mode.equals("sp")) {
                        if (player != null) {
                            player.sendMessage(Text.translatable("command.paomo.gmerrorer.tip").formatted(Formatting.RED), false);
                        }
                    } else {
                        for (ServerPlayerEntity targetPlayer : targetPlayers) {
                            changeGameMode(targetPlayer, mode);
                        }
                    }

                    return 1;
                })));
        dispatcher.register(command);
    }

    private static void changeGameMode(ServerPlayerEntity player, String mode) {
        if (Objects.equals(mode, "0") || Objects.equals(mode, "s")) {
            player.changeGameMode(GameMode.SURVIVAL);
            player.sendMessage(Text.translatable("command.paomo.gm.tip").append("survival"), false);
        } else if (Objects.equals(mode, "1") || Objects.equals(mode, "c")) {
            player.changeGameMode(GameMode.CREATIVE);
            player.sendMessage(Text.translatable("command.paomo.gm.tip").append("creative"), false);
        } else if (Objects.equals(mode, "2") || Objects.equals(mode, "a")) {
            player.changeGameMode(GameMode.ADVENTURE);
            player.sendMessage(Text.translatable("command.paomo.gm.tip").append("adventure"), false);
        } else if (Objects.equals(mode, "3") || Objects.equals(mode, "sp")) {
            player.changeGameMode(GameMode.SPECTATOR);
            player.sendMessage(Text.translatable("command.paomo.gm.tip").append("spectator"), false);
        }
    }

    private static void registerSudoCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sudo").requires(source -> source.hasPermissionLevel(4))  // 只有权限等级为4的玩家（即op）才能执行此命令
                .then(CommandManager.argument("target", EntityArgumentType.player()).then(CommandManager.argument("command", StringArgumentType.greedyString()).executes(context -> {
                    ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "target");
                    String command = StringArgumentType.getString(context, "command");
                    if (command.startsWith("c:")) {
                        // 如果命令以 "c:" 开头，那么将 "c:" 之后的所有字符作为参数执行 "say" 命令
                        String sayMessage = command.substring(2);
                        String sayCommand = "execute as " + player.getName().getString() + " run say " + sayMessage;
                        ParseResults<ServerCommandSource> parse = dispatcher.parse(sayCommand, Objects.requireNonNull(player.getServer()).getCommandSource());
                        return dispatcher.execute(parse);
                    } else {
                        // 否则，像之前一样执行命令
                        ParseResults<ServerCommandSource> parse = dispatcher.parse(command, player.getCommandSource());
                        return dispatcher.execute(parse);
                    }
                }))));
    }

    public static void registerVCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("v").requires(source -> source.hasPermissionLevel(4))  // 确保只有 OP 可以执行这个命令
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    if (player != null) {
                        UUID playerUUID = player.getUuid();
                        if (vanishedPlayers.contains(playerUUID)) {
                            // 如果玩家已经是隐身的，那么他们将变为可见
                            vanishedPlayers.remove(playerUUID);
                            player.setInvisible(false);
                            player.noClip = false;  // 取消无碰撞状态
                            Text message = Text.literal(player.getEntityName() + " joined the game").setStyle(Style.EMPTY.withColor(Formatting.YELLOW));
                            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(p -> p.sendMessage(message));
                            context.getSource().getServer().getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, player));  // 在玩家列表中显示玩家
                        } else {
                            // 如果玩家不是隐身的，那么他们将变为隐身
                            vanishedPlayers.add(playerUUID);
                            player.setInvisible(true);
                            player.noClip = true;  // 设置无碰撞状态
                            Text message = Text.literal(player.getEntityName() + " left the game").setStyle(Style.EMPTY.withColor(Formatting.YELLOW));
                            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(p -> p.sendMessage(message));
                            context.getSource().getServer().getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, player));  // 在玩家列表中隐藏玩家
                        }
                    }
                    return 1;
                }));
    }
}




