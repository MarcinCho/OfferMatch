import React, { useEffect, useState } from "react";
import { Spinner } from "../components/Spinner";
import { Project } from "../components/Project";
import { IProject } from "../models/IProject";
import { Link } from "react-router-dom";

export const ProjectsPage = () => {
  const [projects, setProjects] = useState<IProject[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/project", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });

        const data = await res.json();
        setProjects(data);
      } catch (error) {
        alert("Projects does not fetch from back end");
      } finally {
        setLoading(false);
      }
    };
    fetchProjects();
  }, [projects]);

  return (
    <div>
      <section className="px-4 py-10">
        <div className="lg:container m-auto text-center my-6">
          <h2 className="text-3xl font-bold text-indigo-200 mb-6 text-center">
            Browse Projects
          </h2>
          <Link
            className="text-center bg-blue-400 hover:bg-indigo-600 text-white font-bold py-4 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
            to="/project/add"
          >
            Add project
          </Link>
        </div>
        <div className="mx-6">
          {loading ? (
            <Spinner loading={loading} />
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {projects.map((project) => (
                <Project key={project.id} project={project as IProject} />
              ))}
            </div>
          )}
        </div>
      </section>
    </div>
  );
};
