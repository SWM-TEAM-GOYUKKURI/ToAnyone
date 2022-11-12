<template>
  <div id="letter-write-wrapper">
    <div class="letter-write__decors side">
      <v-tabs v-model="decorItemType" grow>
        <v-tab value="stickers">스티커</v-tab>
        <v-tab value="papers">편지지</v-tab>
        <v-tab value="fonts">글꼴</v-tab>
      </v-tabs>

      <v-window v-model="decorItemType" style="padding: 0.5em">
        <v-window-item value="stickers">
          <span>BACKEND SHOULD BE IMPLEMENTED</span>
        </v-window-item>
      </v-window>
    </div>

    <letter-area id="letter-write-area"
                 v-model:textContent="letterTextContent"
                 :letterWriteMode="true"
                 :letterReplyMode="replyMode"
                 :receiverNickname="replyMode ? replyModeData.senderName : undefined"
                 :letterSendStatus="letterSendStatus"
                 @textareaInput="onTextareaInput" />

    <div class="letter-write__options side">
      <div v-if="!replyMode">
        <h2 style="margin: 0.25em 0.25em 0.75em 0.25em">편지 전송 옵션</h2>

        <div class="option-container">
          <span>나이대 매칭</span>
          <v-radio-group v-model="letterSendOptions.age" inline>
            <v-radio value="random" label="무작위" />
            <v-radio value="same" label="같은 나이대 위주로" />
          </v-radio-group>
        </div>

        <div class="option-container">
          <span>성별 매칭</span>
          <v-radio-group v-model="letterSendOptions.gender" inline>
            <v-radio value="random" label="무작위" />
            <v-radio value="same" label="같은 성별 위주로" />
          </v-radio-group>
        </div>

        <div class="option-container">
          <span>직업 매칭</span>
          <v-radio-group v-model="letterSendOptions.job" inline>
            <v-radio value="random" label="무작위" />
            <v-radio value="same" label="같은 직업 위주로" />
          </v-radio-group>
        </div>
      </div>

      <button class="letter-write__options__send-button button primary fill-width"
              :disabled="sendButtonDisabled"
              @click="onSendButtonClick">
        <v-icon>{{ sendButtonIcon.icon }}</v-icon> <span>{{ replyMode ? "답장" : "편지" }} {{ sendButtonIcon.suffix }}</span>
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import contenteditable from "vue-contenteditable";
import { RouteLocationNormalized } from "vue-router";
import LetterArea, { LetterSendStatus } from "@/components/app/letter/LetterArea.vue";
import { isSuccessful } from "@/util/backend";
import { LetterInboxItem } from "@/interfaces/backend";
import { ItemType } from "@/util/item-loader";

@Options({
  components: {
    contenteditable,
    LetterArea,
  },
})
export default class LetterWritePage extends Vue {
  letterTextContent = "";
  letterTextInputOccured = false;
  letterSendStatus = LetterSendStatus.NORMAL;

  replyMode = false;
  replyModeData: LetterInboxItem | null = null;

  decorItemType: ItemType = "stickers";

  letterSendOptions = {
    age: "random",
    gender: "random",
    job: "random",
  };

  get sendButtonIcon(): { icon: string, suffix: string } {
    switch(this.letterSendStatus) {
      case LetterSendStatus.NORMAL: return { icon: "mdi-send", suffix: "보내기" };
      case LetterSendStatus.SENDING: return { icon: "mdi-progress-upload", suffix: "보내는 중" };
      case LetterSendStatus.DONE: return { icon: "mdi-check", suffix: "보내기 완료!" };
      case LetterSendStatus.ERROR: return { icon: "mdi-alert-circle", suffix: "보내기 오류" };
    }
  }

  get sendButtonDisabled(): boolean {
    return this.letterSendStatus !== LetterSendStatus.NORMAL || !this.letterTextContent;
  }

  beforeCreate(): void {
    if(this.$route.params) {
      this.replyMode = (this.$route.params.replyMode === "true");

      if(this.replyMode) {
        this.replyModeData = JSON.parse(this.$route.params.replyModeData as string) as LetterInboxItem;
      }
    }

    if(this.$route.name === "letter-reply" && !(this.replyMode && this.replyModeData)) {
      // Route is letter reply but without valid reply data: redirect to main
      this.$router.replace({ name: "main" });
    }
  }

  mounted(): void {
    window.scrollTo({ top: 0, behavior: "smooth" });
    document.body.scrollTo({ top: 0, behavior: "smooth" });
  }

  beforeRouteLeave(to: RouteLocationNormalized, from: RouteLocationNormalized) {
    let answer = true;

    if(this.letterTextInputOccured && this.letterTextContent.length > 0) {
      answer = window.confirm("편지를 작성 중이에요. 정말로 나가실건가요?");
    }

    if(answer) {
      if(to.name === "letter-view") {
        if(from.name === "letter-reply" &&
          this.replyMode &&
          this.replyModeData) {
          to.params = {
            letterId: this.replyModeData.id.toString(),
          };
        }
      }

      return true;
    } else {
      return false;
    }
  }

  onTextareaInput() {
    this.letterTextInputOccured = true;
  }

  async onSendButtonClick() {
    if(this.letterSendStatus === LetterSendStatus.NORMAL && this.letterTextContent.length > 0) {
      this.letterSendStatus = LetterSendStatus.SENDING;

      if(!this.replyMode) {
        /* NORMAL WRITE MODE */
        const response = await this.$api.writeLetter({
          content: this.letterTextContent,
          decorations: [],
        });

        if(isSuccessful(response.statusCode)) {
          this.letterTextInputOccured = false;
          this.letterSendStatus = LetterSendStatus.DONE;

          alert("편지를 성공적으로 전송했어요.");
          this.$router.replace({ name: "letter-box" });
        } else {
          // TEMP ALERT
          alert(`편지 전송 중 API 오류: ${response.statusCode}`);
        }
      } else if(this.replyMode && this.replyModeData) {
        /* REPLY MODE */
        const response = await this.$api.writeReplyLetter(this.replyModeData.id, {
          content: this.letterTextContent,
          decorations: [],
        });

        if(isSuccessful(response.statusCode)) {
          this.letterTextInputOccured = false;
          this.letterSendStatus = LetterSendStatus.DONE;

          alert("답장을 성공적으로 전송했어요.");
          this.$router.replace({ name: "letter-view" });
        } else {
          // TEMP ALERT
          alert(`편지 답장 전송 중 API 오류: ${response.statusCode}`);
        }
      }
    }
  }

  onEditorKeyDown(event: KeyboardEvent): void {
    if(event.ctrlKey) {
      switch(event.key.toLowerCase()) {
        case "s":
          event.preventDefault();
          // CTRL+S : 임시 저장
          // TO BE IMPLEMENTED
          break;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
$decor-area-width: 20vw;
$letter-paper-area-width: 60vw;
$letter-paper-area-padding: 1em;
$contents-min-height: 80vh;
$contents-side-width: calc((85vw - var(--letter-area-width)) / 2);
$viewport-letter-write-small-width: 1400px;

#letter-write-wrapper {
  display: flex;
  flex-direction: row;
  align-items: flex-start;

  padding: 2rem 0 8rem 0;

  .side {
    position: sticky;
    top: calc(var(--app-navbar-height) + 2rem);
    overflow: auto;
    width: $contents-side-width;
    max-width: 350px;
    min-height: 40vh;
    max-height: calc(100vh - var(--app-navbar-height) - 2rem);
    padding: 1em;
    background-color: rgba($color-secondary, 0.5);
  }

  #decor {
    position: absolute;
    left: 0;
    width: $decor-area-width;
    min-height: $contents-min-height;
    background-color: rgba($color-secondary, 0.5);
    border-top-right-radius: 1.5em;
    border-bottom-right-radius: 1.5em;
    padding: 1rem;
    box-shadow: 0 1em 1.5em rgba(black, 0.33);
  }

  #letter-write-area {
    // width: $letter-paper-area-width;
    min-height: $contents-min-height;
    //margin-right: calc((100vw - $decor-area-width - $letter-paper-area-width) / 2.5);
    margin: 0 auto;
  }

  .letter-write {
    &__options {
      &__send-button {
        margin-top: 1em;
      }

      .option-container {
        margin: 0.5em;
      }
    }
  }
}
</style>
