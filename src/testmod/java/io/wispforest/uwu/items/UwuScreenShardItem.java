package io.wispforest.uwu.items;

import io.wispforest.uwu.client.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UwuScreenShardItem extends Item {

    public UwuScreenShardItem() {
        super(new Settings().rarity(Rarity.UNCOMMON));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            MinecraftClient.getInstance().setScreen(user.isSneaking() ? new SelectUwuScreenScreen() : new UwuConfigScreen());
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}