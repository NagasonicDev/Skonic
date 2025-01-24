package au.nagasonic.skonic.elements.hitbox;

import com.google.gson.JsonObject;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.BoundingBoxTrait;

public class NPCHitbox {
    private float scale, width, height;

    public NPCHitbox(float scale, float width, float height){
        this.scale = scale;
        this.width = width;
        this.height = height;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("scale", scale);
        jsonObject.addProperty("width", width);
        jsonObject.addProperty("height", height);
        return jsonObject;
    }

    @Override
    public String toString(){
        return toJson().toString();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return this.scale;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return this.width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHitbox(NPC npc) {
        BoundingBoxTrait trait = npc.getOrAddTrait(BoundingBoxTrait.class);
        trait.setHeight(this.height);
        trait.setWidth(this.width);
        trait.setScale(this.scale);
    }

    public static NPCHitbox fromNPC(NPC npc) {
        BoundingBoxTrait trait = npc.getOrAddTrait(BoundingBoxTrait.class);
        float scale = trait.getAdjustedDimensions().height / trait.get().toDimensions().height;
        float width = trait.get().toDimensions().width;
        float height = trait.get().toDimensions().height;
        return new NPCHitbox(scale, width, height);
    }
}
