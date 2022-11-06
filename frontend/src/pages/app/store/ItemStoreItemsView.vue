<template>
  <div>ItemListView</div>
  <div>
    <div v-for="(item, key) in ITEMS"
         :key="key">
      <img :src="getAssetPath(`items/${itemType}/${key}.png`)" />
      <span>{{ item.name }}</span>
    </div>
  </div>
</template>

<script lang="ts">
import { getStoreItems, ItemType, StoreItemList } from "@/util/item-loader";
import { getAssetPath } from "@/util/path-transform";
import { Vue } from "vue-class-component";

export default class ItemStoreItemsView extends Vue {
  getAssetPath = getAssetPath;

  get itemType(): ItemType {
    return this.$route.meta.type as ItemType;
  }

  get ITEMS(): StoreItemList {
    return getStoreItems(this.itemType);
  }
}
</script>
