package de.constellate.nitroapp.maintenance;

import de.constellate.nitroapp.Main;
import de.constellate.nitroapp.utils.Config;

public class MaintenanceManager {

    Config config = Main.getInstance().getConfiguration();

    public void setMaintenance(Boolean mode) {
        config.getConfig().set("server.maintenance", mode);
    }

    public boolean getMaintenance() {
        if (config.getConfig().contains("server.maintenance")) {
            return config.getConfig().getBoolean("server.maintenance");
        } else {
            return false;
        }
    }

}
