import dotenv from "dotenv";
dotenv.config();
import Product from "../models/ProductDetail.js";
import User from "../models/UserDetail.js";
import mongoose, { Types } from "mongoose";

export const getAllProduct = async (req, res) => {
  try {
    const products = await Product.find({ isDeleted: false });
    res.send({ status: "ok", data: products });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const addProduct = async (req, res) => {
  try {
    const { id } = req.params;
    if (!mongoose.Types.ObjectId.isValid(id)) {
      return res.send({ status: "error", data: "Id is not valid" });
    }
    const ObjectId = Types.ObjectId.createFromHexString(id);

    const user = await User.findOne({ _id: ObjectId });
    if (!user) {
      return res.send({
        status: "notok",
        data: "user is not exist",
      });
    }
    const { name } = req.body;
    const isExist = await Product.findOne({ name });

    if (!isExist) {
      return res.send({
        status: "notok",
        data: "Name product is exist",
      });
    }
    await Product.create({
      ...req.body,
      createdBy: {
        _id: ObjectId,
        email: user.email,
      },
    });
    res.send({
      status: "ok",
      data: `Add Product Successfully`,
    });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const getProduct = async (req, res) => {
  const { id } = req.params;
  try {
    const product = await product.findOne({ _id: id });
    if (!product) {
      return res.send({
        status: "notok",
        message: "Product is not exist",
      });
    }

    const productRespone = {
      id: product._id.toString(),
      name: product.name,
      image: product.image,
      price: product.price,
      rate: product.rate,
      description: product.description,
    };

    res.send({ status: "ok", data: productRespone });
  } catch (err) {
    res.send({ status: "error", message: err.message });
  }
};

export const updateProduct = async (req, res) => {
  const { id, adminId } = req.params;
  if (
    !mongoose.Types.ObjectId.isValid(id) ||
    !mongoose.Types.ObjectId.isValid(idAdmin)
  ) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const product = await product.findOne({ _id: ObjectId });

    const ObjectAdminId = Types.ObjectId.createFromHexString(adminId);
    const admin = await User.findOne({ _id: ObjectAdminId });
    if (!product) {
      return res.send({
        status: "notok",
        data: "product is not exist",
      });
    }
    await Product.updateOne(
      { _id: ObjectId },
      {
        $set: {
          ...req.body,
          updatedBy: {
            id: ObjectId._id,
            email: admin.email,
          },
        },
      }
    );
    res.send({ status: "ok", data: "Update Product Succeessfully" });
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

export const deleteProduct = async (req, res) => {
  const { id, adminId } = req.params;
  if (
    !mongoose.Types.ObjectId.isValid(id) ||
    !mongoose.Types.ObjectId.isValid(adminId)
  ) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const product = await product.findOne({ _id: ObjectId });
    if (!product) {
      return res.send({
        status: "notok",
        data: "product is not exist",
      });
    }
    const ObjectAdminId = Types.ObjectId.createFromHexString(adminId);
    const admin = await User.findOne({ _id: ObjectAdminId });
    await product.updateOne(
      { _id: id },
      {
        ...req.body,
        deletedBy: {
          _id: ObjectId._id,
          email: admin.email,
        },
        isDeleted: true,
      }
    );
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};
