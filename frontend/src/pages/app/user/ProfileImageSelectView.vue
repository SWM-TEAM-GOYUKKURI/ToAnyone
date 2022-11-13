<template>
  <div id="profile-edit-image-wrapper">
    <div class="profile-edit-image__controls">
      <button class="button"
              @click="$router.back()">닫기</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue } from "vue-class-component";

export default class ProfileImageSelectView extends Vue { }
</script>
