export const projectLoader = async (args: { params: { id: string } }) => {
  const res = await fetch(`/api/project/${args.params.id}`);
  const data = await res.json();
  return data;
};
