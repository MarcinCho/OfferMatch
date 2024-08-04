import { Key } from "react";

export interface IProject {
  [id: string]: Key | null | undefined;
  title: string;
  type: string;
  description: string;
  location: string;
  price: string;
  name: string;
  email: string;
  phoneNumber: string;
  file: string;
}
