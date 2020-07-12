/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event;


import entity.player.Player;
import gameevent.GameEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.mamoe.mirai.contact.Group;
import static thorm.Weather.GetMessage.getTodayTemperatureInfo;

/**
 *
 * @author Administrator
 */

public class Weather extends GameEvent {

    public Weather() {
        super("天气");
    }

    @Override
    public void Do(Player player, String message) {
        String data[] = message.split(" ");
        String cityname = data[1];
        String info;
        try {
            info = getTodayTemperatureInfo(cityname);
            info = "======================\n     " + info + "     \n======================";
            player.SendMessageToPlayer(info);
        } catch (Exception ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void Do(Group group, String message) {
        String data[] = message.split(" ");
        String cityname = data[1];
        String info;
        try {
            info = getTodayTemperatureInfo(cityname);
            info = "======================\n     " + info + "     \n======================";
            group.sendMessage(info);
        } catch (Exception ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
