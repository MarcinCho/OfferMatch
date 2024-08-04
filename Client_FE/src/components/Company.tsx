import React from "react";
import { ICompany } from "../models/ICompany";
import { Link } from "react-router-dom";

export const Company = ({ company }: { company: ICompany }) => {
  return (
    <div className="bg-blue-400 rounded-xl">
      <div className="p-4 div mb-6">
        <div className="">
          <div className="">
            <h3 className="text-xl font-bold">{company.companyName}</h3>
            <h3 className="">{company.companyType}</h3>
            <h3>
              <Link to={`/company/${company.companyId}`}>Read more</Link>
            </h3>
          </div>
        </div>
      </div>
    </div>
  );
};
