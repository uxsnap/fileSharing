export default (filesData) =>
  filesData.length ? filesData.map((file) => ({
    id: file.fileId,
    text: file.fileName,
    link: file.fileLink,
    iconType: 'download'
  })) : [];