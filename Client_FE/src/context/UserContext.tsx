import { createContext } from "react";

interface IUserContext {
  id?: string;
  username?: string;
  token?: string;
  roles?: string[];
}

const UserContext = createContext<IUserContext>({});
export default UserContext;
