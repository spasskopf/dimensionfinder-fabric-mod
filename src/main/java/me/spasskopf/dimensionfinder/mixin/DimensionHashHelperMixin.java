package me.spasskopf.dimensionfinder.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.dimension.DimensionHashHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionHashHelper.class)
public class DimensionHashHelperMixin {
    @Inject(method = "getHash", at = @At("RETURN"), cancellable = true)
    private static void getHash(String name, CallbackInfoReturnable<Integer> cir) {
        if (MinecraftClient.getInstance().player != null) {
            BaseText prefix = new LiteralText("[Dimensionfinder]");
            prefix.setStyle(prefix.getStyle().setColor(Formatting.AQUA));
            BaseText message = new LiteralText(String.format("Hash for String %s: %d", name, cir.getReturnValue()));
            message.setStyle(message.getStyle().setColor(Formatting.GREEN));

            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(prefix.append(message));

        } else {
            System.out.printf("Hash for String %s: %d%n", name, cir.getReturnValue());
        }
    }
}
