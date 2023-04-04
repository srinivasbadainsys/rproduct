import dayjs from 'dayjs';

export interface IRuser {
  id?: number;
  login?: string | null;
  password?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  activated?: boolean | null;
  langKey?: string | null;
  imageUrl?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: string | null;
}

export const defaultValue: Readonly<IRuser> = {
  activated: false,
};
