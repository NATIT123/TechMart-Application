import mongoose, { Schema, model } from "mongoose";

const CategoryDetailSchema = new Schema(
  {
    name: String,
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
    createdAt: Date,
    updatedAt: Date,
    isDeleted: { type: Boolean, default: false },
  },
  {
    timestamps: true,
  },
  {
    collection: "Category",
  }
);

const Category = model("Category", CategoryDetailSchema);

export default Category;
