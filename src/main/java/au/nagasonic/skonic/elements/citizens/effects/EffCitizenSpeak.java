package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import org.skriptlang.skript.registration.SyntaxRegistry;
import org.skriptlang.skript.registration.SyntaxInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Speak")
@Description("Makes a Citizens NPC speak a message.")
@RequiredPlugins("Citizens")
@Since("1.3")
@Examples("make npc with id 3 say \"Hello!\"")
public class EffCitizenSpeak extends Effect {
    public static void register(SyntaxRegistry syntaxRegistry) {
        syntaxRegistry.register(
            SyntaxRegistry.EFFECT,
            SyntaxInfo.builder(EffCitizenSpeak.class)
                .addPatterns(
                    "make (citizen|npc) %npc% say %strings%"
                )
                .build()
        );
    }
    private Expression<NPC> npcExpr;
    private Expression<String> messageExpr;

    @Override
    protected void execute(Event event) {
        NPC npc = npcExpr.getSingle(event);
        String[] messages = messageExpr.getArray(event);
        if (npc != null && messages != null) {
            for (String msg : messages) {
                if (msg != null) {
                    npc.speak(new SpeechContext(msg));
                }
            }
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "make " + npcExpr.toString(event, debug) + " say " + messageExpr.toString(event, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        messageExpr = (Expression<String>) exprs[1];
        return true;
    }
}
