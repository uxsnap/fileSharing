export default (filesObj) =>
  filesObj.files.map((file) => ({
    id: file.id,
    name: file.originalName
  }));