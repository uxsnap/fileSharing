import { RES_STATUS } from "nuxxxcomponentlib/dist";
import { defaultResponseObject } from "./index";

export default function (func, dataManipulationOptions, ...funcParams) {
  let self = this;
  return func(...funcParams)
    .then((res) => {
      if (res && res.status === RES_STATUS.OK) {
        return Promise.resolve({
          ...defaultResponseObject(),
          data: dataManipulationOptions ? dataManipulationOptions(res.data) : res.data,
          status: res.status
        });
      }
      self.onError(res ? res.data : { errors: ["Server is probably not working."] });
      return Promise.reject({ status: RES_STATUS.ERROR });
    })
}