import { LetterInboxItemList, LetterItemFull, LetterWriteRequest, LoginGoogleResponse, UserProfileUpdateRequest } from "@/interfaces/backend";
import { BECallReturn, beGET, bePOST, bePUT } from "@/util/backend";

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
}
