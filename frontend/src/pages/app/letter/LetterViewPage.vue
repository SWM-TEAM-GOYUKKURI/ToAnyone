<template>
  <div id="letter-view-wrapper">
    <letter-area v-if="dataLoaded"
                 class="letter-view-area"
                 :senderNickname="letterItem.senderNickname"
                 :receiverNickname="$store.state.auth.userBasicInfo.nickname"
                 :textContent="letterItem.content" />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterArea from "@/components/app/letter/LetterArea.vue";
import { beGET } from "@/util/backend";
import { ILetterBoxItem } from "@/interfaces/ILetterItem";

@Options({
  components: {
    LetterArea,
  },
})
export default class LetterViewPage extends Vue {
  letterItem!: ILetterBoxItem;
  dataLoaded = false;

  beforeMount(): void {
    if(!(this.$route.params && this.$route.params.letterId)) {
      this.$router.replace({ name: "main" });
    }
  }

  async mounted() {
    const response = await beGET(`/letter/inbox/${this.$route.params.letterId}`, null, { credentials: this.$store.state.auth.token! });

    if(response.statusCode === 200) {
      this.letterItem = response.data as unknown as ILetterBoxItem;

      this.dataLoaded = true;
    } else {
      // Error handling
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
