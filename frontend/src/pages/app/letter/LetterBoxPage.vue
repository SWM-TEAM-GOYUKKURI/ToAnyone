<template>
  <div id="letter-box-wrapper">
    <v-progress-circular v-if="!requestCompleted"
                        indeterminate />

    <div v-if="requestCompleted && _letterItems.length > 0">
      <letter-box-item v-for="item in letterItems"
                       :key="item.letterId"
                       :letterItem="item" />
    </div>
    <div v-else-if="requestCompleted && _letterItems.length <= 0">
      <span class="no-letters">아직 받은 편지가 없어요😖</span>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import LetterBoxItem from "@/components/app/letter/LetterBoxItem.vue";
import { beGET } from "@/util/backend";
import { ILetterBoxItem } from "@/interfaces/ILetterItem";

@Options({
  components: {
    LetterBoxItem,
  },
})
export default class LetterBoxPage extends Vue {
  _letterItems: ILetterBoxItem[] = [];
  requestCompleted = false;

  get letterItems(): ILetterBoxItem[] {
    return Array.from(this._letterItems).sort((a, b) => {
      // TODO: sort by read status, receive data... need more data from backend

      return 0;
    });
  }

  async mounted() {
    const response = await beGET("/letter/inbox", null, { credentials: this.$store.state.auth.token! });

    if(response.statusCode === 200) {
      this._letterItems = response.data as unknown as ILetterBoxItem[];
    } else {
      this._letterItems = [];
    }

    this.requestCompleted = true;
  }

  onLetterBoxItemClick(): void {
    this.$router.push({ name: "letter-view", params: { letterId: 9999999 } });
  }
}
</script>

<style lang="scss" scoped>
#letter-box-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2em 0;
  width: 75vw;
  margin: auto;

  & > * {
    text-align: center;
  }

  .no-letters {
    font-size: 2em;
  }
}
</style>
