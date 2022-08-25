<template>
  <div id="letter-view-wrapper">
    <letter-area class="letter-view-area"
                 senderNickname="테스트 발신자 (테스트용 대체 콘텐츠)"
                 :receiverNickname="$store.state.auth.userBasicInfo.nickname"
                 :textContent="testTextContent" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterArea from "@/components/app/letter/LetterArea.vue";

@Options({
  components: {
    LetterArea,
  },
})
export default class LetterViewPage extends Vue {
  testTextContent = "세상에서 가장 중요한 때는\n지금 이 순간이고\n가장 중요한 사람은\n지금 함께 있는 사람이고\n가장 중요한 일은\n내 곁에 있는 사람을 위해\n좋은 일을 하는 것이다\n\n- 톨스토이 세 가지 질문 中\n\n\n자기 자신이 해낸 것을 즐기는,\n그리고 자기자신이 하고 있는것을\n즐기는 사람은 행복한사람이다.\n\n- 괴태";

  beforeMount(): void {
    if(!(this.$route.params && this.$route.params.letterId)) {
      this.$router.replace({ name: "main" });
    }
  }
}
</script>

<style lang="scss" scoped>
#letter-view-wrapper {
  padding: 3rem 0;

  .letter-view-area {
    width: 80vw;
    min-height: 70vh;
    margin: auto;
  }
}
</style>
