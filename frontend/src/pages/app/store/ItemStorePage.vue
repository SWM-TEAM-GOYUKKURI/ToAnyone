<template>
  <div id="item-store-wrapper">
    <v-tabs class="item-store__tabs"
            grow>
      <v-tab link replace :to="{ name: 'item-store-stickers' }">스티커</v-tab>
      <v-tab link replace :to="{ name: 'item-store-papers' }">편지지</v-tab>
      <v-tab link replace :to="{ name: 'item-store-fonts' }">글꼴</v-tab>
    </v-tabs>

    <router-view />
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class ItemStorePage extends Vue {
  beforeMount(): void {
    if(this.$route.name === "item-store") {
      // Default subpage is stickers view
      this.$router.replace({ name: "item-store-stickers" });
    }
  }
}
</script>

<style lang="scss" scoped>
$tabs-bar-height: 64px;

#item-store-wrapper {
  padding-top: 1em;
  width: 80vw;
  margin: auto;

  @media (max-width: $viewport-small-max-width) {
    width: 90vw;
  }

  .item-store {
    &__tabs {
      --v-tabs-height: 64px;

      & * {
        font-size: 1.25em;
      }
    }
  }
}
</style>
