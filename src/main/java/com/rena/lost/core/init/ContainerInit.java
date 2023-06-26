package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.container.NestTeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, LostInTime.MOD_ID);

    public static final RegistryObject<ContainerType<NestTeContainer>> NEST_CONTAINER = CONTAINER_TYPES.register("nest_container", () -> IForgeContainerType.create(NestTeContainer::new));
}
