<template>
  <div class="letter-area">
    <div class="letter-area__content-area">
      <div><strong>From. {{ realSenderNickname }}</strong></div>
      <contenteditable v-model="letterTextContent"
                       class="letter-area__content-area__text-area"
                       tag="div"
                       :contenteditable="letterWriteMode"
                       :no-html="true"
                       @keydown.ctrl="onEditorKeyDown"></contenteditable>
      <div style="text-align: right"><strong>To. {{ realReceiverNickname }}</strong></div>
    </div>

    <div v-if="letterWriteMode"
         class="letter-area__send-button animation-button"
         @click="$emit('sendButtonClick')">
          <v-icon>mdi-send</v-icon>
          <span>편지 보내기</span>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop, PropSync } from "vue-property-decorator";
import contenteditable from "vue-contenteditable";

@Options({
  components: {
    contenteditable,
  },
})
export default class LetterArea extends Vue {
  @Prop({ type: Boolean, default: false }) letterWriteMode!: boolean;
  @Prop({ type: String, default: "SENDER" }) senderNickname!: string;
  @Prop({ type: String, default: "RECEIVER" }) receiverNickname!: string;
  @PropSync("textContent", { type: String, default: "" }) letterTextContent!: string;

  get realReceiverNickname(): string {
    return this.letterWriteMode ? "Anyone" : this.receiverNickname;
  }

  get realSenderNickname(): string {
    return this.letterWriteMode ? this.$store.state.auth.userBasicInfo!.nickname : this.senderNickname;
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
}
</script>

<style lang="scss" scoped>
.letter-area {
  display: flex;
  position: relative;
  padding: 1em;
  font-size: 1.5em;
  border-radius: 1em;
  background-color: #E9E9CC;
  box-shadow: 0 1em 1.5em rgba(black, 0.33);

  &__content-area {
    display: flex;
    flex-grow: 1;
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    font-family: serif;
    color: #310;
    padding-bottom: 2em;

    &__text-area {
      flex-grow: 1;
      width: 100%;
      margin: 0.5em 0;
      line-height: 2;

      &:focus, &:focus-visible, &:focus-within {
        border: none;
        outline: none;
      }
    }
  }

  &__send-button {
    cursor: pointer;
    position: absolute;
    right: -1.5em;
    bottom: -1.5em;
    padding: 1em;
    display: flex;
    align-items: center;
    background-color: $color-primary;
    border-radius: 999em;
    box-shadow: 0 0.33em 0.5em rgba(black, 0.25);
    z-index: 1;

    & > * {
      margin: 0 0.25em;
    }
  }
}
</style>
