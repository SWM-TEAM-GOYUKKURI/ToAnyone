<template>
  <div>
    <sign-in-with-google />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import SignInWithGoogle from "@/components/signin/SignInWithGoogle.vue";
import "@/plugins/signin/google/index";

@Options({
  components: {
    SignInWithGoogle,
  },
})
export default class LoginPage extends Vue {

}
</script>
