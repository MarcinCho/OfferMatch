import React from "react";
import { NavLink } from "react-router-dom";
import logo from "../assets/images/image.png";

export const Navbar = () => {
  const linkClass = ({ isActive }: { isActive: boolean }) =>
    isActive
      ? "text-white bg-black hover:bg-gray-900 hover:text-white rounded-md px-3 py-2"
      : "text-white hover:bg-gray-900 hover:text-white rounded-md px-3 py-2";

  return (
    <nav className="bg-gradient-to-r from-cyan-400 to-blue-800 border-b-2 border-t-2 border-indigo-200">
      <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
        <div className="flex h-20 items-center justify-between">
          <div className="flex flex-1 items-center justify-center md:items-strech md:justify-start">
            <NavLink className="flex flex-shrink-0 items-center mr-4" to="/">
              <img
                src={logo}
                alt="ServiceMatch"
                className="h-10 w-auto rounded-full"
              />
              <span className="hidden md:block text-indigo-200 text-2xl font-bold ml-2">
                Service Match
              </span>
            </NavLink>
            <div className="md:ml-auto">
              <div className="flex space-x-2">
                <NavLink to="/" className={linkClass}>
                  Home
                </NavLink>
                <NavLink to="/companies" className={linkClass}>
                  Companies
                </NavLink>
                <NavLink to="/project/add" className={linkClass}>
                  Add Project
                </NavLink>
                <NavLink to="/login" className={linkClass}>
                  Login
                </NavLink>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
};
