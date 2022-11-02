<template>
  <div>
    <span>개발 중...</span><br><br>
    <span>포인트는 아이템 상점에서 아이템을 구매할 때 사용할 수 있어요.</span><br>
    <span>포인트는 로그인, 편지 쓰기, 업적 달성 등으로 얻을 수 있어요.</span>

    <div class="point-help__controls">
      <button class="button"
              @click="$router.back()">닫기</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class PointHelpView extends Vue { }
</script>

<style lang="scss" scoped>
.point-help {
  &__controls {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    margin-top: 1em;

    & > button {
      margin: 0 0.5em;
    }
  }
}
</style>
