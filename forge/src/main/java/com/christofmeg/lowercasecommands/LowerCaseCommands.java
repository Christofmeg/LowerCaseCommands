package com.christofmeg.lowercasecommands;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@Mod(CommonConstants.MOD_ID)
public class LowerCaseCommands {

    public LowerCaseCommands() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        String input = event.getParseResults().getReader().getString();

        if (input.startsWith("/")) {
            input = input.substring(1);
        }

        String[] parts = input.split(" ", 2);
        String cmd = parts[0];
        String rest = parts.length > 1 ? " " + parts[1] : "";

        if (Character.isUpperCase(cmd.charAt(0))) {
            var lowerNode = event.getParseResults().getContext()
                    .getSource().getServer()
                    .getCommands()
                    .getDispatcher()
                    .getRoot()
                    .getChild(cmd.toLowerCase(Locale.ROOT));

            if (lowerNode != null) {
                // Cancel original parse and re-run lowercase
                event.setCanceled(true);
                event.getParseResults().getContext()
                        .getSource()
                        .getServer()
                        .getCommands()
                        .performPrefixedCommand(
                                event.getParseResults().getContext().getSource(),
                                cmd.toLowerCase(Locale.ROOT) + rest
                        );
            }
        }
    }

}