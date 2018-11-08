package salt.bukkit.dragarea;

import salt.bukkit.dragarea.models.JumpPad;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private DragArea main;
    private ArrayList<IConfigReloadEvent> reloadEventHandler = new ArrayList<>();

    private List<JumpPad> jumpPads;

    ConfigManager(DragArea main) {
        this.main = main;
        this.loadConfig();
    }

    public void addJumpPad(JumpPad jumpPad) {
        if (this.getJumpPad(jumpPad.getName()) != null) throw new Error("JumpPad Name '"+ jumpPad.getName() +"' already taken!");

        this.jumpPads.add(jumpPad);
        this.main.getConfig().set("jumpPads", this.jumpPads);
        this.save();
    }

    public void saveJumpPad(String oldName, JumpPad jumpPad) {
        //@todo refactor for name changing
        if (!oldName.equals(jumpPad.getName()) && this.getJumpPad(jumpPad.getName()) != null) throw new Error("JumpPad Name '"+ jumpPad.getName() +"' already taken!");

        for (JumpPad pad : this.jumpPads) {
            if (oldName.equals(pad.getName())) {
                pad.setPosition(jumpPad.getPosition());
                pad.setDirection(jumpPad.getDirection());
                //@todo set name
                break;
            }
        }

        this.main.getConfig().set("jumpPads", this.jumpPads);
        this.save();
    }

    public void removeJumpPad(String jumpPadName) {
        boolean found = false;
        List<JumpPad> pads = new ArrayList<>();
        for (JumpPad pad : this.jumpPads) {
            if (!jumpPadName.equals(pad.getName())) {
                pads.add(pad);
                found = true;
            }
        }
        if (!found) throw new Error("No JumpPad with name '" + jumpPadName +"'!");

        this.jumpPads = pads;
        this.main.getConfig().set("jumpPads", this.jumpPads);
        this.save();
    }

    public List<JumpPad> getJumpPads() {
        return this.jumpPads;
    }

    public JumpPad getJumpPad(String string) {
        for (JumpPad pad : this.jumpPads) {
            if (string.equals(pad.getName())) return pad;
        }
        return null;
    }

    public void registerReloadEvent(IConfigReloadEvent target) {
        reloadEventHandler.add(target);
        this.triggerReload();
    }

    private void save() {
        this.main.saveConfig();
        this.triggerReload();
    }

    private void reload() {
        this.main.reloadConfig();
        this.jumpPads = (List<JumpPad>)this.main.getConfig().getList("jumpPads");
    }

    private void triggerReload() {
        for (IConfigReloadEvent listener : this.reloadEventHandler) {
            listener.onConfigReload(this);
        }
    }

    private void loadConfig() {
        this.main.saveDefaultConfig();

        //defaults
//        List<JumpPad> jumpPadList = Arrays.asList(new JumpPad("Pad 1", new Vector(-10,3,-10), new Vector(1,1,1)));
//        this.config.set("jumpPads", jumpPadList);
//        this.config.options().copyDefaults(false);
//        this.main.saveConfig();
        this.reload();
        this.triggerReload();
    }

}
