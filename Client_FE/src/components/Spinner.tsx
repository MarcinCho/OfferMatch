import React from "react";
import { HashLoader } from "react-spinners";

export const Spinner = ({ loading }: { loading: boolean }) => {
  return (
    <div>
      <HashLoader loading={loading} />
    </div>
  );
};
