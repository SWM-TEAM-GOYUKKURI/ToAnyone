<template>
  <div class="store-item button"
       @click="$emit('click')">
    <div class="store-item__preview">
      <img v-if="storeItemType === 'stickers'" :src="getAssetPath(`items/stickers/${storeItemKey}.png`)" />
      <div v-else-if="storeItemType === 'papers'" :style="{ backgroundColor: storeItem.color }" />
      <div v-else-if="storeItemType === 'fonts'">준비 중</div>
    </div>
    <div class="store-item__name">{{ storeItem.name }}</div>
  </div>
</template>

<script lang="ts">
import { ItemType, StoreItemBase } from "@/util/item-loader";
import { getAssetPath } from "@/util/path-transform";
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class StoreItem extends Vue {
  getAssetPath = getAssetPath;

  @Prop({ type: Object, required: true }) storeItem!: StoreItemBase;
  @Prop({ type: String, required: true }) storeItemType!: ItemType;
  @Prop({ type: String, required: true }) storeItemKey!: string;
}
</script>

<style lang="scss" scoped>
.store-item {
  cursor: pointer;
  flex-direction: column;

  & > * { margin: 0 !important; }

  &__preview {
    width: 80%;
    aspect-ratio: 1;

    & > * {
      width: 100%;
      aspect-ratio: 1;
      border-radius: 0.5em;
    }
  }

  &__name {
    font-size: 1.2em;
    padding-top: 0.5em;
  }
}
</style>
