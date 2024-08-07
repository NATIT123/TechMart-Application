const express = require("express");
const app = express();
require("dotenv").config();
app.use(express.json());
const PORT = parseInt(process.env.PORT) || 3001;
require("./utils/dbConnect");
const user = require("./routes/users");

app.get("/", (req, res) => {
  res.send("Started");
});

///Handle User
app.use("/user", user);

app.listen(PORT, () => {
  console.log(`Running on Port ${PORT} `);
});
