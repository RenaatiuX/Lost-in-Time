package com.rena.lost;

import com.rena.lost.common.blocks.LostWoodTypes;
import com.rena.lost.common.entities.*;
import com.rena.lost.common.entities.misc.CustomEggEntity;
import com.rena.lost.common.entities.misc.MudBallEntity;
import com.rena.lost.core.init.*;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


@Mod(LostInTime.MOD_ID)
public class LostInTime {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "lost";
    public static final String ARMOR_DIR = MOD_ID + ":textures/armor/";
    public static boolean ENABLE_OVERWORLD_TREES = true;

    public LostInTime() {
        // Register the setup method for modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::registerEntityAttributes);
        GeckoLib.initialize();

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        EntityInit.ENTITY_TYPES.register(modEventBus);
        TileEntityInit.TILE_ENTITIES.register(modEventBus);
        ContainerInit.CONTAINER_TYPES.register(modEventBus);
        EffectInit.EFFECTS.register(modEventBus);
        FluidInit.FLUIDS.register(modEventBus);
        ParticleInit.PARTICLES.register(modEventBus);
        BiomeInit.BIOMES.register(modEventBus);
        FeatureInit.FEATURE.register(modEventBus);
        SurfaceBuilderInit.SURFACE_BUILDERS.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerDispenseBehavior(ItemInit.MUD_BALL.get(), new ProjectileDispenseBehavior() {
                protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                    return Util.make(new MudBallEntity(worldIn, position.getX(), position.getY(), position.getZ()), (mudBall) -> {
                        mudBall.setItem(stackIn);
                    });
                }
            });

            ProjectileDispenseBehavior dispenseBehavior = new ProjectileDispenseBehavior() {
                @Override
                protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                    return Util.make(new CustomEggEntity(worldIn, position.getX(), position.getY(), position.getZ()), (egg) -> {
                        egg.setItem(stackIn);
                    });
                }
            };
            DispenserBlock.registerDispenseBehavior(ItemInit.PELECANIMIMUS_EGG.get(), dispenseBehavior);
            DispenserBlock.registerDispenseBehavior(ItemInit.MIRARCE_EGG.get(), dispenseBehavior);

            IDispenseItemBehavior idispenseitembehavior1 = new DefaultDispenseItemBehavior() {
                private final DefaultDispenseItemBehavior defaultBehaviour = new DefaultDispenseItemBehavior();
                public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                    BucketItem bucketitem = (BucketItem)stack.getItem();
                    BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                    World world = source.getWorld();
                    if (bucketitem.tryPlaceContainedLiquid((PlayerEntity)null, world, blockpos, (BlockRayTraceResult)null)) {
                        bucketitem.onLiquidPlaced(world, stack, blockpos);
                        return new ItemStack(Items.BUCKET);
                    } else {
                        return this.defaultBehaviour.dispense(source, stack);
                    }
                }
            };
            DispenserBlock.registerDispenseBehavior(ItemInit.TEPEXICHTHYS_BUCKET.get(), idispenseitembehavior1);
            DispenserBlock.registerDispenseBehavior(ItemInit.HYPSOCORMUS_BUCKET.get(), idispenseitembehavior1);
            DispenserBlock.registerDispenseBehavior(ItemInit.DECAYED_HYPSOCORMUS_BUCKET.get(), idispenseitembehavior1);

            /*AxeItem.BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.BLOCK_STRIPPING_MAP)
                    .put(BlockInit.ARAUCARIOXYLON_LOG.get(), BlockInit.STRIPPED_ARAUCARIOXYLON_LOG.get()).build();*/

            WoodType.register(LostWoodTypes.ARAUCARIOXYLON);
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientRegistry.bindTileEntityRenderer(TileEntityInit.SIGN_TE.get(), SignTileEntityRenderer::new);
            Atlases.addWoodType(LostWoodTypes.ARAUCARIOXYLON);
        });
    }

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.SAHONACHELYS.get(), Sahonachelys.createAttributes().create());
        event.put(EntityInit.DAKOSAURUS.get(), Dakosaurus.createAttributes().create());
        event.put(EntityInit.DIPLOMOCERAS.get(), Diplomoceras.createAttributes().create());
        event.put(EntityInit.HYPSOCORMUS.get(), Hypsocormus.createAttributes().create());
        event.put(EntityInit.TEPEXICHTHYS.get(), Tepexichthys.createAttributes().create());
        event.put(EntityInit.PELECANIMIMUS.get(), Pelecanimimus.createAttributes().create());
        event.put(EntityInit.MIRARCE.get(), Mirarce.createAttributes().create());
        event.put(EntityInit.ENDER_SNARLER.get(), EnderSnarler.createAttributes().create());
        event.put(EntityInit.DECAYED_HYPSOCORMUS.get(), DecayedHypsocormus.createAttributes().create());
    }
}
