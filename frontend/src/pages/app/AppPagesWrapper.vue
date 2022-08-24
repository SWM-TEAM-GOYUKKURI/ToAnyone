<template>
  <app-navbar />

  <div id="app-wrapper">
    <router-view v-slot="{ Component }">
      <v-fade-transition>
        <component :is="Component" />
      </v-fade-transition>
    </router-view>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import AppNavbar from "@/components/app/global/AppNavbar.vue";

@Options({
  components: {
    AppNavbar,
  },
})
export default class AppPagesWrapper extends Vue {

}
</script>

<style lang="scss" scoped>
#app-wrapper {
  margin-top: $app-navbar-height;
}
</style>
