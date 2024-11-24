import mongoose, { Schema, model } from "mongoose";

const UserDetailSchema = new Schema(
  {
    fullName: String,
    email: String,
    phone: String,
    password: String,
    image: String,
    address: String,
    role: { type: String, default: "User" },
    createdBy: {
      _id: mongoose.Schema.Types.ObjectId,
      email: String,
    },
    updatedBy: {
      _id: mongoose.Schema.Types.ObjectId,
      email: String,
    },
    deletedBy: {
      _id: mongoose.Schema.Types.ObjectId,
      email: String,
    },
    isDeleted: { type: Boolean, default: false },
  },
  {
    timestamps: true,
  }
);

const User = model("Users", UserDetailSchema);

export default User;
