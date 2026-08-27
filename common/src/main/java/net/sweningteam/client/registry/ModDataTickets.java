package net.sweningteam.client.registry;

import net.sweningteam.NotEnoughDragons;
import software.bernie.geckolib.constant.dataticket.DataTicket;

public class ModDataTickets {
    public static final DataTicket<Boolean> IS_BABY = DataTicket.create("is_baby", Boolean.class);

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering DataTickets for "+ NotEnoughDragons.MOD_ID);
    }
}
