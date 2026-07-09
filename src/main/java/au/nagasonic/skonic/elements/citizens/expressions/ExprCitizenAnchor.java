package au.nagasonic.skonic.elements.citizens.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Anchors;
import net.citizensnpcs.util.Anchor;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Citizen Anchors")
@Description({"The anchor locations of a Citizens NPC.", "Changing: SET expects locations, ADD expects a location, REMOVE expects a location, DELETE removes all."})
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples({"set {_locs::*} to anchors of {_npc}", "add location(0, 0, 0, world \"world\") to anchors of {_npc}"})
public class ExprCitizenAnchor extends SimpleExpression<Location> {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EXPRESSION,
            DefaultSyntaxInfos.Expression.builder(ExprCitizenAnchor.class, Location.class)
                .addPatterns(
                    "anchors of (citizen|npc) %npc%",
                    "(citizen|npc) %npc%'[s] anchors"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;

    @Override
    protected Location @Nullable [] get(Event event) {
        NPC npc = npcExpr.getSingle(event);
        if (npc == null || !npc.hasTrait(Anchors.class)) return null;
        List<Anchor> anchors = npc.getOrAddTrait(Anchors.class).getAnchors();
        if (anchors == null || anchors.isEmpty()) return null;
        List<Location> locs = new ArrayList<>();
        for (Anchor anchor : anchors) {
            locs.add(anchor.getLocation().clone());
        }
        return locs.toArray(new Location[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "anchors of " + npcExpr.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        return true;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD) return CollectionUtils.array(Location.class);
        if (mode == Changer.ChangeMode.REMOVE) return CollectionUtils.array(Location.class);
        if (mode == Changer.ChangeMode.DELETE) return CollectionUtils.array();
        if (mode == Changer.ChangeMode.SET) return CollectionUtils.array(Location.class);
        return null;
    }

    @Override
    public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
        NPC npc = npcExpr.getSingle(event);
        if (npc == null) return;
        Anchors anchors = npc.getOrAddTrait(Anchors.class);
        if (mode == Changer.ChangeMode.SET && delta != null) {
            for (Anchor a : new ArrayList<>(anchors.getAnchors())) {
                anchors.removeAnchor(a);
            }
            for (Object obj : delta) {
                if (obj instanceof Location) {
                    anchors.addAnchor("anchor-" + System.currentTimeMillis(), (Location) obj);
                }
            }
        } else if (mode == Changer.ChangeMode.ADD && delta != null && delta[0] instanceof Location) {
            anchors.addAnchor("anchor-" + System.currentTimeMillis(), (Location) delta[0]);
        } else if (mode == Changer.ChangeMode.REMOVE && delta != null && delta[0] instanceof Location) {
            Location target = (Location) delta[0];
            for (Anchor a : new ArrayList<>(anchors.getAnchors())) {
                if (a.getLocation().equals(target)) {
                    anchors.removeAnchor(a);
                    break;
                }
            }
        } else if (mode == Changer.ChangeMode.DELETE) {
            for (Anchor a : new ArrayList<>(anchors.getAnchors())) {
                anchors.removeAnchor(a);
            }
        }
    }
}
