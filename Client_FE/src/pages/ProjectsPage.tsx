import React, { useEffect, useState } from "react";
import { Spinner } from "../components/Spinner";
import { Project } from "../components/Project";
import { ProjectModel } from "../models/IProject";

export const ProjectsPage = () => {
  const [projects, setProjects] = useState<ProjectModel[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProjects = async () => {
      const apiUrl = "http://localhost:8080/api/project";
      try {
        const res = await fetch(apiUrl);
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
        <div className="lg:container m-auto">
          <h2 className="text-3xl font-bold text-indigo-200 mb-6 text-center">
            Browse Projects
          </h2>
          {loading ? (
            <Spinner loading={loading} />
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {projects.map((project) => (
                <Project key={project.id} project={project as ProjectModel} />
              ))}
            </div>
          )}
        </div>
      </section>
    </div>
  );
};
