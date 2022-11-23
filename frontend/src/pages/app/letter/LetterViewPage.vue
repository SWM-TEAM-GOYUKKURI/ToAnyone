<template>
  <div id="letter-view-wrapper">
    <letter-area v-if="dataLoaded"
                 class="letter-view-area"
                 :senderNickname="letterItem.senderName"
                 :senderProfileImageId="letterItem.senderImageUrl"
                 :receiverNickname="letterItem.receiverName"
                 :receiverProfileImageId="letterItem.receiverImageUrl"
                 :textContent="letterItem.content"
                 @reportButtonClick="onOpenReportButtonClick(letterId)" />

    <div v-if="dataLoaded && letterItem.replyLetters"
         class="letter-view__replies">
      <letter-area v-for="letter in letterItem.replyLetters"
                   class="letter-view-area reply"
                   :key="letter.id"
                   :senderNickname="letter.senderName"
                   :senderProfileImageId="letter.senderImageUrl"
                   :receiverNickname="letter.receiverName"
                   :receiverProfileImageId="letter.receiverImageUrl"
                   :textContent="letter.content"
                   @reportButtonClick="onOpenReportButtonClick(letter.id, true)" />
    </div>

    <a v-if="!lastLetterSentByMe"
       href="#"
       class="letter-view__reply-button button round"
       @click="goLetterReplyPage"><v-icon>mdi-reply</v-icon> <span>답장하기</span></a>
  </div>

  <v-slide-y-reverse-transition>
    <in-app-dialog id="letter-report" v-if="showReportDialog" :fullscreenOnVPSmall="true">
      <div id="letter-report">
        <h2>편지 신고</h2>
        <span>편지에 어떤 내용이 담겨있나요?</span>

        <v-radio-group v-model="reportDialogData.reportType" direction="horizontal">
          <v-radio label="욕설·비방 등 언어폭력" value="language" />
          <v-radio label="허위사실 제공·유포" value="falseinfo" />
          <v-radio label="발신자 또는 수신자의 개인정보" value="privacy" />
          <v-radio label="성폭력·성희롱" value="sexual" />
          <v-radio label="마약·약물 관련 정보 제공 또는 권유" value="drug" />
          <v-radio label="기타 사유" value="etc" />
        </v-radio-group>

        <div class="letter-report__controls">
          <button class="button primary" @click="onReportButtonClick"><v-icon>mdi-alert-octagon</v-icon> <span>신고</span></button>
          <button class="button" @click="showReportDialog = false">닫기</button>
        </div>
      </div>
    </in-app-dialog>
  </v-slide-y-reverse-transition>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterArea from "@/components/app/letter/LetterArea.vue";
import InAppDialog from "@/components/InAppDialog.vue";
import { isSuccessful } from "@/util/backend";
import { LetterItemFull } from "@/interfaces/backend";

@Options({
  components: {
    LetterArea,
    InAppDialog,
  },
})
export default class LetterViewPage extends Vue {
  letterItem!: LetterItemFull;
  dataLoaded = false;
  showReportDialog = false;
  reportDialogData = {
    id: -1,
    rootId: -1,
    isReplyLetter: false,
    reportType: "etc",
  };

  get letterId(): number | null {
    if(this.$route.params) {
      return parseInt(this.$route.params.letterId as string);
    } else {
      return null;
    }
  }

  get lastLetterSentByMe(): boolean {
    if(this.dataLoaded && this.letterItem) {
      if(this.letterItem.replyLetters && this.letterItem.replyLetters.length > 0) {
        const lastReplyLetter = this.letterItem.replyLetters[this.letterItem.replyLetters.length - 1];
        if(lastReplyLetter.senderName === this.$store.state.user.user!.nickname) {
          return true;
        }
      } else {
        if(this.letterItem.senderName === this.$store.state.user.user!.nickname) {
          return true;
        }
      }
    }

    return false;
  }

  beforeMount(): void {
    if(!this.letterId) {
      alert("편지 정보가 없습니다.");
      this.$router.replace({ name: "main" });
    }
  }

  async mounted() {
    let error = false;

    if(this.letterId) {
      /* Load letter contents */
      const response = await this.$api.getLetter(this.letterId!);

      if(isSuccessful(response.statusCode) && response.data) {
        this.letterItem = response.data;

        this.dataLoaded = true;
      } else {
        // TEMP ALERT
        alert(`편지 데이터 불러오는 중 오류: ${response.statusCode}`);
        error = true;
      }

      /* Letter read state update */
      const readStateUpdateResponse = await this.$api.updateLetterReadState(this.letterId!);

      if(!isSuccessful(readStateUpdateResponse.statusCode)) {
        // TEMP ALERT
        alert(`편지 읽기 상태 업데이트 중 오류: ${readStateUpdateResponse.statusCode}`);
        error = true;
      }

      /* Update global unread letters */
      // Load unread letters and save it
      if(!(await this.$api.updateUnreadLetters(this))) {
        // TEMP ALERT
        alert("편지 보관 목록을 업데이트하는 중 오류");
      }
    }

    if(error) {
      this.$router.back();
    }

    window.scrollTo(0, document.body.scrollHeight);
  }

  goLetterReplyPage(): void {
    this.$router.push({
      name: "letter-reply",
      params: {
        replyMode: "true",
        replyModeData: JSON.stringify({ ...this.letterItem, id: this.letterId }),
      },
    });
  }

  onOpenReportButtonClick(letterId: number, isReplyLetter = false): void {
    this.showReportDialog = true;
    this.reportDialogData.id = letterId;
    this.reportDialogData.rootId = this.letterId!;
    this.reportDialogData.isReplyLetter = isReplyLetter;
  }

  onReportButtonClick(): void {
    alert("기능 구현 중입니다.");
  }
}
</script>

<style lang="scss" scoped>
#letter-view-wrapper {
  padding: 1rem 0;

  .letter-view-area {
    min-height: 500px;
    margin: 3rem auto;
    max-width: 100%;

    &.reply {
      width: calc(var(--letter-area-width) - 2rem);
    }
  }

  .letter-view {
    &__reply-button {
      position: fixed;
      bottom: 2rem;
      right: 2rem;
      font-size: 1.25em;
      box-shadow: 0 0.25em 0.75em rgba(0, 0, 0, 0.5);
      white-space: pre;
    }
  }
}

#letter-report {
  & > * { margin: 0.33em 0; }

  .letter-report {
    &__controls {
      display: flex;
      align-items: stretch;
      justify-content: flex-end;

      & > * { margin: 0 0.25em; }

      .button.primary { background-color: #F99; }
    }
  }
}
</style>
