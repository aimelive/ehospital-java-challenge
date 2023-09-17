import React from "react";

type MenuItem = {
  url: string;
  title: string;
};

type IconProps = { active: boolean };

type SideBarITem = {
  icon: (props: IconProps) => React.JSX.Element;
} & MenuItem;

type LoginDto = { identifier: string; password: string };

type ProviderProps = {
  children: React.ChildNode;
};

type User = {
  name: string;
  gender: string;
  role: string;
  age: number;
};
type Patient = User & {
  username: string;
};
type Physician = User & {
  email: string;
};
type Pharmacist = User & {
  phoneNumber: string;
};
type Consultation = {
  disease: string;
  description: string;
  physician: Physician;
};
type Medicine = {
  medName: string;
  medPrice: string;
  medExpiration: string;
};
