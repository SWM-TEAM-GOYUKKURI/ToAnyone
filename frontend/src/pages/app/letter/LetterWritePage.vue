<template>
  <div id="letter-write-wrapper">
    <div class="letter-write__decors side"
         :class="{ 'vp-small-appear': vpSmallShowDecors }">
      <v-fade-transition>
        <div v-show="!vpSmallShowOptions"
            class="vp-small-handle"
            title="데코레이션 아이템"
            @click="vpSmallShowDecors = !vpSmallShowDecors">
          <span>데코</span> <v-icon>{{ vpSmallShowDecors ? "mdi-chevron-down" : "mdi-chevron-up" }}</v-icon>
        </div>
      </v-fade-transition>

      <div style="height: -webkit-fill-available" >
        <v-tabs v-model="decorItemType" grow>
          <v-tab value="stickers">스티커</v-tab>
          <v-tab value="papers">편지지</v-tab>
          <v-tab value="fonts">글꼴</v-tab>
        </v-tabs>

        <v-window v-model="decorItemType"
                  class="letter-write__decors__item-tab-wrapper">
          <v-window-item v-for="category in ['stickers', 'papers', 'fonts']"
                         :key="category"
                         :value="category">
            <div class="letter-write__decors__item-container">
              <span v-if="category === 'stickers'" class="vp-small-hide_0" style="font-size: 0.8em; margin: 0.25em 0 0.5em 0;">※ 드래그&amp;드롭으로 스티커를 편지에 붙여보세요.</span>
              <span v-else class="vp-small-hide_0" style="font-size: 0.8em; margin: 0.25em 0 0.5em 0;">※ 항목을 클릭/터치하여 편지에 적용해보세요.</span>

              <button v-for="(item, key) in getUserItems(category)"
                      :key="key"
                      class="item">
                <store-item-preview :item="item"
                                    :itemKey="key"
                                    :itemType="category"
                                    :nameAsPreviewText="true"
                                    :data-key="key"
                                    @click="onDecorItemClick(category, key)"
                                    @dragstart.stop="(event) => { if(category === 'stickers') onItemDragStart(event) }" />
              </button>
            </div>
          </v-window-item>
        </v-window>
      </div>
    </div>

    <letter-area ref="letter-area"
                 id="letter-write-area"
                 v-model:textContent="letterTextContent"
                 :letterWriteMode="true"
                 :letterReplyMode="replyMode"
                 :receiverNickname="replyMode ? replyModeData.senderName : undefined"
                 :letterSendStatus="letterSendStatus"
                 :decorations="letterDecorations"
                 @textareaInput="onTextareaInput"
                 @stickerClick="onLetterStickerClick"
                 @dragover.prevent.stop="onItemDragOver"
                 @drop.prevent.stop="onItemDrop" />

    <v-fade-transition>
      <div v-show="!vpSmallShowDecors"
          class="letter-write__options side"
          title="편지 전송 및 옵션"
          :class="{ 'vp-small-appear': vpSmallShowOptions }">
          <div class="vp-small-handle" @click="vpSmallShowOptions = !vpSmallShowOptions">
            <span>전송/옵션</span> <v-icon>{{ vpSmallShowOptions ? "mdi-chevron-down" : "mdi-chevron-up" }}</v-icon>
          </div>

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
    </v-fade-transition>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import contenteditable from "vue-contenteditable";
import { RouteLocationNormalized } from "vue-router";
import LetterArea, { LetterSendStatus } from "@/components/app/letter/LetterArea.vue";
import StoreItemPreview from "@/components/app/store/StoreItemPreview.vue";
import { isSuccessful } from "@/util/backend";
import { DecorationCategory, LetterInboxItem } from "@/interfaces/backend";
import { getDefaultItems, getStoreItem, ItemType, StoreItemBase, StoreItemList } from "@/util/item-loader";

@Options({
  components: {
    contenteditable,
    LetterArea,
    StoreItemPreview,
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

  letterDecorations: { stickers: Record<string, unknown>[], paperKey?: string, fontKey?: string } = {
    stickers: [],
  };

  vpSmallShowDecors = false;
  vpSmallShowOptions = false;

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

  getUserItems(category: ItemType): StoreItemList<StoreItemBase> {
    const userItems = this.$store.state.user.userItems.filter((x) => x.category === DecorationCategory[category]);
    const userItemObj: StoreItemList<StoreItemBase> = {};

    for(const item of userItems) {
      userItemObj[item.itemId.toString()] = getStoreItem(category, item.itemId.toString());
    }

    return {
      ...getDefaultItems(category),
      ...userItemObj,
    };
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
      if(this.letterTextContent.length < 30) {
        alert("편지 내용을 30자 이상 작성해주세요.");
        return;
      }

      this.letterSendStatus = LetterSendStatus.SENDING;

      if(!this.replyMode) {
        /* NORMAL WRITE MODE */
        const response = await this.$api.writeLetter({
          content: this.filterContent(this.letterTextContent),
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
          content: this.filterContent(this.letterTextContent),
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

  onDecorItemClick(category: ItemType, itemKey: string) {
    if(category === "papers") {
      this.letterDecorations.paperKey = itemKey;
    } else if(category === "fonts") {
      this.letterDecorations.fontKey = itemKey;
    }
  }

  onItemDragStart(event: DragEvent): void {
    event.dataTransfer?.setData("text/plain", `${(event.target as HTMLElement).dataset.key},${event.offsetX},${event.offsetY}`);
  }

  onItemDragOver(event: DragEvent): void {
    // No action
  }

  onItemDrop(event: DragEvent): void {
    const letterAreaElement = (this.$refs["letter-area"] as Vue).$el as HTMLElement;
    const [key, offsetX, offsetY] = event.dataTransfer!.getData("text/plain").split(",");
    const relativeX = event.pageX - letterAreaElement.offsetLeft - parseInt(offsetX);
    const relativeY = event.pageY - letterAreaElement.offsetTop - parseInt(offsetY);

    this.letterDecorations.stickers.push({
      x: relativeX,
      y: relativeY,
      key,
    });
  }

  onLetterStickerClick(index: number): void {
    this.letterDecorations.stickers.splice(index, 1);
  }

  filterContent(text: string): string {
    const regexDial1 = /(0(\d+)?)[-.\s](\d+)[-.\s](\d+)/g; // "000-0000-0000" "000 0000 0000" "000.0000.0000"
    const regexDial2 = /(0\d\d\d\d\d\d?\d\d\d\d?)/g; // "00000000000"
    const regexDial3 = /(0(\d+)?)\)(\s+)?(\d+)[-.\s](\d+)/g; // "000) 0000 0000" "000) 0000-0000" "000) 0000.0000"
    const regexMail = /([A-Za-z1-9_\-.]+)@([A-Za-z1-9_-]+)\.([A-Za-z1-9_-]+)/g; // "so_me-th.1ng@e-xa_mp1e.com"

    text = text.replaceAll(regexDial1, "***");
    text = text.replaceAll(regexDial2, "***");
    text = text.replaceAll(regexDial3, "***");
    text = text.replaceAll(regexMail, "***");

    return text;
  }
}
</script>

<style lang="scss" scoped>
$decor-area-width: 20vw;
$letter-paper-area-width: 60vw;
$letter-paper-area-padding: 1em;
$contents-min-height: 80vh;
$contents-side-width: calc((90vw - var(--letter-area-width)) / 2);
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
    max-width: 400px;
    max-height: calc(100vh - var(--app-navbar-height) - 2rem);
    padding: 1em;
    background-color: rgba($color-secondary, 0.5);

    .vp-small-handle {
      display: none;  // none <-> flex

      cursor: pointer;
      align-items: center;
      justify-content: center;
      position: absolute;
      left: 50%;
      top: 0;
      transform: translateX(-50%) translateY(-100%);

      width: 40%;
      height: 48px;
      color: $color-foreground;
      background-color: $color-dark;
      border-top-left-radius: 0.5em;
      border-top-right-radius: 0.5em;
    }
  }

  #letter-write-area {
    // width: $letter-paper-area-width;
    min-height: $contents-min-height;
    //margin-right: calc((100vw - $decor-area-width - $letter-paper-area-width) / 2.5);
    margin: 0 auto;
  }

  .letter-write {
    &__decors {
      &__item-tab-wrapper {
        padding: 0.5em;
      }

      &__item-container {
        display: flex;
        justify-content: center;
        flex-direction: row;
        flex-wrap: wrap;

        .item {
          width: 96px;
          height: 96px;
          margin: 4px;

          & img {
            -webkit-user-drag: element !important;
            width: 100%;
            height: 100%;
          }
        }
      }
    }

    &__options {
      &__send-button {
        margin-top: 1em;
      }

      .option-container {
        margin: 0.5em;
      }
    }
  }

  @media (max-width: $viewport-letter-write-small-width) {
    padding-bottom: 15rem;

    .vp-small-hide_0 { display: none !important; }

    .side {
      position: fixed;
      top: initial;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%) translateY(100%);
      transition: transform 0.5s cubic-bezier(0, 0, 0, 1), opacity 0.2s;
      width: var(--letter-area-width);
      max-width: 100%;
      overflow: initial;
      background-color: $color-dark;
      z-index: 10;

      &.vp-small-appear {
        transform: translateX(-50%);
        will-change: transform;
      }

      .vp-small-handle { display: flex; }

      &.letter-write__decors .vp-small-handle { left: 25%; }
      &.letter-write__options .vp-small-handle { left: 75%; }
    }

    .letter-write {
      &__decors {
        &__item-tab-wrapper {
          overflow-x: auto;
          height: 8rem;
        }

        &__item-container {
          flex-wrap: nowrap;
          height: 100%;
          width: fit-content;
        }
      }
    }
  }
}
</style>
