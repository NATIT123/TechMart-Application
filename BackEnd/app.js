const express = require("express");
const app = express();
const mongoose = require("mongoose");
require("dotenv").config();
app.use(express.json());
const PORT = parseInt(process.env.PORT) || 3001;
app.get("/", (req, res) => {
  res.send({ status: "Started" });
});
app.listen(PORT, () => {
  console.log(`Running on Port ${PORT} `);
});
