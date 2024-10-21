import React, { useState } from "react";
import { ILogin } from "../models/ILogin";

export const LoginPage = ({
  loginUser,
}: {
  loginUser: (login: ILogin) => void;
}) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const submitLogin = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    const currentLogin: ILogin = {
      username,
      password,
    };
    loginUser(currentLogin);
  };

  return (
    <section className="text-gray-900">
      <div className="container m-auto max-w-xl py-24 ">
        <div className="bg-indigo-50 px-6 py-8 mb-4 shadow-md rounded-2xl rounded-mb-border md:m-0">
          <form onSubmit={submitLogin}>
            <h2 className="text-3xl text-center font-semibold mb-6">
              Login to your account.
            </h2>
            <div className="mb-4 py-3">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Login
              </label>
              <input
                type="text"
                id="title"
                name="username"
                className="border rounded w-full py-2 px-3 mb-2"
                placeholder="user3232"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
              </div>
              <div className="mb-4 py-3">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Login
              </label>
              <input
                type="password"
                id="title"
                name="password"
                className="border rounded w-full py-2 px-3 mb-2"
                placeholder="user3232"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              </div>
                  <button
                    className="bg-indigo-400 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                    type="submit"
                  >
                    Add project
                  </button>
          </form>
        </div>
      </div>
    </section>
  );
};
