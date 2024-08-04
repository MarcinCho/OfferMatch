import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

export const AddCompanyPage = ({
  addCompany,
}: {
  addCompany: (company: {
    companyName: string;
    email: string;
    contactPerson: string;
  }) => void;
}) => {
  const [companyName, setCompanyName] = useState("");
  const [email, setEmail] = useState("");
  const [contactPerson, setContactPerson] = useState("");

  const navigate = useNavigate();

  const submitForm = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    const newCompany: {
      companyName: string;
      email: string;
      contactPerson: string;
    } = { companyName, email, contactPerson };
    addCompany(newCompany);
    toast.success("Company added!");
    return navigate("/companies");
  };

  return (
    <section className="text-gray-900">
      <div className="container m-auto max-w-xl py-24">
        <div className="bg-indigo-50 px-6 py-8 mb-4 shadow-md rounded-2xl rounded-mb-border md:m-0">
          <form onSubmit={submitForm}>
            <h2 className="text-3xl text-center font-semiblod mb-6">
              Add a Company
            </h2>
            <div className="mb-2 py-2">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Company name
              </label>
              <input
                type="text"
                name=""
                id=""
                placeholder=""
                value={companyName}
                onChange={(e) => setCompanyName(e.target.value)}
                required
                className="border rounded w-full py-2 px-3 mb-2"
              />
            </div>
            <div className="mb-2 py-2">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Email
              </label>
              <input
                type="email"
                name=""
                id=""
                placeholder=""
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                className="border rounded w-full py-2 px-3 mb-2"
              />
            </div>
            <div className="mb-2 py-2">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Contact Person
              </label>
              <input
                type="text"
                name=""
                id=""
                placeholder=""
                value={contactPerson}
                onChange={(e) => setContactPerson(e.target.value)}
                required
                className="border rounded w-full py-2 px-3 mb-2"
              />
            </div>
            <div>
              <button
                className="bg-indigo-400 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Add Company
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};
