import { useEffect, useState } from "react";
import { ICompany } from "../models/ICompany";
import { Spinner } from "../components/Spinner";
import { Company } from "../components/Company";

export const CompaniesPage = () => {
  const [companies, setCompanies] = useState<ICompany[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCompanies = async () => {
      try {
        const res = await fetch("http://localhost:8090/company/", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        const data = await res.json();
        setCompanies(data);
      } catch (error) {
        alert("Companies are not fetchig");
      } finally {
        setLoading(false);
      }
    };
    fetchCompanies();
  }, [companies]);

  return (
    <div>
      <section className="px-4 py-10">
        <div className="lg:container m-auto">
          <h2 className="text-3xl font-bold text-indigo-100 mb-6 text-center">
            Browse Companies
          </h2>
          {loading ? (
            <Spinner loading={loading} />
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {companies.map((company) => (
                <Company
                  key={company.companyId}
                  company={company as ICompany}
                />
              ))}
            </div>
          )}
        </div>
      </section>
    </div>
  );
};
