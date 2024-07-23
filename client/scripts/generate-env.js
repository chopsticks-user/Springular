const fs = require("fs");
const path = require("path");

const filepath = path.resolve(__dirname, '../.env');

const content = `
ANGULAR_PROD_SERVER_ADDRESS=${process.env.ANGULAR_PROD_SERVER_ADDRESS}
ANGULAR_PROD_API_ROOT_ADDRESS=${process.env.ANGULAR_PROD_API_ROOT_ADDRESS}
`;

// const content = Object.entries(envVariables)
//   .map(([key, value]) => `${key}=${value}`)
//   .join('\n');

fs.writeFileSync(filepath, content.trim(), { encoding: "utf8" });
