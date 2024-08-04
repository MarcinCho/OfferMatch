import { Link, useLoaderData } from "react-router-dom";
import { IProject } from "../models/IProject";

export const SingleProjectPage = () => {
  const project = useLoaderData() as IProject;

  return (
    <div className="container m-auto max-w-3xl py-24">
      <div className="bg-blue-400 rounded-xl shadow md relative text-gray-700">
        <div className="p-4">
          <div className="mb-6">
            <div className=" py-2">{project.type}</div>
            <h3 className="text-gray-950 text-xl font-bold">{project.title}</h3>
            <h3 className="text-xl font-bold">
              <Link to={`/project/edit/${project.id}`}>Edit project</Link>
            </h3>
          </div>
        </div>
      </div>
    </div>
  );
};
