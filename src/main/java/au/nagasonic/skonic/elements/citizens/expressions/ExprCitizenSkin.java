package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.elements.skins.Skin;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.jetbrains.annotations.Nullable;

public class ExprCitizenSkin extends SimplePropertyExpression<NPC, Skin> {
    static {
        register(ExprCitizenSkin.class, Skin.class, "(citizen|npc) skin", "npc");
    }
    @Override
    public @Nullable Skin convert(NPC npc) {
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        return new Skin(trait.getTexture(), trait.getSignature());
    }

    @Override
    protected String getPropertyName() {
        return "citizen skin";
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }
}
