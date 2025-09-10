package au.nagasonic.skonic.elements.citizens.expressions;

import au.nagasonic.skonic.elements.skins.Skin;
import au.nagasonic.skonic.elements.util.Util;
import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.jetbrains.annotations.Nullable;

@Name("Citizen Skin")
@Description("Returns the skin of a Citizen.")
@Since("1.2.2-b1")
@RequiredPlugins("Citizens")
@Examples("broadcast the npc skin of {_npc}")
public class ExprCitizenSkin extends SimplePropertyExpression<NPC, Skin> {
    static {
        register(ExprCitizenSkin.class, Skin.class, "(citizen|npc) skin", "npc");
    }
    @Override
    public @Nullable Skin convert(NPC npc) {
        SkinTrait trait = npc.getOrAddTrait(SkinTrait.class);
        Util.log("hi");
        if (trait.getTexture() == null || trait.getSignature() == null){
            return new Skin("nig", "ger");
        }
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
