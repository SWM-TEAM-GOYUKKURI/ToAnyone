/* Decorations */
export enum DecorationCategory {
  FONT,
  LETTER_PAPER,
  STAMP
}

export type DecorationCategoryList = DecorationCategory[];

/* Letter */
export interface LetterWriteRequest {
  // Can be used both write and reply

  content: string,
  decorations: DecorationCategoryList,
}

interface LetterItemBase {
  content: string,
  decorations: DecorationCategoryList,
  id: number,
  senderName: string,
  sendDate: Date,
}

export interface LetterInboxItem extends LetterItemBase {
  read: boolean,
  receiverName: string,
  replied?: boolean,
}

export type LetterInboxItemList = LetterInboxItem[];

export interface LetterItemFull extends LetterInboxItem {
  replyLetters: LetterItemBase[],
}

/* User */
export interface LoginGoogleResponse {
  email: string,
  name: string,
  registrationFormFilled: boolean,
  token: string,
}

export enum UserProfileAge {
  TEN = "TEN",
  TWENTY = "TWENTY",
  THIRTY = "THIRTY",
  FORTY = "FORTY",
  FIFTY = "FIFTY",
  NOT_SELECTED = "NOT_SELECTED",
}

export enum UserProfileGender {
  MALE = "MALE",
  FEMALE = "FEMALE",
  NON_BINARY = "NON_BINARY",
  NOT_SELECTED = "NOT_SELECTED",
}

export enum UserProfileJob {
  STUDENT = "STUDENT",
  STUDENT_UNIV = "STUDENT_UNIV",
  MANAGEMENT = "MANAGEMENT",
  OFFICE_TECH = "OFFICE_TECH",
  PROFESSIONAL = "PROFESSIONAL",
  FREELANCER = "FREELANCER",
  SIMPLE_LABOR = "SIMPLE_LABOR",
  SELLER = "SELLER",
  SERVICE = "SERVICE",
  SELF_EMP = "SELF_EMP",
  HOME_MAKER = "HOME_MAKER",
  NOT_WORKING = "NOT_WORKING", // Unemployment or ETC
  NOT_SELECTED = "NOT_SELECTED",
}

export interface UserProfilePsychologicalExamItem {
  answerId: number,
  questionId: number
}

export interface UserProfileUpdateRequest {
  age: UserProfileAge,
  gender: UserProfileGender,
  job: UserProfileJob,
  nickname: string,
  psychologicalExams: UserProfilePsychologicalExamItem[],
}

export interface UserAchievementInfo {
  id: number,
  level: number,
  name: string,
  tag: string,
  userId: number,
}

export type UserAchievementInfoResponse = UserAchievementInfo[];
