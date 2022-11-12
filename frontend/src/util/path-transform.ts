export function getAssetPath(requirePath: string): string {
  return require(`@/assets/${requirePath}`);
}
