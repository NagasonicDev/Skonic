package au.nagasonic.skonic.elements.citizens;

import com.google.gson.JsonObject;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.ForcefieldTrait;

public class Forcefield {
    public double width,strength,height,vertStrength;

    public Forcefield(double width, double height, double strength, double vertStrength){
        this.width = width;
        this.height = height;
        this.strength = strength;
        this.vertStrength = vertStrength;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("width", width);
        jsonObject.addProperty("height", height);
        jsonObject.addProperty("strength", strength);
        jsonObject.addProperty("vertStrength", vertStrength);
        return jsonObject;
    }

    @Override
    public String toString(){
        return toJson().toString();
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setStrength(double strength){
        this.strength = strength;
    }

    public void setVertStrength(double vertStrength){
        this.vertStrength = vertStrength;
    }

    public void setForcefield(NPC npc){
        ForcefieldTrait trait = npc.getOrAddTrait(ForcefieldTrait.class);
        trait.setWidth(width);
        trait.setHeight(height);
        trait.setStrength(strength);
        trait.setVerticalStrength(vertStrength);
    }
}
