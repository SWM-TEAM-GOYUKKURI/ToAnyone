import { Vue } from "vue-class-component";
import { LetterInboxItemList, LetterItemFull, LetterWriteRequest, LoginGoogleResponse, UserAchievementInfoResponse, UserProfileUpdateRequest } from "@/interfaces/backend";
import { BECallReturn, beDEL, beGET, bePOST, bePUT, filterUnreadLetters, isSuccessful } from "@/util/backend";
import { UserInfo } from "@/interfaces/internal";

export default class APICaller {
  private token!: string;

  registerToken(token: string): void {
    this.token = token;
  }

  isAvailable(): boolean {
    return !!this.token;
  }

  async requestGoogleLogin(googleCredential: string): Promise<BECallReturn<LoginGoogleResponse>> {
    return await bePOST<LoginGoogleResponse>("/login/google", {}, { credential: googleCredential });
  }

  async getInbox(): Promise<BECallReturn<LetterInboxItemList>> {
    return await beGET<LetterInboxItemList>("/letter/inbox", null, { credentials: this.token });
  }

  async getSentInbox(): Promise<BECallReturn<LetterInboxItemList>> {
    return await beGET<LetterInboxItemList>("/letter/sent", null, { credentials: this.token });
  }

  async getUserInfo(): Promise<BECallReturn<UserInfo>> {
    return await beGET<UserInfo>("/user/me", null, { credentials: this.token });
  }

  async getUserAchievementInfo(): Promise<BECallReturn<UserAchievementInfoResponse>> {
    return await beGET<UserAchievementInfoResponse>("/user/me/achievement", null, { credentials: this.token });
  }

  async deleteUser(): Promise<BECallReturn<null>> {
    // NOT YET IMPLEMENTED IN BACKEND
    return await beDEL("/user/me", null, { credentials: this.token });
  }

  async putUserSignupData(data: UserProfileUpdateRequest): Promise<BECallReturn<null>> {
    return await bePUT("/user", data, { credentials: this.token });
  }

  async writeLetter(data: LetterWriteRequest): Promise<BECallReturn<null>> {
    return await bePOST("/letter", data, { credentials: this.token });
  }

  async writeReplyLetter(targetLetterId: number, data: LetterWriteRequest): Promise<BECallReturn<null>> {
    return await bePOST(`/letter/inbox/${targetLetterId}`, data, { credentials: this.token });
  }

  async getLetter(targetLetterId: number): Promise<BECallReturn<LetterItemFull>> {
    return await beGET<LetterItemFull>(`/letter/inbox/${targetLetterId}`, null, { credentials: this.token });
  }

  async updateLetterReadState(targetLetterId: number): Promise<BECallReturn<null>> {
    return await bePUT(`/letter/inbox/${targetLetterId}`, {}, { credentials: this.token });
  }

  async updateUnreadLetters(vueThis: Vue): Promise<boolean> {
    // THIS IS VERY UNEFFICIENT, IMPROPER, AND VERY HACKY WAY TO USE `Vue`,
    // BUT WE HAVE NO TIME TO MAKE THIS PROPERLY, SO I JUST MADE THIS FUNCTION.

    const inboxResponse = await this.getInbox();
    const sentInboxResponse = await this.getSentInbox();

    if(isSuccessful(inboxResponse.statusCode) && isSuccessful(sentInboxResponse.statusCode) && inboxResponse.data && sentInboxResponse.data) {
      vueThis.$store.commit("user/updateUnreadLetters", filterUnreadLetters(inboxResponse.data));
      vueThis.$store.commit("user/updateUnreadSentLetters", filterUnreadLetters(sentInboxResponse.data));
      return true;
    } else {
      return false;
    }
  }
}
