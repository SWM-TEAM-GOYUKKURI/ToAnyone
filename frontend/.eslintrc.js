module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    "plugin:vue/vue3-essential",
    "@vue/standard",
    "@vue/typescript/recommended",
  ],
  parserOptions: {
    ecmaVersion: 2020,
  },
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    "quotes": ["error", "double"],
    "semi": ["error", "always"],
    "quote-props": ["error", "consistent-as-needed"],
    "comma-dangle": ["error", "always-multiline"],
    "keyword-spacing": ["error", {
      before: true,
      after: true,
      overrides: {
        if: { after: false },
        switch: { after: false },
        for: { after: false },
        while: { after: false },
      },
    }],
    "space-before-function-paren": ["error", {
      anonymous: "never",
      named: "never",
      asyncArrow: "always",
    }],
  },
  overrides: [
    {
      files: [
        "**/__tests__/*.{j,t}s",
        "**/tests/unit/**/*.spec.{j,t}s",
      ],
      env: {
        jest: true,
      },
    },
  ],
};
