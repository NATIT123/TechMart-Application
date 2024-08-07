const mongoose = require("mongoose");
const mongoUrl = process.env.mongoUrl || "";
mongoose
  .connect(mongoUrl)
  .then(() => {
    console.log("Connect Database");
  })
  .catch((err) => console.log(err));
