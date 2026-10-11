package net.sweningteam.not_enough_dragons.client.registry;

import com.geckolib.constant.dataticket.DataTicket;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModDataTickets {
    public static final DataTicket<Boolean> IS_BABY = DataTicket.create("is_baby", Boolean.class);
    public static final DataTicket<Boolean> HAS_SADDLE = DataTicket.create("has_saddl", Boolean.class);

    public static void init(){
        NotEnoughDragons.LOGGER.info(" [Client] Registering DataTickets for {}!", NotEnoughDragons.MOD_NAME);
    }
}
