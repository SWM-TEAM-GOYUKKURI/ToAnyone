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
          if(loaderContext.resourcePath.endsWith("/styles/globals.scss")) {
            return content;
          }

          return `@import "@/styles/globals.scss"; ${content}`;
        },
      },
    },
  },
  pluginOptions: {
    vuetify: {
      // DO NOT remove `pluginOptions.vuetify` even if it's empty
    },
  },
});
