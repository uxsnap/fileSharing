export default (filesObj) => {
  return filesObj.files.map((file) => ({
    id: file.id,
    name: file.originalName
  }));
};