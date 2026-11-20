package me.daivdmajholt.sessentials.other;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnablesHandler {

    private static boolean lifecycleRegistrationAvailable;

    public static boolean isLifecycleRegistrationAvailable() {
        return lifecycleRegistrationAvailable;
    }

    public static void applyNewCommandRegistrationAPI() {
        lifecycleRegistrationAvailable = false;

        try {
            JavaPlugin plugin = JavaPlugin.getProvidingPlugin(EnablesHandler.class);

            Class<?> lifecycleEvents = Class.forName("io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents");
            Class<?> literalBuilder = Class.forName("com.mojang.brigadier.builder.LiteralArgumentBuilder");
            Class<?> commandInterface = Class.forName("com.mojang.brigadier.Command");
            Method getLifecycleManager = plugin.getClass().getMethod("getLifecycleManager");
            Object lifecycleManager = getLifecycleManager.invoke(plugin);
            Object commandsKey = lifecycleEvents.getField("COMMANDS").get(null);

            Method register = null;
            for (Method method : lifecycleManager.getClass().getMethods()) {
                if (method.getName().equals("registerEventHandler") && method.getParameterCount() == 2) {
                    register = method;
                    break;
                }
            }
            if (register == null) {
                return;
            }

            Consumer<Object> handler = event -> {
                try {
                    Object registrar = event.getClass().getMethod("registrar").invoke(event);
                    Object dispatcher = registrar.getClass().getMethod("getDispatcher").invoke(registrar);
                    Method literal = literalBuilder.getMethod("literal", String.class);

                    Object slashRoot = literal.invoke(null, "/");
                    for (String name : me.daivdmajholt.sessentials.events.CommandHandler.doubleSlashCommands) {
                        Object commandNode = literal.invoke(null, name);
                        Object brigadierCommand = Proxy.newProxyInstance(
                                commandInterface.getClassLoader(),
                                new Class<?>[]{commandInterface},
                                (proxy, method, args) -> {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), name);
                                    return 1;
                                });

                        commandNode = commandNode.getClass().getMethod("executes", commandInterface).invoke(commandNode, brigadierCommand);
                        slashRoot = slashRoot.getClass().getMethod("then", literalBuilder).invoke(slashRoot, commandNode);
                    }

                    dispatcher.getClass().getMethod("register", literalBuilder).invoke(dispatcher, slashRoot);
                } catch (Exception exception) {
                    Bukkit.getLogger().warning("[SEssentials] Lifecycle Brigadier registration failed: " + exception.getMessage());
                }
            };

            register.invoke(lifecycleManager, commandsKey, handler);
            lifecycleRegistrationAvailable = true;
            plugin.getLogger().info("Using modern lifecycle Brigadier command registration.");
        } catch (ReflectiveOperationException | SecurityException exception) {
            lifecycleRegistrationAvailable = false;
        }
    }
}
