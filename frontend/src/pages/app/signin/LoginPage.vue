<template>
  <div>
    <login-with-google />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LoginWithGoogle from "@/components/signin/LoginWithGoogle.vue";
import "@/plugins/signin/google/index";

@Options({
  components: {
    LoginWithGoogle,
  },
})
export default class LoginPage extends Vue {

}
</script>
