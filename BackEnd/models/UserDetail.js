import { Schema, model } from "mongoose";

const UserDetailSchema = new Schema(
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

const User = model("UserInfo", UserDetailSchema);

export default User;
