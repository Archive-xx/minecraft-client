package de.crazymemecoke.features.commands;

import de.crazymemecoke.Client;
import de.crazymemecoke.features.commands.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    public List<Command> commands = new ArrayList<Command>();

    public CommandManager() {
        commands.add(new Enchant());
        commands.add(new Toggle());
        commands.add(new Rename());
        commands.add(new Friend());
        commands.add(new Vanish());
        commands.add(new Reload());
        commands.add(new Panic());
        commands.add(new Bind());
        commands.add(new Help());
        commands.add(new Info());
        commands.add(new Kick());
        commands.add(new Fix());
        commands.add(new Say());
        commands.add(new NCP());
    }

    public boolean execute(String text) {
        if (!text.startsWith(Client.main().getClientPrefix())) {
            return false;
        }

        text = text.substring(1);
        String[] arguments = text.split(" ");
        for (Command cmd : commands) {
            if (cmd.getName().equalsIgnoreCase(arguments[0])) {
                String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
                cmd.execute(args);
                return true;
            }
        }
        return false;
    }

    public List<Command> getCommands() {
        return commands;
    }
}