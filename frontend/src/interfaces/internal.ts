import { UserProfileGender, UserProfileAge, UserProfileJob } from "./backend";

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
  firstSignupPassed: boolean,
}

export interface UserInfo extends UserInfoBasic {
  profileImageId: number,
}
