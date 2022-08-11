<template>
  <div>
    <sign-in-with-google />
    <sign-in-with-kakao />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import "@/plugins/signin/google/index";
import SignInWithGoogle from "@/components/signin/SignInWithGoogle.vue";
import SignInWithKakao from "@/components/signin/SignInWithKakao.vue";

@Options({
  components: {
    SignInWithGoogle,
    SignInWithKakao,
  },
})
export default class LoginPage extends Vue {

}
</script>
