import React from "react";
import { Link, useLoaderData } from "react-router-dom";
import { ICompany } from "../models/ICompany";

export const SingleCompanyPage = () => {
  const company = useLoaderData() as ICompany;
  return (
    <div className="container m-auto max-w-3xl py-24">
      <div className="bg-blue-400 rounded-xl shadow md relative text-gray-700">
        <div className="p-4">
          <div className="mb-6">
            <div className=" py-2">{company.companyName}</div>
            <h3 className="text-gray-950 text-xl font-bold">
              {company.companyType}
            </h3>
            <h3 className="text-xl font-bold"></h3>
            <h4>
              <Link to={`/company/edit/${company.companyId}`}>
                {" "}
                Edit company
              </Link>
            </h4>
          </div>
        </div>
      </div>
    </div>
  );
};
