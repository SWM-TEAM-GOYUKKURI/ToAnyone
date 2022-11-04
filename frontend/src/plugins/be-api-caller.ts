import { LetterInboxItemList } from "@/interfaces/backend";
import { BECallReturn, beGET } from "@/util/backend";

export default class APICaller {
  private token!: string;

  registerToken(token: string): void {
    this.token = token;
  }

  isAvailable(): boolean {
    return !!this.token;
  }

  async getInbox(): Promise<BECallReturn<LetterInboxItemList>> {
    return await beGET<LetterInboxItemList>("/letter/inbox", null, { credentials: this.token });
  }

  async getSentInbox(): Promise<BECallReturn<LetterInboxItemList>> {
    return await beGET<LetterInboxItemList>("/letter/sent", null, { credentials: this.token });
  }
}
