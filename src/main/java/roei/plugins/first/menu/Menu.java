package roei.plugins.first.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.awt.*;
import java.util.*;

import static org.bukkit.Bukkit.*;

public class Menu {
    private static final Map<UUID,Menu> openMenus = new HashMap<>();
    private static final Map<String, Set<UUID>> viewers = new HashMap<>();
    private static final Map<Integer,MenuClick> menuClickActions = new HashMap<>();
    private MenuClick generalClickAction;
    private MenuClick genInvClickAction;

    private MenuDrag dragAction;
    private MenuOpen openAction;
    private MenuClose closeAction;
    
    public final UUID uuid;
    private final Inventory inventory;
    private final String viewerId;

    public Menu(int size, String name) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(this::getInventory,size,name);
        viewerId = null;
    }
    public Menu(int size, String name,String viewerId) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(this::getInventory, size,name);
        this.viewerId = viewerId;
    }

    protected void setOpenAction(MenuOpen openAction) {this.openAction = openAction;}

    protected void setCloseAction(MenuClose closeAction) {this.closeAction = closeAction;}

    public MenuClick getGeneralClickAction() {return generalClickAction;}

    protected void setGeneralClickAction(MenuClick generalClickAction) {this.generalClickAction = generalClickAction;}

    public MenuClick getGenInvClickAction() {return genInvClickAction;}

    protected void setGenInvClickAction(MenuClick genInvClickAction) {this.genInvClickAction = genInvClickAction;}

    public MenuDrag getDragAction() {return dragAction;}

    protected void setDragAction(MenuDrag dragAction) {this.dragAction = dragAction;}

    public interface MenuClick{ void Click(Player p, InventoryClickEvent event);}
    public interface MenuDrag{ void drag (Player p, InventoryDragEvent event);}
    public interface MenuOpen { void Open(Player p);}
    public interface MenuClose{void Close(Player p);}
    public MenuClick getAction(int index)
    {
        return menuClickActions.getOrDefault(index,null);
    }
    public void open(Player p){
        p.openInventory(inventory);
        openMenus.put(p.getUniqueId(),this);
        if(viewerId != null){}
        if(openAction != null){
            openAction.Open(p);
        }
    }
    //adds a viewer to the hash map if there is no user in the hash map yet
    public void addViewer(Player p){
        if(viewerId == null) return;
        Set<UUID>list = viewers.getOrDefault(viewerId, new HashSet<>());
        list.add(p.getUniqueId());
        viewers.put(viewerId,list);
    }
    public void SetItem(int Index, ItemStack item){
        inventory.setItem(Index,item);
    }
    public void SetItem(int index,ItemStack item,MenuClick action){
        inventory.setItem(index,item);
        if (action == null ){
            menuClickActions.remove(index);
        }else menuClickActions.put(index,action);
    }
    public Inventory getInventory() {
        return inventory;
    }
}
