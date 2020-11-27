package de.crazymemecoke.features.modules.movement;

import de.crazymemecoke.Client;
import de.crazymemecoke.manager.modulemanager.ModuleInfo;
import de.crazymemecoke.manager.settingsmanager.Setting;
import de.crazymemecoke.manager.eventmanager.Event;
import de.crazymemecoke.manager.eventmanager.impl.EventUpdate;
import de.crazymemecoke.manager.modulemanager.Category;
import de.crazymemecoke.manager.modulemanager.Module;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;

import static net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

@ModuleInfo(name = "Step", category = Category.MOVEMENT, description = "Automatically jumps real fast when colliding horizontally")
public class Step extends Module {

    public Setting mode = new Setting("Mode", this, "Vanilla", new String[] {"Vanilla", "NCP"});
    public Setting stepHeight = new Setting("Step Height", this, 1.0, 1.0, 10.0, false);
    public Setting reverse = new Setting("Reverse", this, false);

    @Override
    public void onToggle() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.5f;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {

            if (reverse.isToggled()) {
                doReverse();
            }

            if (mc.thePlayer.isCollidedHorizontally) {
                switch (mode.getCurrentMode()) {
                    case "Vanilla": {
                        doVanilla();
                        break;
                    }
                    case "NCP": {
                        doNCP();
                        break;
                    }
                }
            }
        }
    }

    private void doNCP() {
        double stepHeight = Client.main().setMgr().settingByName("Step Height", this).getCurrentValue();

        final double posX = mc.thePlayer.posX;
        final double posY = mc.thePlayer.posY;
        final double posZ = mc.thePlayer.posZ;
        mc.thePlayer.stepHeight = (float) stepHeight;
        mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(posX, posY + 0.42, posZ, mc.thePlayer.onGround));
        mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(posX, posY + 0.753, posZ, mc.thePlayer.onGround));
    }

    private void doVanilla() {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    }

    private void doReverse() {
        BlockPos underPlayer = new BlockPos(mc.thePlayer.getPosition().getX(), mc.thePlayer.getPosition().getY() - 2, mc.thePlayer.getPosition().getZ());
        IBlockState blockUnderPlayer = mc.thePlayer.worldObj.getBlockState(underPlayer);

        if (blockUnderPlayer.getBlock() instanceof BlockAir && !mc.thePlayer.capabilities.isFlying) {
            mc.thePlayer.motionY = -1;
        }
    }
}
