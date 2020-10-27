package me.linus.momentum.module;

import me.linus.momentum.api.events.mixin.RenderEvent;
import me.linus.momentum.module.modules.combat.*;
import me.linus.momentum.module.modules.gui.*;
import me.linus.momentum.module.modules.misc.*;
import me.linus.momentum.module.modules.movement.*;
import me.linus.momentum.module.modules.player.*;
import me.linus.momentum.module.modules.render.*;
import me.linus.momentum.utils.Wrapper;
import me.linus.momentum.utils.utils.EntityUtil;
import me.linus.momentum.utils.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ModuleManager {
    private static ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
       // keep these organized by length - keep in mind some of these have hudinfo attached to them
       modules.add(new Velocity());
       // modules.add(new LongJump());
       modules.add(new QuickEXP());
       modules.add(new QuickMine());
       modules.add(new Criticals());
       modules.add(new AutoCity());
       // modules.add(new AutoCrystal());
       modules.add(new Offhand());
       modules.add(new HoleMovement());
       // modules.add(new InventoryViewer());
       // modules.add(new ActiveModules());
       modules.add(new HandProgress());
       modules.add(new ItemPreview());
       modules.add(new AutoTotem());
       modules.add(new Step());
       modules.add(new SwingPrevent());
       // modules.add(new SkinBlinker());
       modules.add(new EntityAlert());
       modules.add(new PlayerAlert());
       // modules.add(new TargetHUD());
       modules.add(new BiomeSpeed());
       modules.add(new AntiHunger());
       modules.add(new DiscordRPC());
       modules.add(new FakePlayer());
       modules.add(new AutoArmor());
       modules.add(new WorldColor());
       modules.add(new PacketMine());
       modules.add(new MultiTask());
       modules.add(new NoWeather());
       modules.add(new ExtraSlots());
       // modules.add(new GappleChug());
       modules.add(new ChatSuffix());
       modules.add(new ClickGUI());
       modules.add(new NoRender());
       modules.add(new Web());
       modules.add(new FullBright());
       // modules.add(new StorageESP());
       modules.add(new FastPlace());
       modules.add(new Scaffold());
       modules.add(new Surround());
       // modules.add(new FreeCam());
       modules.add(new GUIMove());
       // modules.add(new Quiver());
       modules.add(new FastBow());
       modules.add(new Skeleton());
       modules.add(new Refill());
       modules.add(new HoleESP());
       modules.add(new NoSlow());
       modules.add(new Sprint());
       modules.add(new Capes());
       modules.add(new PrefixChat());
       modules.add(new Timer());
       modules.add(new HUD());
       modules.add(new ESP());
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        Module m = modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m;
    }

    public static boolean isModuleToggled(String name){
        Module mod = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return mod.isToggled();
    }

    public static void render(RenderWorldLastEvent event) {
        Minecraft.getMinecraft().profiler.startSection("momentum");

        Minecraft.getMinecraft().profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();

        GlStateManager.glLineWidth(1f);
        Vec3d renderPos = EntityUtil.getInterpolatedPos(Wrapper.getPlayer(), event.getPartialTicks());

        RenderEvent e = new RenderEvent(RenderUtil.INSTANCE, renderPos);
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isToggled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.render(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");
        GlStateManager.glLineWidth(1f);

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        RenderUtil.releaseGL();
        Minecraft.getMinecraft().profiler.endSection();

        Minecraft.getMinecraft().profiler.endSection();
    }

    public static void onRender() {
        modules.stream().filter(Module::isToggled).forEach(Module::onRender);
    }
}
