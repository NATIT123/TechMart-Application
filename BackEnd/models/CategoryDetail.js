import mongoose, { Schema, model } from "mongoose";

const CategoryDetailSchema = new Schema(
  {
    name: String,
    description: String,
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

const Category = model("Category", CategoryDetailSchema);

export default Category;
