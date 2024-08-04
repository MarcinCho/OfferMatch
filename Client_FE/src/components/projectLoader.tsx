import { LoaderFunctionArgs } from "react-router-dom";

export const projectLoader = async (args: LoaderFunctionArgs<unknown>) => {
  const res = await fetch(`/api/project/${args.params.id}`);
  const data = await res.json();
  return data;
};
