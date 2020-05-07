import {ContactInfo} from './contactInfo';

export interface User {
  id?: number;
  username?: string;
  image?: string;
  isStaff?: boolean;
  contactInfo?: ContactInfo;
}

