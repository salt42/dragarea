package salt.bukkit.dragarea.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import salt.bukkit.dragarea.DragArea;
import salt.bukkit.dragarea.models.JumpPad;

public class JumpPadCommand implements CommandExecutor{
    DragArea main;

    public JumpPadCommand(DragArea main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            //@todo check permissions
            switch(strings[0]) {
                case "add":
                    try {
                        JumpPad pad = new JumpPad(strings[1],
                                                  new Vector(Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]) ),
                                                  new Vector(Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), Integer.parseInt(strings[7]) ));
                        main.configManager.addJumpPad(pad);
                    } catch (Exception e) {
                        player.sendMessage(e.toString());
                        return false;
                    }
                    break;
                case "edit":
//                    try {
                        JumpPad pad = main.configManager.getJumpPad(strings[1]);

                        if(pad == null) {
                            player.sendMessage("No JumpPad with name '" + strings[1] +"'!");//@todo remove
//                            throw new Error("No JumpPad with name '" + strings[1] +"'!");//@todo uncomment

                        }
                        switch(strings[2]) {
                            case "pos": //jumpPad edit NAME pos POS VEL
                                pad.setPosition(new Vector(Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])) );
                                break;
                            case "dir":
                                pad.setDirection(new Vector(Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])) );
                                break;
                            case "name":
                                //@todo
                                break;
                            default: //jumpPad edit NAME NEW_NAME POS VEL
                                //set name to strings[2]
                                pad.setPosition(new Vector(Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5])) );
                                pad.setDirection(new Vector(Integer.parseInt(strings[6]), Integer.parseInt(strings[7]), Integer.parseInt(strings[8])) );
                                break;
                        }
                        main.configManager.saveJumpPad(strings[1], pad);
//                    } catch (Exception e) {
//                        player.sendMessage(e.toString());
//                        return false;
//                    }
                    break;
                case "remove":
//                    try {
                        main.configManager.removeJumpPad(strings[1]);
//                    } catch (Exception e) {
//                        player.sendMessage(e.toString());
//                        return false;
//                    }
                    break;
            }
        }

        return true;
    }
}
