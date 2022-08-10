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
