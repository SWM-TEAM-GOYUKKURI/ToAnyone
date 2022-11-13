export function getAssetPath(requirePath: string): string {
  return require(`@/assets/${requirePath}`);
}

export function getPicsumUrl(seedId: number | null, size: { x: number, y: number } = { x: 200, y: 200 }): string {
  if(!seedId || seedId <= 0) {
    return `https://picsum.photos/id/998/${size.x}/${size.y}`; // default
  } else {
    return `https://picsum.photos/seed/toanyone${seedId}/${size.x}/${size.y}`;
  }
}
