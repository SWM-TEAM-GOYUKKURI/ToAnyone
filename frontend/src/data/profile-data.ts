import { UserProfileAge, UserProfileGender, UserProfileJob } from "@/interfaces/backend";

export interface ProfileSelectItem {
  value: string,
  title: string,
}

export enum UserProfileAgeName {
  TEN = "10대",
  TWENTY = "20대",
  THIRTY = "30대",
  FORTY = "40대",
  FIFTY = "50대",
  NOT_SELECTED = "선택하지 않음",
}

export enum UserProfileGenderName {
  MALE = "남성",
  FEMALE = "여성",
  NON_BINARY = "논바이너리 / 기타",
  NOT_SELECTED = "선택하지 않음",
}

export enum UserProfileJobName {
  STUDENT = "초·중·고등학생",
  STUDENT_UNIV = "대학생",
  MANAGEMENT = "경영·관리",
  OFFICE_TECH = "사무·기술직",
  PROFESSIONAL = "전문직",
  FREELANCER = "프리랜서",
  SIMPLE_LABOR = "단순노무 (기능·작업 등)",
  SELLER = "판매·영업·마케팅",
  SERVICE = "서비스",
  SELF_EMP = "자영업",
  HOME_MAKER = "전업주부",
  NOT_WORKING = "무직·기타",
  NOT_SELECTED = "선택하지 않음",
}

export const NOT_SELECTED_ITEM: ProfileSelectItem = Object.freeze({
  value: "NOT_SELECTED",
  title: "선택하지 않음",
});

export const GENDER_ITEMS: readonly ProfileSelectItem[] = Object.freeze([
  {
    value: UserProfileGender.MALE,
    title: UserProfileGenderName.MALE,
  }, {
    value: UserProfileGender.FEMALE,
    title: UserProfileGenderName.FEMALE,
  }, {
    value: UserProfileGender.NON_BINARY,
    title: UserProfileGenderName.NON_BINARY,
  },
  NOT_SELECTED_ITEM,
]);

export const AGE_ITEMS: readonly ProfileSelectItem[] = Object.freeze([
  {
    value: UserProfileAge.TEN,
    title: UserProfileAgeName.TEN,
  }, {
    value: UserProfileAge.TWENTY,
    title: UserProfileAgeName.TWENTY,
  }, {
    value: UserProfileAge.THIRTY,
    title: UserProfileAgeName.THIRTY,
  }, {
    value: UserProfileAge.FORTY,
    title: UserProfileAgeName.FORTY,
  }, {
    value: UserProfileAge.FIFTY,
    title: UserProfileAgeName.FIFTY,
  },
  NOT_SELECTED_ITEM,
]);

export const JOB_ITEMS: readonly ProfileSelectItem[] = Object.freeze([
  {
    value: UserProfileJob.STUDENT,
    title: UserProfileJobName.STUDENT,
  }, {
    value: UserProfileJob.STUDENT_UNIV,
    title: UserProfileJobName.STUDENT_UNIV,
  }, {
    value: UserProfileJob.MANAGEMENT,
    title: UserProfileJobName.MANAGEMENT,
  }, {
    value: UserProfileJob.OFFICE_TECH,
    title: UserProfileJobName.OFFICE_TECH,
  }, {
    value: UserProfileJob.PROFESSIONAL,
    title: UserProfileJobName.PROFESSIONAL,
  }, {
    value: UserProfileJob.FREELANCER,
    title: UserProfileJobName.FREELANCER,
  }, {
    value: UserProfileJob.SIMPLE_LABOR,
    title: UserProfileJobName.SIMPLE_LABOR,
  }, {
    value: UserProfileJob.SELLER,
    title: UserProfileJobName.SELLER,
  }, {
    value: UserProfileJob.SERVICE,
    title: UserProfileJobName.SERVICE,
  }, {
    value: UserProfileJob.SELF_EMP,
    title: UserProfileJobName.SELF_EMP,
  }, {
    value: UserProfileJob.HOME_MAKER,
    title: UserProfileJobName.HOME_MAKER,
  }, {
    value: UserProfileJob.NOT_WORKING,
    title: UserProfileJobName.NOT_WORKING,
  },
  NOT_SELECTED_ITEM,
]);
