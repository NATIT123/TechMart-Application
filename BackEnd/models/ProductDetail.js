import mongoose, { Schema, model } from "mongoose";

const ProductDetailSchema = new Schema(
  {
    name: String,
    image: [
      {
        type: String,
      },
    ],
    price: String,
    rate: Number,
    description: String,
    category: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Category",
    },
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
  }
);

const Product = model("Products", ProductDetailSchema);

export default Product;
