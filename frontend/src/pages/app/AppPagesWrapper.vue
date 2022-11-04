<template>
  <app-navbar />

  <div id="app-wrapper">
    <router-view v-slot="{ Component }">
      <v-fade-transition leave-absolute>
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
  beforeMount(): void {
    if(!this.$api.isAvailable() && this.$store.state.auth.token) {
      this.$api.registerToken(this.$store.state.auth.token);
    }
  }
}
</script>

<style lang="scss" scoped>
#app-wrapper {
  margin-top: $app-navbar-height;
}
</style>
