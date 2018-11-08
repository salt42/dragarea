package salt.bukkit.dragarea.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import salt.bukkit.dragarea.ConfigManager;
import salt.bukkit.dragarea.DragArea;
import salt.bukkit.dragarea.IConfigReloadEvent;
import salt.bukkit.dragarea.models.JumpPad;

import java.util.List;
import java.util.ListIterator;

public class AreaEvent implements Listener, IConfigReloadEvent{

    private DragArea main;
    private List<JumpPad> jumpPads;

    public AreaEvent(DragArea plugin) {
        this.main = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.main.configManager.registerReloadEvent(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Location l = e.getPlayer().getLocation();

        int x = l.getBlockX();
        int y = l.getBlockY()-1;
        int z = l.getBlockZ();
//        @todo Vector.isInAABB()
        if (x > 0 && x < 10 &&
            y > 0 && y < 10 &&
            z > 0 && z < 10) {

            e.getPlayer().setVelocity(new Vector(5, 0, 0));
            return;
        }


        Vector playerPos = l.toVector();
        //for each swirl
        /*
        swirl:
            centerX: 30
            centerZ: 30
            top: 35
            bottom: 25
            radius: 10
            speed: 0.3
            centerPull: 3 //higher is less
            verticalPull: -5 //zug nach unten
         */
        int centerX = 36;
        int centerZ = -2;
        int top = 23;
        int bottom = 16;
        int radius = 10;
        float speed = 0.3f;
        float centerPull = 3;
        float verticalPull = -0.1f;

        Vector center = new Vector(centerX, playerPos.getY(), centerZ);
        double distance = l.distance(new Location(e.getPlayer().getWorld(), center.getX(), center.getY(), center.getZ()));

        if (playerPos.getY() < top && playerPos.getY() > bottom ) {
            if (distance < radius && distance > 1) {
                Vector a = playerPos.subtract(center);
                Vector cross = a.getCrossProduct(new Vector(0, -1, 0))
                        .normalize()
                        .multiply(distance * centerPull)
                        .subtract(playerPos)
                        .normalize()
                        .multiply(speed)
                        .add(new Vector(0, verticalPull, 0));

                e.getPlayer().setVelocity(cross);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void jumpPad(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();

        ListIterator<JumpPad> iter = this.jumpPads.listIterator();

        while(iter.hasNext()){
            JumpPad pad = iter.next();

            Vector pos = pad.getPosition();
            final float force = 3f;
            int time = 10;

            if (l.getBlockX() == pos.getX() && l.getBlockY()-1 == pos.getY() && l.getBlockZ() == pos.getZ()) {
                final Vector direction = pad.getDirection().clone().normalize();
                p.setVelocity(direction);

//@todo nur die kraft nach oben darf abnehmen
                int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable() {
                    float ticks = 0;
                    @Override
                    public void run() {
                        float f = force * (1 - ticks / time);
                        p.setVelocity(direction.multiply(f));
                        this.ticks = this.ticks + 1;
//                        p.sendMessage(velocity.multiply(f).toString());
                    }
                } , 0L, 2L);

                //cancel delay task
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(taskID);
                    }
                } , (long)time);

            }
        }
    }

    @Override
    public void onConfigReload(ConfigManager configManager) {
        this.main.getLogger().info("Reload AreaEvent config");
        this.jumpPads = configManager.getJumpPads();
    }
}
