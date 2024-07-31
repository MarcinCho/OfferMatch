import React from "react";
import { TbError404 } from "react-icons/tb";

export const NotFoundPage = () => {
  return (
    <div>
      <section className="text-center flex flex-col justify center items-center h96">
        <TbError404 className="text-red-500 text-9xl mb-4" />
        <h1 className="text-4xl font-bold mb-4">404 Not Found</h1>
        <p className="text-xl mb-5">This page does not exist </p>
      </section>
    </div>
  );
};
