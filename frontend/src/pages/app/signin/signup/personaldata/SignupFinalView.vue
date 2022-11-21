<template>
  <div id="wrapper">
    <v-slide-y-reverse-transition leave-absolute>
      <div v-show="transitionTimeline.emoji" class="emoji">{{ emoji }}</div>
    </v-slide-y-reverse-transition>

    <v-slide-y-reverse-transition leave-absolute>
      <div v-show="transitionTimeline.message" class="message" v-html="message" />
    </v-slide-y-reverse-transition>

    <v-slide-y-reverse-transition leave-absolute>
      <router-link v-show="transitionTimeline.button" :to="{ name: 'main' }"><button class="button primary"><span>메인 페이지로!</span> <v-icon>mdi-chevron-right</v-icon></button></router-link>
    </v-slide-y-reverse-transition>
  </div>
</template>

<script lang="ts">
import { doAfter } from "@/util/transition-helper";
import { Vue } from "vue-class-component";

export default class SignupFinalView extends Vue {
  readonly PSYSCORE_THRESHOLD_HIGH = 15; // 높음
  readonly PSYSCORE_THRESHOLD_NORMAL = 30; // 보통
  readonly PSYSCORE_THRESHOLD_LOW = 40; // 낮음

  transitionTimeline = {
    emoji: false,
    message: false,
    button: false,
  };

  get psyScore(): number {
    if(this.$store.state.auth.userPsyScore) {
      return this.$store.state.auth.userPsyScore;
    } else {
      return -1;
    }
  }

  get emoji(): string {
    if(this.psyScore >= 0) {
      if(this.psyScore <= this.PSYSCORE_THRESHOLD_HIGH) return "💪";
      else if(this.psyScore <= this.PSYSCORE_THRESHOLD_NORMAL) return "😊";
      else if(this.psyScore <= this.PSYSCORE_THRESHOLD_LOW) return "😌";
    }

    return "✉️";
  }

  get message(): string {
    if(this.psyScore >= 0) {
      if(this.psyScore <= this.PSYSCORE_THRESHOLD_HIGH) return "건강한 마음을 가지고 계시네요! <br /> To. Anyone에서 여러 사용자들과 속마음을 나누는 편지를 주고받아보세요.";
      else if(this.psyScore <= this.PSYSCORE_THRESHOLD_NORMAL) return "To. Anyone에 어서 오세요! <br /> 지금부터 속마음을 나누는 편지를 주고받아보아요.";
      else if(this.psyScore <= this.PSYSCORE_THRESHOLD_LOW) return "최근에 힘든 일이 있었나요? <br /> To. Anyone에서 속마음을 털어놓아보아요.";
    }

    return "지금 바로 To. Anyone을 사용해보세요.";
  }

  async mounted() {
    await doAfter(100, () => { this.transitionTimeline.emoji = true; });
    await doAfter(200, () => { this.transitionTimeline.message = true; });
    await doAfter(200, () => { this.transitionTimeline.button = true; });
  }
}
</script>

<style lang="scss" scoped>
#wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  padding: 1em;

  .emoji { font-size: 10em; }

  .message {
    text-align: center;
    line-height: 1.5;
    font-size: 2em;
    margin: 1em 0;
  }
}
</style>
