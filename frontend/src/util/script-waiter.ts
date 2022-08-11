/**
 * An async function to wait for specific global object become available, which should be injected in global `window` object by external script.
 * This function polls the availability of object every 100ms, at maximum 100 tries.
 *
 * @param globalObjName - The name of object which should be injected in `window`.
 * @returns A `Promise<boolean>`, which is `await`able for `boolean` value.
 *          `true` if the object has found in maximum polling tries. `false` if not.
 */
export default async function waitFor(globalObjName: string) {
  const checkInterval = 100;
  const maxTryCount = 100;
  let found = false;
  let tryCount = 0;

  while(!found) {
    found = await new Promise<boolean>((resolve) => {
      setTimeout(() => {
        if(globalObjName in window) {
          resolve(true);
        } else {
          resolve(false);
        }
      }, checkInterval);
    });

    tryCount += 1;
    if(tryCount >= maxTryCount) break;
  }

  return found;
}
