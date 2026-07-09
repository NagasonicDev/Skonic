package au.nagasonic.skonic.registration.modules;

import au.nagasonic.skonic.classes.citizens.other.CitizenTypes;
import au.nagasonic.skonic.elements.citizens.conditions.*;
import au.nagasonic.skonic.elements.citizens.effects.*;
import au.nagasonic.skonic.elements.citizens.events.CitizenEvents;
import au.nagasonic.skonic.elements.citizens.expressions.*;
import au.nagasonic.skonic.registration.HierarchicalAddonModule;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.registration.SyntaxRegistry;

public class CitizensModule extends HierarchicalAddonModule {

    @NotNull
    @Override
    public String name() {
        return "Citizens";
    }

    @Override
    public void loadSelf(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = Skript.instance().syntaxRegistry();

        CitizenTypes.register(registry);

        CondCitizenCanLoadChunks.register(registry);
        CondCitizenHasForcefield.register(registry);
        CondCitizenIsAggressive.register(registry);
        CondCitizenIsCollidable.register(registry);
        CondCitizenIsFlyable.register(registry);
        CondCitizenIsGlowing.register(registry);
        CondCitizenIsLeashable.register(registry);
        CondCitizenIsNavigating.register(registry);
        CondCitizenIsOwnedBy.register(registry);
        CondCitizenIsPaused.register(registry);
        CondCitizenIsTargetable.register(registry);
        CondCitizenIsVulnerable.register(registry);
        CondCitizenKnockback.register(registry);
        CondCitizenPicksUpItems.register(registry);
        CondCitizenShouldJump.register(registry);
        CondCitizenUsesMCAI.register(registry);
        CondEntityIsCitizen.register(registry);

        EffChangeCitizenSkin.register(registry);
        EffChangeCitizenSkinName.register(registry);
        EffChangeCitizenSkinURL.register(registry);
        EffCitizenAggressive.register(registry);
        EffCitizenAttack.register(registry);
        EffCitizenBlockBreak.register(registry);
        EffCitizenCollidable.register(registry);
        EffCitizenFlyable.register(registry);
        EffCitizenGlow.register(registry);
        EffCitizenLoadChunks.register(registry);
        EffCitizenMetadataModify.register(registry);
        EffCitizenMinecraftAI.register(registry);
        EffCitizenMirror.register(registry);
        EffCitizenMount.register(registry);
        EffCitizenPathfind.register(registry);
        EffCitizenPause.register(registry);
        EffCitizenPlayerAnimate.register(registry);
        EffCitizenRotate.register(registry);
        EffCitizenSpeak.register(registry);
        EffCitizenTargetable.register(registry);
        EffCitizenVulnerable.register(registry);
        EffCloneCitizen.register(registry);
        EffDeleteCitizen.register(registry);
        EffDespawnCitizen.register(registry);
        EffLockAge.register(registry);
        EffSpawnCitizen.register(registry);

        ExprActivationRange.register(registry);
        ExprAllCitizens.register(registry);
        ExprCitizenAge.register(registry);
        ExprCitizenAnchor.register(registry);
        ExprCitizenAttributes.register(registry);
        ExprCitizenAvoidWater.register(registry);
        ExprCitizenBehaviour.register(registry);
        ExprCitizenCosmeticEntity.register(registry);
        ExprCitizenDrops.register(registry);
        ExprCitizenEntityType.register(registry);
        ExprCitizenFaceLocation.register(registry);
        ExprCitizenGamemode.register(registry);
        ExprCitizenGlowColor.register(registry);
        ExprCitizenGravity.register(registry);
        ExprCitizenHidden.register(registry);
        ExprCitizenHome.register(registry);
        ExprCitizenID.register(registry);
        ExprCitizenInventory.register(registry);
        ExprCitizenKnockback.register(registry);
        ExprCitizenLeashable.register(registry);
        ExprCitizenMetadata.register(registry);
        ExprCitizenMoveDestination.register(registry);
        ExprCitizenNameTagVisibility.register(registry);
        ExprCitizenOpenDoors.register(registry);
        ExprCitizenOwner.register(registry);
        ExprCitizenPacket.register(registry);
        ExprCitizenPathfindingRange.register(registry);
        ExprCitizenPicksUpItems.register(registry);
        ExprCitizenPlayerFilter.register(registry);
        ExprCitizenPlayerList.register(registry);
        ExprCitizenPose.register(registry);
        ExprCitizenRotate.register(registry);
        ExprCitizenRotateSettings.register(registry);
        ExprCitizenUUID.register(registry);
        ExprCitizenWith.register(registry);
        ExprLastCreatedCitizen.register(registry);
        ExprLookClose.register(registry);
        ExprNameOfCitizen.register(registry);
        ExprCitizenTablistVisibility.register(registry);
        ExprCitizenTarget.register(registry);
        ExprCitizenTrackingRange.register(registry);
        ExprCitizenUseItem.register(registry);
        ExprCitizenWander.register(registry);
        ExprCitizenScaledMaxHealth.register(registry);
        ExprCitizenShops.register(registry);
        ExprCitizenShouldJump.register(registry);
        ExprCitizenSitting.register(registry);
        ExprCitizenSkin.register(registry);
        ExprCitizenSkinLayers.register(registry);
        ExprCitizenSneaking.register(registry);
        ExprCitizenSounds.register(registry);
        ExprCitizenSpeak.register(registry);
        ExprCitizenSpeed.register(registry);
        ExprCitizenStoredLocation.register(registry);
        ExprCitizenSwim.register(registry);

        CitizenEvents.register(registry);
    }
}
