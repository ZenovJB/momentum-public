package me.linus.momentum;

import me.linus.momentum.api.events.EventProcessor;
import me.linus.momentum.gui.font.CFontRender;
import me.linus.momentum.module.modules.gui.ClickGUI;
import me.linus.momentum.utils.capes.CapeUtils;
import me.linus.momentum.utils.managers.ConfigManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.linus.momentum.module.Module;
import me.linus.momentum.module.ModuleManager;
import me.linus.momentum.utils.settings.SettingsManager;
import me.linus.momentum.utils.command.CommandManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;

/**
 * created by linustouchtips24 on 08/28/2020
 **/

@Mod(
        name = "Momentum",
        modid = "momentum",
        version = "rel1.1"
)

public class Momentum{
    public static final String name = "Momentum";
    public static final String modid = "momentum";
    public static final String version = "1.1";
    public static final String fullversion = "release 1.1";
    public static final String appid = "734272177959993365";
    public static String prefix = "!";
    public static ConfigManager configManager;
    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static CFontRender fontRenderer;
    public static Momentum INSTANCE;
    public CapeUtils capeUtils;
    public static final EventBus EVENT_BUS = new EventManager();
    EventProcessor eventProcessor;

    public Momentum() {
        Momentum.INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Display.setTitle(name + " Utility Mod " + version);
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {

        fontRenderer = new CFontRender(new Font("Ariel", Font.PLAIN, 18), true,true);
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        eventProcessor = new EventProcessor();

        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new CommandManager());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        moduleManager.getModuleByName("ClickGUI").setKey(25);
        ClickGUI gui = (ClickGUI) moduleManager.getModuleByName("ClickGUI");
        Integer guiKey = moduleManager.getModuleByName("ClickGUI").getKey();
        for (Module m: moduleManager.getModules()) {
            if (Keyboard.isKeyDown(m.getKey())) {
                m.toggle();
            }
        }

        if (Keyboard.isKeyDown(guiKey)){
            gui.toggle();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        capeUtils = new CapeUtils();
    }

    public static Momentum getInstance() {
        return INSTANCE;
    }
}