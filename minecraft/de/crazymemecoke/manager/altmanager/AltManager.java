package de.crazymemecoke.manager.altmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import de.crazymemecoke.Client;
import de.crazymemecoke.features.ui.guiscreens.altmanager.AltSlot;
import de.crazymemecoke.utils.FileUtils;

public class AltManager {
    public static ArrayList altList = new ArrayList();
    public static ArrayList slotList = new ArrayList();
    public static File altFile;

    static {
        try {
            altFile = new File(Client.getInstance().getClientDir() + "/alts.txt");
            if (altFile.createNewFile()) {
                System.out.println("File created: " + altFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void saveAlts() {
        Iterator var2 = slotList.iterator();
        List<String> formattedAlts = new ArrayList<String>();
        while (var2.hasNext()) {
            AltSlot slot = (AltSlot) var2.next();
            formattedAlts.add(slot.getUsername() + ":" + slot.getPassword());
        }
        FileUtils.saveFile(altFile, formattedAlts);
    }

    public static void loadAlts() {
        slotList.clear();

        FileUtils.loadFile(altFile).forEach(line -> {
            final String[] args = line.split(":");
            if (args.length == 2) {
                String username = args[0];
                String password = args[1];
                slotList.add(new AltSlot(username, password));
            }
        });
    }

    public File getAltFile() {
        return altFile;
    }
}
