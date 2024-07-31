import React from "react";
import { HashLoader } from "react-spinners";

export const Spinner = ({ loading }: { loading: boolean }) => {
  const override = {
    display: "block",
    margin: "100px auto",
  };

  return (
    <div>
      <HashLoader loading={loading} size="150" cssOverride={override} />
    </div>
  );
};
