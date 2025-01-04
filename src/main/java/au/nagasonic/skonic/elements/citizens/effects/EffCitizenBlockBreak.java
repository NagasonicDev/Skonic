package au.nagasonic.skonic.elements.citizens.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.ai.tree.StatusMapper;
import net.citizensnpcs.api.npc.BlockBreaker;
import net.citizensnpcs.api.npc.BlockBreaker.BlockBreakerConfiguration;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@Name("Make Citizen Break Block")
@Description({"Make a Citizens NPC break a block in a radius.", "No idea how radius works (or what it does), but Citizens has it so I do too."})
@Since("1.2")
@RequiredPlugins("Citizens")
@Examples("make citizen with id 2 break {_block} with radius 3")
public class EffCitizenBlockBreak extends Effect {
    static {
        Skript.registerEffect(EffCitizenBlockBreak.class,
                "make %npc% break %block% [with radius %number%]");
    }
    private Expression<NPC> npcExpr;
    private Expression<Block> blockExpr;
    private Expression<Number> radiiExpr;
    @Override
    protected void execute(Event event) {
        if (npcExpr != null && blockExpr != null){
            NPC npc = npcExpr.getSingle(event);
            Block block = blockExpr.getSingle(event);
            if (npc != null && block != null){
                BlockBreakerConfiguration cfg = new BlockBreakerConfiguration();
                if (radiiExpr != null){
                    Number radius = radiiExpr.getSingle(event);
                    cfg.radius(radius.doubleValue());
                }else{
                    cfg.radius(-1);
                }
                if (npc.getEntity() instanceof InventoryHolder) {
                    cfg.blockBreaker((block1, itemstack) -> {
                        Inventory inventory = ((InventoryHolder) npc.getEntity()).getInventory();
                        Location location = npc.getEntity().getLocation();
                        Collection<ItemStack> drops = block.getDrops(itemstack);
                        block1.setType(Material.AIR);
                        for (ItemStack drop : drops){
                            for (ItemStack unadded : inventory.addItem(drop).values()){
                                location.getWorld().dropItemNaturally(npc.getEntity().getLocation(), unadded);
                            }
                        }
                    });
                }
                BlockBreaker breaker = npc.getBlockBreaker(block, cfg);
                npc.getDefaultGoalController().addBehavior(StatusMapper.singleUse(breaker), 1);
            }
        }

    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        if (radiiExpr == null){
            return "make " + npcExpr.toString(event, b) + " break " + blockExpr.toString(event, b);
        }else return "make " + npcExpr.toString(event, b) + " break " + blockExpr.toString(event, b) + " with radius " + radiiExpr.toString(event, b);

    }

    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        npcExpr = (Expression<NPC>) exprs[0];
        blockExpr = (Expression<Block>) exprs[1];
        if (parseResult.hasTag("radius")){
            radiiExpr = (Expression<Number>) exprs[2];
        }
        return true;
    }
}
