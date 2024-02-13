package roei.plugins.first.events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import roei.plugins.first.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class GeneralEvents implements Listener {
    @EventHandler
    public void inventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        Menu menu = Menu.getMenu(p);
        if(menu != null){
            event.setCancelled(true);
            //click in own inv
            if(event.getClickedInventory() != null){
                if (event.getRawSlot()> event.getClickedInventory().getSize()){
                    if(Menu.getGenInvClickAction()!= null) Menu.getGenInvClickAction().Click(p,event);
                }else if(Menu.getGeneralClickAction() != null){//click in open menu
                    Menu.getGenInvClickAction().Click(p,event);
                }
            }
            Menu.MenuClick menuClick = menu.getAction(event.getRawSlot());
            if(menuClick != null) menuClick.Click(p,event);
        }

    }
}
