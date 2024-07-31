import React, { useState } from "react";
import { ProjectModel } from "../models/IProject";
import { Link } from "react-router-dom";
export const Project = ({ project }: { project: ProjectModel }) => {
  //   const [fullDesc, setFullDesc] = useState(false);

  return (
    <div className="bg-blue-400 rounded-xl shadow md relative text-gray-700">
      <div className="p-4">
        <div className="mb-6">
          <div className=" py-2">{project.type}</div>
          <h3 className="text-gray-950 text-xl font-bold">{project.title}</h3>
          <h3 className="text-xl font-bold">
            <Link to={`/project/${project.id}`}>Read more</Link>
          </h3>
        </div>
      </div>
    </div>
  );
};
