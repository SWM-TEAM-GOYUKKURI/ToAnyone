<template>
  <div id="letter-write-wrapper">
    <div id="decor">
      꾸미기 아이템 영역<br>구현 중입니다.
    </div>

    <div id="letter-paper">
      <div class="letter-paper__content-area">
        <div><strong>From. {{ $store.state.auth.userBasicInfo.nickname }}</strong></div>
        <contenteditable v-model="letterTextContent"
                        class="letter-paper__content-area__text-area"
                        tag="div"
                        contenteditable="true"
                        @keydown.ctrl="onEditorKeyDown"></contenteditable>
        <div style="text-align: right"><strong>To. Anyone</strong></div>
      </div>

      <div class="letter-paper__send-button animation-button"
           @click="onSendButtonClick">
           <v-icon>mdi-send</v-icon>
           <span>편지 보내기</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import contenteditable from "vue-contenteditable";

@Options({
  components: {
    contenteditable,
  },
})
export default class LetterWritePage extends Vue {
  letterTextContent = "";

  onSendButtonClick(): void {
    // TO BE IMPLEMENTED
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

  & > * {
    box-shadow: 0 1em 1.5em rgba(black, 0.33);
  }

  #decor {
    position: absolute;
    left: 0;
    width: $decor-area-width;
    min-height: $contents-min-height;
    background-color: rgba($color-secondary, 0.5);
    border-top-right-radius: 4rem;
    border-bottom-right-radius: 4rem;
    padding: 1rem;
  }

  #letter-paper {
    position: relative;
    width: $letter-paper-area-width;
    min-height: $contents-min-height;
    margin-right: calc((100vw - $decor-area-width - $letter-paper-area-width) / 2.5);
    margin-left: auto;
    padding: 1em;
    font-size: 1.5em;
    border-radius: 1rem;
    background-color: #E9E9CC;

    .letter-paper {
      &__content-area {
        display: flex;
        flex-direction: column;
        width: 100%;
        min-height: calc($contents-min-height - ($letter-paper-area-padding * 2));
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
  }
}
</style>
