const express = require("express");
const app = express();
const mongoose = require("mongoose");
const bcrypt = require("bcrypt");
require("dotenv").config();
app.use(express.json());
const PORT = parseInt(process.env.PORT) || 3001;
require("./models/UserDetail");
const User = mongoose.model("UserInfo");
const saltRounds = parseInt(process.env.saltRounds);
require("./utils/dbConnect");

app.get("/", (req, res) => {
  res.send({ status: "Started" });
});

app.post("/user/register", async (req, res) => {
  const { name, email, phone, password, image } = req.body;

  const oldUser = await User.findOne({ email: email });
  let encryptPassword;
  if (oldUser != null) {
    return res.send({ status: "notok", data: "User already registered" });
  } else {
    await bcrypt
      .genSalt(saltRounds)
      .then((salt) => {
        return bcrypt.hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    try {
      const userObject = {
        name: name,
        email: email,
        phone: phone,
        password: encryptPassword,
        image,
      };
      const savedUser = await User.collection.insertOne(userObject);
      console.log(savedUser.insertedId.toString());
      res.send({
        status: "ok",
        data: `User created successfully:${savedUser.insertedId.toString()}`,
      });
    } catch (err) {
      res.send({ status: "error", data: err.toString() });
    }
  }
});

app.post("/user/login", async (req, res) => {
  const { email, password } = req.body;

  try {
    const oldUser = await User.collection.findOne({ email: email });

    if (!oldUser) {
      return res.send({
        status: "notok",
        data: "Email or password is not found",
      });
    }

    console.log(oldUser.password);

    if (bcrypt.compare(password, oldUser.password)) {
      res.send({
        status: "ok",
        data: `Login Succesfully:${oldUser.insertedId}:${oldUser.fullName}:${oldUser.image}`,
      });
    }
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
});

app.listen(PORT, () => {
  console.log(`Running on Port ${PORT} `);
});
