import { useState } from "react";
import { useLoaderData, useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { IProject } from "../models/IProject";

export const EditProjectPage = ({
  updateProject,
}: {
  updateProject: (project: IProject) => void;
}) => {
  const upProject: IProject = useLoaderData() as IProject;
  const { id } = useParams();

  const [title, setTitle] = useState(upProject.title);
  const [type, setType] = useState(upProject.type);
  const [description, setDescription] = useState(upProject.description);
  const [location, setLocation] = useState(upProject.location);
  const [price, setPrice] = useState(upProject.price);

  const [name, setName] = useState(upProject.name);
  const [email, setEmail] = useState(upProject.email);
  const [phoneNumber, setPhoneNumber] = useState(upProject.phoneNumber);
  const [file, setFile] = useState(upProject.file);

  const navigate = useNavigate();

  const submitForm = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    const uProject: IProject = {
      id,
      title,
      type,
      description,
      location,
      price,
      name,
      email,
      phoneNumber,
      file,
    };
    updateProject(uProject);
    toast.success("Project added!");
    return navigate("/projects");
  };

  const deleteClick = async (id: string | undefined) => {
    const confirm = window.confirm(
      "Are you sure? This will delete project " + id
    );
    if (!confirm) return;

    const request = await fetch(`http://localhost:8080/api/project/${id}`, {
      method: "DELETE",
    });

    if (request.ok) {
      toast.success("Project deleted!");
    } else {
      toast.error("Failed to delete project");
    }
    return navigate("/projects");
  };

  return (
    <section className="text-gray-900">
      <div className="container m-auto max-w-xl py-24 ">
        <div className="bg-indigo-50 px-6 py-8 mb-4 shadow-md rounded-2xl rounded-mb-border md:m-0">
          <form onSubmit={submitForm}>
            <h2 className="text-3xl text-center font-semibold mb-6">
              Add project that you need help with.
            </h2>
            <div className="mb-4 py-3">
              <label className="ml-4 block text-gray-900 font-bold mb-2">
                Project Title
              </label>
              <input
                type="text"
                id="title"
                name="title"
                className="border rounded w-full py-2 px-3 mb-2"
                placeholder="Awesome Project Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
              <div className="mb-4">
                <label className="ml-4 block font-bold mb-2">
                  Project type
                </label>
                <select
                  name="type"
                  id="type"
                  className="border rounded w-full py-2 px-3"
                  value={type}
                  onChange={(e) => setType(e.target.value)}
                  required
                >
                  <option value="B2B">B2B</option>
                  <option value="B2C">B2C</option>
                  <option value="Freelance">Freelance</option>
                  <option value="Other">Other</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="ml-4 block font-bold mb-2">Description</label>
                <textarea
                  rows={6}
                  className="border rounded w-full py-2 px-3 mb-2"
                  id="description"
                  name="description"
                  placeholder="Describe your project"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  required
                />
                <div className="mb-4">
                  <label htmlFor="" className="ml-4 block font-bold mb-2">
                    Location
                  </label>
                  <input
                    type="text"
                    id="location"
                    name="location"
                    className="border rounded w-full py-2 px-3 mb-2"
                    placeholder="Awesome Project Title"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label htmlFor="" className="ml-4 block font-bold mb-2">
                    Price
                  </label>
                  <input
                    type="number"
                    id="price"
                    name="price"
                    className="border rounded w-full py-2 px-3 mb-2"
                    placeholder="Awesome Project Title"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label htmlFor="" className="ml-4 block font-bold mb-2">
                    Your name
                  </label>
                  <input
                    type="text"
                    id="name"
                    name="name"
                    className="border rounded w-full py-2 px-3 mb-2"
                    placeholder="Awesome Project Title"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label htmlFor="" className="ml-4 block font-bold mb-2">
                    Your phone
                  </label>
                  <input
                    type="tel"
                    id="phone"
                    name="phone"
                    className="border rounded w-full py-2 px-3 mb-2"
                    placeholder="Awesome Project Title"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label htmlFor="" className="ml-4 block font-bold mb-2">
                    Your email
                  </label>
                  <input
                    type="email"
                    id="email"
                    name="email"
                    className="border rounded w-full py-2 px-3 mb-2"
                    placeholder="Awesome Project Title"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="ml-4 block font-bold mb-2">
                    Add files
                    <input
                      type="file"
                      className="w-full py-2 mb-2"
                      id="file"
                      name="file"
                      value={file}
                      onChange={(e) => setFile(e.target.value)}
                    />
                  </label>
                </div>
                <div className="my-2">
                  <button
                    className="bg-indigo-400 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                    type="submit"
                  >
                    Edit project
                  </button>
                </div>
                <div className="my-2">
                  <button
                    className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                    type="button"
                    onClick={() => deleteClick(id)}
                  >
                    Delete Project
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};
