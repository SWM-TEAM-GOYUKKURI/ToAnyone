const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    devServer: {
      allowedHosts: "all",
    },
  },
  css: {
    loaderOptions: {
      sass: {
        additionalData: (content, loaderContext) => {
          if(loaderContext.resourcePath.endsWith("/styles/global.scss")) {
            return content;
          }

          return `@import "@/styles/global.scss"; ${content}`;
        },
      },
    },
  },
});
