<template>
  <div class="store-item button"
       :class="{ bought }"
       @click="() => { if(!bought) $emit('itemClick') }"
       @mouseenter="mouseEnter = true"
       @mouseleave="mouseEnter = false">
    <div class="store-item__preview">
      <store-item-preview :item="storeItem"
                          :itemType="storeItemType"
                          :itemKey="storeItemKey" />
    </div>
    <div class="store-item__name">{{ bought ? (mouseEnter ? "구입 완료" : storeItem.name) : (mouseEnter ? `${storeItem.price.toLocaleString()} P` : storeItem.name) }}</div>
  </div>
</template>

<script lang="ts">
import { ItemType, StoreItemBase } from "@/util/item-loader";
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import StoreItemPreview from "./StoreItemPreview.vue";

@Options({
  components: {
    StoreItemPreview,
  },
})
export default class StoreItem extends Vue {
  @Prop({ type: Object, required: true }) storeItem!: StoreItemBase;
  @Prop({ type: String, required: true }) storeItemType!: ItemType;
  @Prop({ type: String, required: true }) storeItemKey!: string;
  @Prop({ type: Boolean, default: false }) bought!: boolean;

  mouseEnter = false;
}
</script>

<style lang="scss" scoped>
.store-item {
  cursor: pointer;
  flex-direction: column;

  & > * { margin: 0 !important; }

  &.bought {
    cursor: initial;

    & > * {
      opacity: 0.33;
    }
  }

  &__preview {
    width: 80%;
    aspect-ratio: 1;

    & > * {
      width: 100%;
      aspect-ratio: 1;
    }
  }

  &__name {
    font-size: 1.2em;
    padding-top: 0.5em;
  }
}
</style>
