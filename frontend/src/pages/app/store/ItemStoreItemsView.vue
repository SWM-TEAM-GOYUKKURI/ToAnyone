<template>
  <div id="store-item-wrapper">
    <store-item v-for="(item, key) in ITEMS"
                :key="key"
                :storeItem="item"
                :storeItemType="itemType"
                :storeItemKey="key">
    </store-item>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { getStoreItems, ItemType, StoreItemBase, StoreItemList } from "@/util/item-loader";
import { getAssetPath } from "@/util/path-transform";
import StoreItem from "@/components/app/store/StoreItem.vue";

@Options({
  components: {
    StoreItem,
  },
})
export default class ItemStoreItemsView extends Vue {
  getAssetPath = getAssetPath;

  get itemType(): ItemType {
    return this.$route.meta.type as ItemType;
  }

  get ITEMS(): StoreItemList<StoreItemBase> {
    return getStoreItems(this.itemType);
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
</style>
