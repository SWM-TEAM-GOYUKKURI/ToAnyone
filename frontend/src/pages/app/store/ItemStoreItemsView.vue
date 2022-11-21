<template>
  <div id="store-item-wrapper">
    <store-item v-for="(item, key) in ITEMS"
                :key="key"
                :storeItem="item"
                :storeItemType="itemType"
                :storeItemKey="key"
                :bought="item.default || boughtItems.includes(parseInt(key))"
                @itemClick="onStoreItemClick(item, itemType, key)">
    </store-item>
  </div>

  <v-slide-y-reverse-transition>
    <in-app-dialog v-if="showItemDialog"
                  class="item-dialog">
      <div class="item-dialog__content">
        <div class="item-dialog__content__preview">
          <store-item-preview :item="dialogData.item"
                              :itemType="dialogData.itemType"
                              :itemKey="dialogData.itemKey"
                              :fontPreviewExtended="true" />
        </div>
        <div class="item-dialog__content__name">{{ dialogData.item.name }}</div>
        <div class="item-dialog__content__price">{{ getItemTypeName(dialogData.itemType) }} &bull; {{ dialogData.item.price.toLocaleString() }} 포인트</div>
      </div>
      <div class="item-dialog__controls">
        <button class="button" @click="showItemDialog = false">닫기</button>
        <button class="button primary" @click="onPurchaseButtonClick">구매</button>
      </div>
    </in-app-dialog>
  </v-slide-y-reverse-transition>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { getStoreItems, ItemType, StoreItemBase, StoreItemList } from "@/util/item-loader";
import InAppDialog from "@/components/InAppDialog.vue";
import StoreItem from "@/components/app/store/StoreItem.vue";
import StoreItemPreview from "@/components/app/store/StoreItemPreview.vue";
import { DecorationCategory, DecorationItemList } from "@/interfaces/backend";
import { isSuccessful } from "@/util/backend";

interface StoreItemDialogData {
  item: StoreItemBase,
  itemType: ItemType,
  itemKey: string,
}

@Options({
  components: {
    StoreItem,
    StoreItemPreview,
    InAppDialog,
  },
})
export default class ItemStoreItemsView extends Vue {
  showItemDialog = false;
  dialogData: StoreItemDialogData | null = null;

  get itemType(): ItemType {
    return this.$route.meta.type as ItemType;
  }

  get ITEMS(): StoreItemList<StoreItemBase> {
    return getStoreItems(this.itemType);
  }

  get boughtItems(): number[] {
    return this.$store.state.user.userItems.map((x) => x.itemId);
  }

  getItemTypeName(itemType: ItemType): string {
    switch(itemType) {
      case "stickers": return "스티커";
      case "papers": return "편지지";
      case "fonts": return "글꼴";
    }
  }

  onStoreItemClick(item: StoreItemBase, itemType: ItemType, itemKey: string): void {
    this.showItemDialog = true;
    this.dialogData = { item, itemType, itemKey };
  }

  async onPurchaseButtonClick() {
    if(this.dialogData) {
      const response = await this.$api.purchaseStoreItem({
        category: DecorationCategory[this.dialogData.itemType],
        itemId: parseInt(this.dialogData.itemKey),
        price: this.dialogData.item.price,
      });

      if(isSuccessful(response.statusCode)) {
        alert("아이템 구매 성공!");
      } else {
        if(response.error && response.errorBody) {
          alert("아이템 구매 실패: " + response.errorBody.message);
        } else {
          alert("아이템 구매 실패: " + response.statusCode);
        }

        this.showItemDialog = false;
        return;
      }
    }

    // Update user info
    const userInfoResponse = await this.$api.getUserInfo();
    if(isSuccessful(userInfoResponse.statusCode) && userInfoResponse.data) { this.$store.commit("user/updateUserInfo", userInfoResponse.data); }

    // Get items of user
    const userItemsResponse = await this.$api.getUserItems();
    if(isSuccessful(userItemsResponse.statusCode) && userItemsResponse.data) { this.$store.commit("user/updateUserItems", userItemsResponse.data); }
  }
}
</script>

<style lang="scss" scoped>
#store-item-wrapper {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-evenly;
  padding: 1em;

  .store-item {
    width: 10em;
    height: 10em;
    margin: 0.75em;
    background-color: rgba($color-secondary, 0.75);

    @media (max-width: $viewport-small-max-width) {
      width: 8em;
      height: 8em;
      margin: 0.5em;
    }
  }
}

.item-dialog {
  &__content {
    text-align: center;

    & > * { margin: 0.5em 0; }

    &__preview {
      width: 80%;
      aspect-ratio: 1;
      margin-left: auto;
      margin-right: auto;

      .paper { padding: 1em; }
    }

    &__name {
      font-size: 1.2em;
      font-weight: bold;
      margin: 0.5em 0;
    }
  }

  &__controls {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    margin-top: 1em;

    & > * {
      margin: 0 0.5em;
    }
  }
}
</style>
