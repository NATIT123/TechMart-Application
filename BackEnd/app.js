import express from "express";
const app = express();
import dotenv from "dotenv";
import connectDb from "./utils/dbConnect.js";
import user from "./routes/users.js";
import product from "./routes/products.js";
import category from "./models/CategoryDetail.js";
dotenv.config();
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
const PORT = parseInt(process.env.PORT) || 3000;

connectDb();

app.get("/", (req, res) => {
  res.send("Started");
});

///Handle User
app.use("/user", user);

app.use("/product", product);

app.use("/category", category);

app.listen(PORT, () => {
  console.log(`Running on Port ${PORT} `);
});
