<template>
  <div class="letter-area">
    <div class="letter-area__content-area">
      <div>From. <strong>{{ realSenderNickname }}</strong></div>
      <div class="letter-area__content-area__text-area-wrapper">
        <contenteditable v-model="letterTextContent"
                         ref="letterTextElement"
                         class="text-area"
                         tag="div"
                         :contenteditable="letterWriteMode"
                         :no-html="true"
                         @input="onEditorInput"
                         @keydown.ctrl="onEditorKeyDown" />

        <div class="line-wrapper">
          <div ref="letterTextElementFirstLine" class="line" /> <!-- First line is mandatory for height calculation -->
          <div v-for="x in letterTextElementRequiredLines"
               :key="x"
               class="line" />
        </div>
      </div>
      <div style="text-align: right">To. <strong>{{ receiverNickname }}</strong></div>
    </div>

    <button v-if="letterWriteMode"
            class="letter-area__send-button button round primary"
            :class="{ 'disabled': buttonDisabled }"
            @click="() => !buttonDisabled ? $emit('sendButtonClick') : null">
      <v-fade-transition leave-absolute>
        <span v-if="letterSendStatus === LetterSendStatus.NORMAL"><v-icon>mdi-send</v-icon> {{ letterReplyMode ? "답장" : "편지" }} 보내기</span>

        <v-progress-circular v-else-if="letterSendStatus === LetterSendStatus.SENDING" indeterminate size="large" />
        <v-icon v-else-if="letterSendStatus === LetterSendStatus.DONE">mdi-check</v-icon>
        <v-icon v-else-if="letterSendStatus === LetterSendStatus.ERROR">mdi-alert-circle</v-icon>
      </v-fade-transition>
    </button>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop, PropSync, Watch } from "vue-property-decorator";
import contenteditable from "vue-contenteditable";

export enum LetterSendStatus {
  NORMAL,
  SENDING,
  DONE,
  ERROR,
}

@Options({
  components: {
    contenteditable,
  },
})
export default class LetterArea extends Vue {
  LetterSendStatus = LetterSendStatus;

  @Prop({ type: Boolean, default: false }) letterWriteMode!: boolean;
  @Prop({ type: Boolean, default: false }) letterReplyMode!: boolean;
  @Prop({ default: LetterSendStatus.NORMAL }) letterSendStatus!: LetterSendStatus;
  @Prop({ type: String, default: "" }) senderNickname!: string;
  @Prop({ type: String, default: "Anyone" }) receiverNickname!: string;
  @PropSync("textContent", { type: String, default: "" }) letterTextContent!: string;

  letterTextElementHeight = 0;
  letterTextElementLineHeight = 0;
  letterTextElementRequiredLines = 0;

  get realSenderNickname(): string {
    return this.letterWriteMode ? this.$store.state.auth.userBasicInfo!.nickname : this.senderNickname;
  }

  get buttonDisabled(): boolean {
    return this.letterSendStatus !== LetterSendStatus.NORMAL || !this.letterTextContent;
  }

  get letterTextElement(): HTMLDivElement {
    return (this.$refs.letterTextElement as Vue).$el as HTMLDivElement;
  }

  mounted(): void {
    this.letterTextElementHeight = this.letterTextElement.offsetHeight;
    this.letterTextElementLineHeight = (this.$refs.letterTextElementFirstLine as HTMLDivElement).offsetHeight;
  }

  onEditorKeyDown(event: KeyboardEvent): void {
    if(event.ctrlKey) {
      switch(event.key.toLowerCase()) {
        case "s":
          // CTRL+S : 임시 저장
          event.preventDefault();
          this.$emit("requestTempSave");
          break;
      }
    }
  }

  onEditorInput(): void {
    this.letterTextElementHeight = this.letterTextElement.offsetHeight;

    this.$emit("textareaInput");
  }

  @Watch("letterTextElementHeight", { immediate: true })
  onLetterTextElementHeightChange(): void {
    const required = Math.floor(this.letterTextElementHeight / this.letterTextElementLineHeight);
    if(required) {
      this.letterTextElementRequiredLines = required;
    }
  }
}
</script>

<style lang="scss" scoped>
.letter-area {
  display: flex;
  position: relative;
  padding: 1em;
  font-size: 1.5em;
  background-color: #FFF7E8;
  box-shadow: 0 1em 1.5em rgba(black, 0.33);

  &__content-area {
    display: flex;
    flex-grow: 1;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    color: $color-dark;
    padding-bottom: 2em;

    &__text-area-wrapper {
      position: relative;
      overflow: hidden;
      display: flex;
      flex-grow: 1;
      width: 100%;
      margin: 0.5em 0 1em 0;
      padding: 0 0.5em;
      line-height: 2;

      .text-area {
        flex-grow: 1;

        &:focus, &:focus-visible, &:focus-within {
          border: none;
          outline: none;
        }
      }

      .line-wrapper {
        position: absolute;
        width: calc(100% - (0.5em * 2)); // minus parent padding

        &, & * { pointer-events: none; }

        .line {
          height: 2em; // font-size(1em) * line-height(2)
          border-bottom: solid rgba($color-dark, 0.5) 2px;
        }
      }
    }
  }

  &__send-button {
    position: absolute;
    right: -50px;
    bottom: -50px;
    min-width: 270px;
    min-height: 100px;
    box-shadow: 0 0.33em 0.5em rgba(black, 0.25);
    background: $color-primary !important;
    z-index: 1;
  }
}
</style>
