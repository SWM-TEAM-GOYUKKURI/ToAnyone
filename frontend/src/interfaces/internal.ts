import { UserProfileGender, UserProfileAge, UserProfileJob, UserProfileUpdateRequest } from "./backend";

export interface SignupData {
  nickname?: string,
  gender?: UserProfileGender,
  age?: UserProfileAge,
  job?: UserProfileJob,
  survey?: Record<number, number>,
}

export interface UserInfoBasic {
  nickname: string,
  email: string,
  registrationFormFilled: boolean,
}

export interface UserInfo extends UserInfoBasic {
  achievementCountValue: number,
  createdAt: Date,
  id: number,
  lastLogin: Date,
  loginCount: number,
  loginType: "GOOGLE",
  name: string,
  point: number,
  profile: UserProfileUpdateRequest,
  receiveCount: number,
  sendLetterCountValue: number,
  uniqueId: string,
  userImageUrl: string, // THIS WILL BE USED AS PICSUM PHOTO ID (number), DON'T TAKE CARE OF THE PROPERTY NAME
}

export interface LetterStickerItem {
  x: number,
  y: number,
  key: string,
}
