package de.constellate.nitroapp.utils;
import com.google.gson.JsonObject;
import de.constellate.nitroapp.Main;

import java.util.UUID;

public class TransactionLogger {

    public static void logTransaction(String sender_name, UUID sender_uuid, String receiver_name, UUID receiver_uuid, int amount) {

        // Make API call to api.remastered.nitroapp.de/transactions
        // POST
        // Content-Type: application/json

        Config config = Main.getInstance().getConfiguration();

        if (config.getConfig().contains("server.transaction_logger")) {
            if (config.getConfig().getBoolean("server.transaction_logger")) {
                JsonObject json = new JsonObject();
                JsonObject sender = new JsonObject();
                JsonObject receiver = new JsonObject();

                sender.addProperty("name", sender_name);
                sender.addProperty("uuid", sender_uuid.toString());

                receiver.addProperty("name", receiver_name);
                receiver.addProperty("uuid", receiver_uuid.toString());

                json.add("sender", sender);
                json.add("receiver", receiver);
                json.addProperty("amount", amount);

                API.call("/transactions", json);
            } else {
                System.out.println("Transaction logging disabled");
            }
        } else {
            System.out.println("Transaction logging disabled");
        }

    }

}
