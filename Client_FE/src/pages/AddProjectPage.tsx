import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { ProjectModel } from "../models/IProject";

export const AddProjectPage = ({
  addProject,
}: {
  addProject: (project: ProjectModel) => void;
}) => {
  const [title, setTitle] = useState("");
  const [type, setType] = useState("B2C");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [price, setPrice] = useState("");

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [file, setFile] = useState("");

  const navigate = useNavigate();

  const submitForm = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    const newProject: ProjectModel = {
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
    addProject(newProject);
    toast.success("Project added!");
    return navigate("/companies");
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
                <div>
                  <button
                    className="bg-indigo-400 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                    type="submit"
                  >
                    Add project
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
