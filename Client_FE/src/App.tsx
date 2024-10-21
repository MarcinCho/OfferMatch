import {
  createBrowserRouter,
  createRoutesFromElements,
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
import { EditProjectPage } from "./pages/EditProjectPage";
import { CompaniesPage } from "./pages/CompaniesPage";
import { SingleCompanyPage } from "./pages/SingleCompanyPage";
import { ChatPage } from "./pages/ChatPage";
import UserContext from "./context/UserContext";
import SocketClientContext, {
  ISocketClientContext,
} from "./context/SocketClientContext";
import { useState } from "react";
import backendClient from "./webSocket/BackendClient";
import { SocketClient } from "./webSocket/WsClient";
import { webSocketUrl } from "./ConfigConstats";
import { LoginPage } from "./pages/LoginPage";
import { ILogin } from "./models/ILogin";

export const App = () => {
  const [userContext, setUserContext] = useState({});
  const [socketClientContext, setSocketClientContext] =
    useState<ISocketClientContext>({
      socketClient: new SocketClient(webSocketUrl, ""),
    });

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

  const updateCompany = async (company: ICompany) => {
    const res = await fetch(
      `http://localhost:8090/company/id/${company.companyId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(company),
      }
    );
    return res;
  };

  const updateProject = async (project: IProject) => {
    const res = await fetch(`http://localhost:8080/api/project/${project.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(project),
    });
    return res;
  };

  const loginUser = async (login: ILogin) => {
    try {
      const jsonPayload = await backendClient.login({
        username: login.username,
        password: login.password,
      });
      console.log(jsonPayload);
      const { token, id, username, roles } = jsonPayload;
      backendClient.jwt = token;
      console.log(`here should be a token = ${backendClient.jwt}`);
      setSocketClientContext({
        socketClient: new SocketClient(webSocketUrl, token),
      });
      console.log(
        `Here is socket send jwt ${socketClientContext.socketClient.jwt}`
      );

      await socketClientContext.socketClient.awaitConnection();
      setUserContext({ id, username, token, roles });
    } catch (error) {
      console.error("Error initializing Socket client", error);
    }
  };

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
          path="/project/edit/:id"
          element={<EditProjectPage updateProject={updateProject} />}
          loader={projectLoader}
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
        <Route path="/chat" element={<ChatPage />} />
        <Route
          path="/project/:id"
          element={<SingleProjectPage />}
          loader={projectLoader}
        />
        <Route
          path="/company/:id"
          element={<SingleCompanyPage />}
          loader={companyLoader}
        />
        <Route path="/login" element={<LoginPage loginUser={loginUser} />} />
        <Route path="/projects" element={<ProjectsPage />} />
        <Route path="/companies" element={<CompaniesPage />} />
      </Route>
    )
  );
  return (
    <UserContext.Provider value={userContext}>
      <SocketClientContext.Provider value={socketClientContext}>
        <RouterProvider router={router} />
      </SocketClientContext.Provider>
    </UserContext.Provider>
  );
};
