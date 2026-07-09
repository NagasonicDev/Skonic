package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.PlayerAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Player Animation")
@Description("Plays a player animation on a Citizens NPC.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("make npc with id 3 play animation SWING_MAIN_HAND")
public class EffCitizenPlayerAnimate extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenPlayerAnimate.class)
                .addPatterns(
                    "make (citizen|npc) %npc% play animation %string%"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private Expression<String> animExpr;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        String animName = animExpr.getSingle(event);
        if (npc == null || !npc.isSpawned() || !(npc.getEntity() instanceof Player) || animName == null) return;
        Player player = (Player) npc.getEntity();
        try {
            PlayerAnimation anim = PlayerAnimation.valueOf(animName.toUpperCase().replace(" ", "_"));
            anim.play(player);
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "make " + npcExpr.toString(event, debug) + " play animation " + animExpr.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        animExpr = (Expression<String>) exprs[1];
        return true;
    }
}
