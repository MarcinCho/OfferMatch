import {
  createBrowserRouter,
  createRoutesFromElements,
  LoaderFunctionArgs,
  Route,
  RouterProvider,
} from "react-router-dom";
import { MainLayout } from "./layouts/MainLayout";
import { HomePage } from "./pages/HomePage";
import { NotFoundPage } from "./pages/NotFoundPage";
import { AddProjectPage } from "./pages/AddProjectPage";
import { ProjectsPage } from "./pages/ProjectsPage";
import { SingleProjectPage } from "./pages/SingleProjectPage";
import { IProject } from "./models/IProject";
import { projectLoader } from "./components/projectLoader";
import { AddCompanyPage } from "./pages/AddCompanyPage";
import { ICompany } from "./models/ICompany";
import { EditCompanyPage } from "./pages/EditCompanyPage";
import { companyLoader } from "./components/companyLoader";

const addProject = async (project: IProject) => {
  const ppp = await fetch("http://localhost:8080/api/project", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(project),
  });
  return ppp;
};

const addCompany = async (company: {
  companyName: string;
  email: string;
  contactPerson: string;
}) => {
  console.log(company);
  const ppp = await fetch("http://localhost:8090/company/", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(company),
  });
  console.log(company);
  return ppp;
};

// const editCompany = async (company: ICompany) => {
//   console.log(company);
//   const ppp = await fetch("http://localhost:8090/company/", {
//     method: "POST",
//     headers: {
//       "Content-Type": "application/json",
//     },
//     body: JSON.stringify(company),
//   });
//   console.log(company);
//   return ppp;
// };

const updateCompany = async (company: ICompany) => {
  const res = await fetch(`http://localhost:8090/company/id/${company.id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(company),
  });
  return res;
};

export const App = () => {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path="/" element={<MainLayout />}>
        <Route index element={<HomePage />} />
        <Route path="*" element={<NotFoundPage />} />
        <Route
          path="/project/add"
          element={<AddProjectPage addProject={addProject} />}
        />
        <Route
          path="/company/add"
          element={<AddCompanyPage addCompany={addCompany} />}
        />
        <Route
          path="/company/edit/:id"
          element={<EditCompanyPage updateCompany={updateCompany} />}
          loader={companyLoader}
        />
        <Route
          path="/project/:id"
          element={<SingleProjectPage />}
          loader={(args: LoaderFunctionArgs<unknown>) => {
            const { params } = args;
            return projectLoader({ params: { id: params.id || "" } });
          }}
        />
        <Route path="/project" element={<ProjectsPage />} />
      </Route>
    )
  );
  return <RouterProvider router={router} />;
};
