package au.nagasonic.skonic.modules.citizens;

import au.nagasonic.skonic.classes.citizens.other.CitizenTypes;
import au.nagasonic.skonic.modules.citizens.elements.conditions.*;
import au.nagasonic.skonic.modules.citizens.elements.effects.*;
import au.nagasonic.skonic.modules.citizens.elements.events.CitizenEvents;
import au.nagasonic.skonic.modules.citizens.elements.expressions.*;
import au.nagasonic.skonic.modules.citizens.forcefield.ForcefieldModule;
import au.nagasonic.skonic.modules.citizens.hitbox.HitboxModule;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.HierarchicalAddonModule;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.bukkit.lang.eventvalue.EventValueRegistry;

import java.util.List;

public class CitizensModule extends HierarchicalAddonModule {

    public CitizensModule(AddonModule parent) {
        super(parent);
    }

    @Override
    public Iterable<AddonModule> children() {
        return List.of(
                new ForcefieldModule(this),
                new HitboxModule(this)
        );
    }

    @Override
    public String name() {
        return "citizens";
    }

    @Override
    protected boolean canLoadSelf(@NotNull SkriptAddon addon) {
        return Bukkit.getPluginManager().isPluginEnabled("Citizens");
    }

    @Override
    protected void loadSelf(SkriptAddon addon) {
        register(addon,
                CitizenTypes::register,
                CondCitizenCanLoadChunks::register,
                CondCitizenHasForcefield::register,
                CondCitizenIsAggressive::register,
                CondCitizenIsCollidable::register,
                CondCitizenIsFlyable::register,
                CondCitizenIsGlowing::register,
                CondCitizenIsLeashable::register,
                CondCitizenIsNavigating::register,
                CondCitizenIsOwnedBy::register,
                CondCitizenIsPaused::register,
                CondCitizenIsTargetable::register,
                CondCitizenIsVulnerable::register,
                CondCitizenKnockback::register,
                CondCitizenPicksUpItems::register,
                CondCitizenShouldJump::register,
                CondCitizenUsesMCAI::register,
                CondEntityIsCitizen::register,
                EffChangeCitizenSkin::register,
                EffChangeCitizenSkinURL::register,
                ExprCitizenSkinName::register,
                EffCitizenAggressive::register,
                EffCitizenAttack::register,
                EffCitizenBlockBreak::register,
                EffCitizenCollidable::register,
                EffCitizenFlyable::register,
                EffCitizenGlow::register,
                EffCitizenLoadChunks::register,
                EffCitizenMetadataModify::register,
                EffCitizenMinecraftAI::register,
                EffCitizenMirror::register,
                EffCitizenMount::register,
                EffCitizenPathfind::register,
                EffCitizenPause::register,
                EffCitizenPlayerAnimate::register,
                EffCitizenRotate::register,
                EffCitizenSpeak::register,
                EffCitizenTargetable::register,
                EffCitizenVulnerable::register,
                EffCloneCitizen::register,
                EffDeleteCitizen::register,
                EffDespawnCitizen::register,
                EffLockAge::register,
                EffSpawnCitizen::register,
                ExprActivationRange::register,
                ExprAllCitizens::register,
                ExprCitizenAge::register,
                ExprCitizenAnchor::register,
                ExprCitizenAttributes::register,
                ExprCitizenAvoidWater::register,
                ExprCitizenBehaviour::register,
                ExprCitizenCosmeticEntity::register,
                ExprCitizenDrops::register,
                ExprCitizenEntityType::register,
                ExprCitizenFaceLocation::register,
                ExprCitizenGamemode::register,
                ExprCitizenGlowColor::register,
                ExprCitizenGravity::register,
                ExprCitizenHidden::register,
                ExprCitizenHome::register,
                ExprCitizenID::register,
                ExprCitizenInventory::register,
                ExprCitizenKnockback::register,
                ExprCitizenLeashable::register,
                ExprCitizenMetadata::register,
                ExprCitizenMoveDestination::register,
                ExprCitizenNameTagVisibility::register,
                ExprCitizenOpenDoors::register,
                ExprCitizenOwner::register,
                ExprCitizenPacket::register,
                ExprCitizenPathfindingRange::register,
                ExprCitizenPicksUpItems::register,
                ExprCitizenPlayerFilter::register,
                ExprCitizenPlayerList::register,
                ExprCitizenPose::register,
                ExprCitizenRotate::register,
                ExprCitizenRotateSettings::register,
                ExprCitizenUUID::register,
                ExprCitizenWith::register,
                ExprLastCreatedCitizen::register,
                ExprLookClose::register,
                ExprNameOfCitizen::register,
                ExprCitizenTablistVisibility::register,
                ExprCitizenTarget::register,
                ExprCitizenTrackingRange::register,
                ExprCitizenUseItem::register,
                ExprCitizenWander::register,
                ExprCitizenScaledMaxHealth::register,
                ExprCitizenShops::register,
                ExprCitizenShouldJump::register,
                ExprCitizenSitting::register,
                ExprCitizenSkin::register,
                ExprCitizenSkinLayers::register,
                ExprCitizenSneaking::register,
                ExprCitizenSounds::register,
                ExprCitizenSpeak::register,
                ExprCitizenSpeed::register,
                ExprCitizenStoredLocation::register,
                ExprCitizenSwim::register
        );
        CitizenEvents.register(addon.syntaxRegistry(), addon.registry(EventValueRegistry.class));
    }
}
