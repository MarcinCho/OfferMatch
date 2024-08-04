import { LoaderFunctionArgs } from "react-router-dom";

export const companyLoader = async (args: LoaderFunctionArgs<unknown>) => {
  const { params } = args;
  const id = parseInt(params.id as string);
  const res = await fetch(`http://localhost:8090/company/id/${id}`);
  const data = await res.json();
  return data;
};
