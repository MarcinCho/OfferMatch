import React, { useState } from "react";
import { ICompany } from "../models/ICompany";
import { useLoaderData, useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";

export const EditCompanyPage = ({
  updateCompany,
}: {
  updateCompany: (company: ICompany) => void;
}) => {
  const company: ICompany = useLoaderData() as ICompany;
  const [companyName, setCompanyName] = useState(company.companyName);
  const [companyType, setCompanyType] = useState(company.companyType);
  const [description, setDescription] = useState(company.description);
  const [phoneNumber, setPhoneNumber] = useState(company.phoneNumber);
  const [email, setEmail] = useState(company.email);
  const [address, setAddress] = useState(company.address);
  const [contactPerson, setContactPerson] = useState(company.contactPerson);

  const navigate = useNavigate();
  const { id } = useParams();

  const submitForm = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    const edtCompany: ICompany = {
      companyId: Number(id),
      companyName,
      companyType,
      description,
      phoneNumber,
      email,
      address,
      contactPerson,
    };
    console.log(edtCompany);
    updateCompany(edtCompany);
    toast.success("Company added!");
    return navigate("/companies");
  };

  const deleteClick = async (id: string | undefined) => {
    const confirm = window.confirm(
      "Are you sure? This will delete company " + id
    );
    if (!confirm) return;

    const request = await fetch(`http://localhost:8090/company/id/${id}`, {
      method: "DELETE",
    });

    if (request.ok) {
      toast.success("Company deleted!");
    } else {
      toast.error("Failed to delete company");
    }
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
                Company type
              </label>
              <select
                name="type"
                id="type"
                className="border rounded w-full py-2 px-3"
                value={companyType}
                onChange={(e) => setCompanyType(e.target.value)}
                required
              >
                <option value="B2B">General</option>
                <option value="B2C">IT</option>
                <option value="Freelance">Construction</option>
                <option value="Other">Cleaning</option>
              </select>
            </div>
            <div className="mb-2 py-2">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Description
              </label>
              <textarea
                rows={6}
                name="description"
                id="description"
                placeholder="Few words About company"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className="border rounded w-full py-2 px-3 mb-2"
              />
            </div>
            <div className="mb-2 py-2">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Phone number
              </label>
              <input
                type="tel"
                name="phone"
                id="phone"
                placeholder="789789789"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
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
                Address
              </label>
              <input
                type="text"
                name=""
                id=""
                placeholder=""
                value={address}
                onChange={(e) => setAddress(e.target.value)}
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
            <div className="my-2">
              <button
                className="bg-indigo-400 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                type="submit"
              >
                Edit Company
              </button>
            </div>
            <div className="my-2">
              <button
                className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                type="button"
                onClick={() => deleteClick(id)}
              >
                Delete Company
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};
