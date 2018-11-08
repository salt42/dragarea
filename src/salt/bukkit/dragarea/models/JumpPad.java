package salt.bukkit.dragarea.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("JumpPad")
public class JumpPad implements ConfigurationSerializable {
    //@todo add rename feature
    private final String name;
    private Vector position;
    private Vector direction;

    public JumpPad(String name, Vector position, Vector direction) {
        this.name = name;
        this.position = position;
        this.direction = direction;
    }

    public Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("name", this.name);
        result.put("posX", Double.valueOf(this.position.getX()));
        result.put("posY", Double.valueOf(this.position.getY()));
        result.put("posZ", Double.valueOf(this.position.getZ()));
        result.put("velX", Double.valueOf(this.direction.getX()));
        result.put("velY", Double.valueOf(this.direction.getY()));
        result.put("velZ", Double.valueOf(this.direction.getZ()));
        return result;
    }

    public static JumpPad deserialize(Map<String, Object> args) {
        double posX = 0;
        double posY = 0;
        double posZ = 0;
        if(args.containsKey("posX")) {
            posX = ((Double)args.get("posX")).doubleValue();
        }

        if(args.containsKey("posY")) {
            posY = ((Double)args.get("posY")).doubleValue();
        }

        if(args.containsKey("posZ")) {
            posZ = ((Double)args.get("posZ")).doubleValue();
        }

        double velX = 0;
        double velY = 0;
        double velZ = 0;
        if(args.containsKey("velX")) {
            velX = ((Double)args.get("velX")).doubleValue();
        }

        if(args.containsKey("velY")) {
            velY = ((Double)args.get("velY")).doubleValue();
        }

        if(args.containsKey("velZ")) {
            velZ = ((Double)args.get("velZ")).doubleValue();
        }
        return new JumpPad((String)args.get("name"), new Vector(posX, posY, posZ), new Vector(velX, velY, velZ));
    }

    public String getName() {
        return name;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }
}
