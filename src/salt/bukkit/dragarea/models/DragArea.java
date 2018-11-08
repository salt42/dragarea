package salt.bukkit.dragarea.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.LinkedHashMap;
import java.util.Map;

@SerializableAs("DragArea")
public class DragArea implements ConfigurationSerializable {
    //@todo add rename feature
    private final String name;
    private Vector pos1;
    private Vector pos2;
    private Vector direction;
    private float force = 1;

    public DragArea(String name, Vector position1, Vector position2, Vector direction, float force) {
        this.name = name;
        this.pos1 = position1;
        this.pos2 = position2;
        this.direction = direction;
        this.force = force;
    }

    public Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("name", this.name);
        result.put("pos1X", Double.valueOf(this.pos1.getX()));
        result.put("pos1Y", Double.valueOf(this.pos1.getY()));
        result.put("pos1Z", Double.valueOf(this.pos1.getZ()));
        result.put("pos2X", Double.valueOf(this.pos2.getX()));
        result.put("pos2Y", Double.valueOf(this.pos2.getY()));
        result.put("pos2Z", Double.valueOf(this.pos2.getZ()));
        result.put("dirX", Double.valueOf(this.direction.getX()));
        result.put("dirY", Double.valueOf(this.direction.getY()));
        result.put("dirZ", Double.valueOf(this.direction.getZ()));
        result.put("force", Float.valueOf(this.force));
        return result;
    }

    public static DragArea deserialize(Map<String, Object> args) {
        //position 1
        double pos1X = 0;
        double pos1Y = 0;
        double pos1Z = 0;
        if(args.containsKey("posX")) {
            pos1X = ((Double)args.get("pos1X")).doubleValue();
        }

        if(args.containsKey("posY")) {
            pos1Y = ((Double)args.get("pos1Y")).doubleValue();
        }

        if(args.containsKey("posZ")) {
            pos1Z = ((Double)args.get("pos1Z")).doubleValue();
        }

        //position 2
        double pos2X = 0;
        double pos2Y = 0;
        double pos2Z = 0;
        if(args.containsKey("posX")) {
            pos2X = ((Double)args.get("pos2X")).doubleValue();
        }

        if(args.containsKey("posY")) {
            pos2Y = ((Double)args.get("pos2Y")).doubleValue();
        }

        if(args.containsKey("posZ")) {
            pos2Z = ((Double)args.get("pos2Z")).doubleValue();
        }

        double dirX = 0;
        double dirY = 0;
        double dirZ = 0;
        if(args.containsKey("velX")) {
            dirX = ((Double)args.get("dirX")).doubleValue();
        }

        if(args.containsKey("velY")) {
            dirY = ((Double)args.get("dirY")).doubleValue();
        }

        if(args.containsKey("velZ")) {
            dirZ = ((Double)args.get("dirZ")).doubleValue();
        }

        float force = 0f;
        if(args.containsKey("velZ")) {
            force = (float)args.get("force");
        }

        return new DragArea((String)args.get("name"), new Vector(pos1X, pos1Y, pos1Z), new Vector(pos2X, pos2Y, pos2Z), new Vector(dirX, dirY, dirZ), force);
    }

    public Vector getPos1() {
        return pos1;
    }

    public void setPos1(Vector pos1) {
        this.pos1 = pos1;
    }

    public Vector getPos2() {
        return pos2;
    }

    public void setPos2(Vector pos2) {
        this.pos2 = pos2;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public float getForce() {
        return force;
    }

    public void setForce(float force) {
        this.force = force;
    }
}
