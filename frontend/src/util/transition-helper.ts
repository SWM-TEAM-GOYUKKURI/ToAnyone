export async function doAfter<T = unknown>(ms: number, action: () => T): Promise<T> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(action());
    }, ms);
  });
}
