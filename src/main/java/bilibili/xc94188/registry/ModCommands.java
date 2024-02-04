package bilibili.xc94188.registry;

import bilibili.xc94188.PaoMo;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

import java.util.Collection;
import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, isReload) -> {
            registerGmCommand(dispatcher);
            // 在这里添加更多的命令注册函数
        });
        PaoMo.LOGGER.info("Registering Mod Commands for " + PaoMo.MOD_ID);
    }

    private static void registerGmCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("gm")
                .then(argument("mode", StringArgumentType.string())
                        .suggests((context, builder) -> CommandSource.suggestMatching(new String[]{
                                "0", "1", "2", "3", "s", "c", "a", "sp"
                        }, builder))
                        .executes(context -> {
                            String mode = StringArgumentType.getString(context, "mode");
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            if (!mode.equals("0") && !mode.equals("1") && !mode.equals("2") && !mode.equals("3") &&
                                    !mode.equals("s") && !mode.equals("c") && !mode.equals("a") && !mode.equals("sp")) {
                                if (player != null) {
                                    player.sendMessage(Text.translatable("command.paomo.gmerrorer.tip").formatted(Formatting.RED), false);
                                }
                            } else {
                                changeGameMode(player, mode);
                            }
                            return 1;
                        })
                        .then(argument("target", EntityArgumentType.players())
                                .executes(context -> {
                                    String mode = StringArgumentType.getString(context, "mode");
                                    ServerPlayerEntity player = context.getSource().getPlayer();
                                    Collection<ServerPlayerEntity> targetPlayers = EntityArgumentType.getPlayers(context, "target");
                                    if (!mode.equals("0") && !mode.equals("1") && !mode.equals("2") && !mode.equals("3") &&
                                            !mode.equals("s") && !mode.equals("c") && !mode.equals("a") && !mode.equals("sp")) {
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
}
