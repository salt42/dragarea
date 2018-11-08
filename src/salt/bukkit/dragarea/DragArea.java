package salt.bukkit.dragarea;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import salt.bukkit.dragarea.commands.DragAreaCommand;
import salt.bukkit.dragarea.commands.JumpPadCommand;
import salt.bukkit.dragarea.commands.SwirlCommand;
import salt.bukkit.dragarea.events.AreaEvent;
import salt.bukkit.dragarea.models.JumpPad;

import java.util.Arrays;
import java.util.List;

public class DragArea extends JavaPlugin {
    static {
        ConfigurationSerialization.registerClass(JumpPad.class, "JumpPad");
    }
    private FileConfiguration config = getConfig();
    public ConfigManager configManager;


    @Override
    public void onEnable() {
        //config
        this.configManager = new ConfigManager(this);

        //commands
        this.getCommand("jumpPad").setExecutor(new JumpPadCommand(this));
        this.getCommand("dragArea").setExecutor(new DragAreaCommand(this));
        this.getCommand("swirlArea").setExecutor(new SwirlCommand(this));

        //events
        new AreaEvent(this);


//        this.loadConfig();

//        getLogger().info("Enable DragArea");


//        SystemAutoSave save = (SystemAutoSave) getInstance(SystemAutoSave.class);
//        int saveTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, save, 12000L, 12000L);
//        getServer().getPluginManager().registerEvent(new AreaEvent(), this);
    }

    @Override
    public void onDisable() {

    }


}
