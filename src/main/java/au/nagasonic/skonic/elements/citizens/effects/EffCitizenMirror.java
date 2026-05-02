package au.nagasonic.skonic.elements.citizens.effects;

import au.nagasonic.skonic.Skonic;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.MirrorTrait;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Mirror")
@Description("Enable/Disable a Citizens NPC's Mirror Trait")
@Since("1.2.8")
@RequiredPlugins("Citizens")
public class EffCitizenMirror extends Effect {
    static {
        Skript.registerEffect(EffCitizenMirror.class,
                "enable (npc|citizen)[s] %npcs% mirror [n:with name] [e:[and] with equipment]",
                "disable (npc|citizen)[s] %npcs% mirror");
    }
    private Expression<NPC> npcsExpr;
    private int pattern;
    private boolean hasName;
    private boolean hasEquipment;


    @Override
    protected void execute(Event event) {
        if (((Citizens) CitizensAPI.getPlugin()).getPacketEventsListener() == null) {
            Skonic.logger().warn("PacketEvents must be enabled to use this effect: EffCitizenMirror");
            return;
        }
        if (npcsExpr != null){
            NPC[] npcs = npcsExpr.getArray(event);
            if (npcs != null){
                for (NPC npc : npcs){
                    if (pattern == 0){
                        MirrorTrait trait = npc.getOrAddTrait(MirrorTrait.class);
                        if (hasEquipment){
                            trait.setMirrorEquipment(true);
                        }else{
                            trait.setMirrorEquipment(false);
                        }
                        if (hasName){
                            trait.setEnabled(true);
                            trait.setMirrorName(true);
                        }else{
                            trait.setEnabled(true);
                            trait.setMirrorName(false);
                        }
                    }else{
                        MirrorTrait trait = npc.getOrAddTrait(MirrorTrait.class);
                        trait.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        if (pattern == 0){
            return "mirror npc/s " + npcsExpr.toString(event, debug) + " with name: (" + hasName + ") with equipment: (" + hasEquipment + ")";
        }else{
            return "disable npc/s " + npcsExpr.toString(event, debug) + " mirror";
        }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.pattern = matchedPattern;
        this.npcsExpr = (Expression<NPC>) exprs[0];
        this.hasName = parseResult.hasTag("n");
        this.hasEquipment = parseResult.hasTag("e");
        return true;
    }
}
