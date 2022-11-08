<template>
  <img v-if="itemType === 'stickers'" class="sticker" :src="getAssetPath(`items/stickers/${itemKey}.png`)" />
  <div v-else-if="itemType === 'papers'" class="paper" :style="{ backgroundColor: item.color }">To.<br/>Anyone</div>
  <div v-else-if="itemType === 'fonts'" class="font">준비 중</div>
</template>

<script lang="ts">
import { ItemType, StoreItemBase } from "@/util/item-loader";
import { getAssetPath } from "@/util/path-transform";
import { Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";

export default class StoreItemPreview extends Vue {
  getAssetPath = getAssetPath;

  @Prop({ type: Object, required: true }) item!: StoreItemBase;
  @Prop({ type: String, required: true }) itemType!: ItemType;
  @Prop({ type: String, required: true }) itemKey!: string;
}
</script>

<style lang="scss" scoped>
* {
  aspect-ratio: 1;
  border-radius: 0.5em;

  &.paper {
    text-align: left;
    font-size: 1.1em;
    padding: 0.5em;
    color: $color-dark;
  }
}
</style>
