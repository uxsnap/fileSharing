export default (mainClassName, nullableClassName) =>
  `${mainClassName} ${nullableClassName ? nullableClassName : ''}`;
