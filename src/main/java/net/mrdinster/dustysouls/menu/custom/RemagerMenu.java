package net.mrdinster.dustysouls.menu.custom;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.mrdinster.dustysouls.block.ModBlocks;
import net.mrdinster.dustysouls.menu.ModMenuTypes;
import net.mrdinster.dustysouls.item.ModItems;

public class RemagerMenu extends AbstractContainerMenu {

    private final Container inputContainer = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            super.setChanged();
            RemagerMenu.this.slotsChanged(this);
        }
    };

    private final ResultContainer resultContainer = new ResultContainer();

    public RemagerMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ModMenuTypes.REMAGER_MENU_TYPE);
    }

    public RemagerMenu(int containerId, Inventory playerInventory, MenuType<?> type) {
        super(type, containerId);
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.addSlot(new Slot(this.inputContainer, 0, 30, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {

                return stack.is(Items.CLAY_BALL) ||
                        stack.is(Items.SPIDER_EYE) ||
                        stack.is(Items.PITCHER_POD) ||
                        stack.is(Blocks.TUFF.asItem()) ||
                        stack.is(Items.STRING) ||
                        stack.is(Items.SLIME_BALL) ||
                        stack.is((ModBlocks.DODO_EGG.asItem()));
            }
        });

        this.addSlot(new Slot(this.resultContainer, 0, 77, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                ItemStack inputStack = RemagerMenu.this.inputContainer.getItem(0);
                if (!inputStack.isEmpty()) {
                    int amountToConsume = getRequiredInputAmount(inputStack);
                    inputStack.shrink(amountToConsume);
                    RemagerMenu.this.inputContainer.setItem(0, inputStack);
                }
                super.onTake(player, stack);
            }
        });
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == this.inputContainer) {
            ItemStack input = this.inputContainer.getItem(0);

            if (!input.isEmpty()) {
                if (input.is(Items.CLAY_BALL) && input.getCount() >= 8) {
                    this.resultContainer.setItem(0, new ItemStack(ModItems.ANCIENT_STICK, 1));
                    return;
                }
                if (input.is(Items.SPIDER_EYE) && input.getCount() >= 4) {
                    this.resultContainer.setItem(0, new ItemStack(ModItems.NANGU, 1));
                    return;
                }
                if (input.is(Items.PITCHER_POD) && input.getCount() >= 16) {
                    this.resultContainer.setItem(0, new ItemStack(ModItems.TELDEN_UPGRADE, 1));
                    return;
                }
                if (input.is(Blocks.TUFF.asItem())  && input.getCount() >= 6) {
                    this.resultContainer.setItem(0, new ItemStack(ModItems.TEMPUS_NUGGET, 1));
                    return;
                }
                if (input.is(Items.SLIME_BALL)  && input.getCount() >= 8) {
                    this.resultContainer.setItem(0, new ItemStack(ModItems.SLIMUS_BALL, 1));
                    return;
                }
                if (input.is(Items.STRING) && input.getCount() >= 32) {
                    this.resultContainer.setItem(0, new ItemStack(ModBlocks.TREKKER_EGG, 1));
                    return;
                }
                if (input.is(ModBlocks.DODO_EGG.asItem()) && input.getCount() >= 32) {
                    this.resultContainer.setItem(0, new ItemStack(ModBlocks.FLEAGLE_EGG, 1));
                    return;
                }
            }

            this.resultContainer.setItem(0, ItemStack.EMPTY);
        }
    }


    private int getRequiredInputAmount(ItemStack inputStack) {
        if (inputStack.is(Items.CLAY_BALL)) return 8;
        if (inputStack.is(Items.SPIDER_EYE)) return 4;
        if (inputStack.is(Items.PITCHER_POD)) return 16;
        if (inputStack.is(Blocks.TUFF.asItem())) return 6;
        if (inputStack.is(Items.SLIME_BALL)) return 8;
        if (inputStack.is(Items.STRING)) return 32;
        if (inputStack.is(ModBlocks.DODO_EGG.asItem())) return 32;
        return 1;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide()) {
            this.clearContainer(player, this.inputContainer);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
