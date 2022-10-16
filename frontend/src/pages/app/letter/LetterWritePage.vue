<template>
  <div id="letter-write-wrapper">
    <div id="decor">
      꾸미기 아이템 영역<br>구현 중입니다.
    </div>

    <!-- Normal write -->
    <letter-area v-if="!replyMode"
                 id="letter-write-area"
                 v-model:textContent="letterTextContent"
                 :letterWriteMode="true"
                 :letterSendInProgress="letterSendInProgress"
                 @textareaInput="onTextareaInput"
                 @sendButtonClick="onSendButtonClick" />

    <!-- Reply -->
    <letter-area v-else
                 id="letter-write-area"
                 v-model:textContent="letterTextContent"
                 :letterWriteMode="true"
                 :letterReplyMode="true"
                 :receiverNickname="replyModeData.senderName"
                 :letterSendInProgress="letterSendInProgress"
                 @textareaInput="onTextareaInput"
                 @sendButtonClick="onSendButtonClick" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import contenteditable from "vue-contenteditable";
import { RouteLocationNormalized } from "vue-router";
import LetterArea from "@/components/app/letter/LetterArea.vue";
import { bePOST } from "@/util/backend";
import { LetterInboxItem, LetterWriteRequest } from "@/interfaces/backend";

@Options({
  components: {
    contenteditable,
    LetterArea,
  },
})
export default class LetterWritePage extends Vue {
  letterTextContent = "";
  letterTextInputOccured = false;
  letterSendInProgress = false;

  replyMode = false;
  replyModeData: LetterInboxItem | null = null;

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

  beforeRouteLeave(to: RouteLocationNormalized, from: RouteLocationNormalized) {
    if(this.letterTextInputOccured) {
      const answer = window.confirm("편지를 작성 중이에요. 정말로 나가실건가요?");
      if(answer) {
        if(to.name === "letter-view" &&
          from.name === "letter-reply" &&
          this.replyMode &&
          this.replyModeData) {
          to.params = {
            letterId: this.replyModeData.id.toString(),
          };
        }

        return true;
      } else {
        return false;
      }
    }
  }

  onTextareaInput() {
    this.letterTextInputOccured = true;
  }

  async onSendButtonClick() {
    if(!this.letterSendInProgress && this.letterTextContent.length > 0) {
      this.letterSendInProgress = true;

      if(!this.replyMode) {
        /* NORMAL WRITE MODE */

        const responseData: LetterWriteRequest = {
          content: this.letterTextContent,
          decorations: [],
        };

        const response = await bePOST("/letter", responseData, {
          credentials: this.$store.state.auth.token!,
        });

        if(response.statusCode === 201) {
          // HTTP 201 Created: Letter sent successfully
          this.letterTextContent = "";
        } else {
          // Error handling
        }
      } else {
        /* REPLY MODE */

        const response = await bePOST(`/letter/inbox/${this.replyModeData?.id}`, {
          content: this.letterTextContent,
          decorations: [],
        }, {
          credentials: this.$store.state.auth.token!,
        });

        if(response.statusCode === 201) {
          this.letterTextContent = "";
        } else {
          // todo
        }
      }

      this.letterSendInProgress = false;
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
#letter-write-wrapper {
  $decor-area-width: 20vw;
  $letter-paper-area-width: 60vw;
  $letter-paper-area-padding: 1em;
  $contents-min-height: 80vh;

  padding: 2rem 0 8rem 0;

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
    width: $letter-paper-area-width;
    min-height: $contents-min-height;
    margin-right: calc((100vw - $decor-area-width - $letter-paper-area-width) / 2.5);
    margin-left: auto;
  }
}
</style>
