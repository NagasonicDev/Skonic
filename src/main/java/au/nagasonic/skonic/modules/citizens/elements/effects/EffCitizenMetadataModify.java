package au.nagasonic.skonic.modules.citizens.elements.effects;

import au.nagasonic.skonic.Skonic;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import net.citizensnpcs.api.npc.NPC.Metadata;
import org.jetbrains.annotations.Nullable;

@Name("Modify Citizen Metadata")
@Description("Modify the metadata of a Citizens NPC")
@RequiredPlugins("Citizens")
@Since("1.2.7")
public class EffCitizenMetadataModify extends AsyncEffect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenMetadataModify.class)
                .addPatterns(
                    "(modify|set) [p:persistent] (citizen|npc) %npcmetadata% [metadata] of %npcs% to (%-boolean%|%number%)",
                    "(modify|set) %npcs%['s] [p:persistent] (citizen|npc) %npcmetadata% [metadata] to (%-boolean%|%number%)"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private Expression<Metadata> metadataExpr;
    private Expression<Boolean> boolExpr;
    private Expression<Number> numberExpr;
    private int pattern;
    private boolean p;

    @Override
    protected void execute(Event event) {
        if (npcExpr == null || metadataExpr == null || pattern > 2) return;
        if (boolExpr == null && numberExpr == null) return;
        NPC[] npcs = npcExpr.getArray(event);
        if (npcs == null) return;
        Metadata metadata = metadataExpr.getSingle(event);
        Object value = null;
        Class<? extends Object> aclass = Object.class;
        if (boolExpr != null){
            value = boolExpr.getSingle(event);
            aclass = Boolean.class;
        } else if (numberExpr != null) {
            Number num = numberExpr.getSingle(event);
            Double dval = num.doubleValue();
            Integer ival = num.intValue();
            if (dval > ival){
                value = dval;
                aclass = Double.class;
            }else {
                value = ival;
                aclass = Integer.class;
            }
        }
        if (metadata == null || value == null) return;
        if (metadata.accepts(aclass)){
            for (NPC npc : npcs){
                if (p){
                    npc.data().setPersistent(metadata, value);
                }else{
                    npc.data().set(metadata, value);
                }
            }
        }else{
            Skonic.logger().severe("Metadata " + metadata.getKey() + " does not accept " + value.getClass() + " as a possible value.");
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.pattern = i;
        this.p = parseResult.hasTag("p");
        if (pattern == 0){
            this.npcExpr = (Expression<NPC>) exprs[1];
            this.metadataExpr = (Expression<Metadata>) exprs[0];
            this.boolExpr = (Expression<Boolean>) exprs[2];
            this.numberExpr = (Expression<Number>) exprs[3];
        }else{
            this.npcExpr = (Expression<NPC>) exprs[0];
            this.metadataExpr = (Expression<Metadata>) exprs[1];
            this.boolExpr = (Expression<Boolean>) exprs[2];
            this.numberExpr = (Expression<Number>) exprs[3];
        }
        return true;
    }
}
