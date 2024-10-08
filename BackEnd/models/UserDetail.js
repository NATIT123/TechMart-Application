const mongoose = require("mongoose");

const UserDetailSchema = new mongoose.Schema(
  {
    fullName: String,
    email: String,
    phone: String,
    password: String,
    image: String,
    address: String,
    role: String,
  },
  {
    collection: "UserInfo",
  }
);

const User = mongoose.model("UserInfo", UserDetailSchema);

module.exports = User;
