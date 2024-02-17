package com.cgd.mods;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(MoreFood.MODID)
public class MoreFood {
	public static final String MODID = "morefood";
	public final Logger LOGGER = LogUtils.getLogger();
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato"
			, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(2f).build())));
	public static final RegistryObject<Item> CABAGE = ITEMS.register("cabage"
			, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1f).build())));
	public static final RegistryObject<Item> CHEDAR = ITEMS.register("chedar"
			, () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(2f).build())));
	public static final RegistryObject<CreativeModeTab> MOREFOOD_TAB = CREATIVE_MODE_TABS.register("morefood_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> TOMATO.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(TOMATO.get());
                output.accept(CABAGE.get());
                output.accept(CHEDAR.get());
            }).build());
	public MoreFood() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		eventBus.addListener(this::commonSetup);
		ITEMS.register(eventBus);
		CREATIVE_MODE_TABS.register(eventBus);
		MinecraftForge.EVENT_BUS.register(this);
		eventBus.addListener(this::addCreative);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
	
	private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(TOMATO.get());
            event.accept(CABAGE.get());
            event.accept(CHEDAR.get());
        }
    }
	
	private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("SETUP STARTED");
        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
}
