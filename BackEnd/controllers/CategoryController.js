import dotenv from "dotenv";
dotenv.config();
import Category from "../models/CategoryDetail.js";
import User from "../models/UserDetail.js";
import mongoose, { Types } from "mongoose";

export const getAllCategory = async (req, res) => {
  try {
    const category = await Category.find({ isDeleted: false });
    res.send({ status: "ok", data: category });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const addCategory = async (req, res) => {
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
    const isExist = await Category.findOne({ name });

    if (!isExist) {
      return res.send({
        status: "notok",
        data: "Name Category is exist",
      });
    }
    await Category.create({
      ...req.body,
      createdBy: {
        _id: ObjectId,
        email: user.email,
      },
    });
    res.send({
      status: "ok",
      data: `Add Category Successfully`,
    });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const getCategory = async (req, res) => {
  const { id } = req.params;
  try {
    const category = await Category.findOne({ _id: id });
    if (!category) {
      return res.send({
        status: "notok",
        message: "Category is not exist",
      });
    }

    const categoryRespone = {
      id: category._id.toString(),
      name: category.name,
      image: category.image,
      price: category.price,
      rate: category.rate,
      description: category.description,
    };

    res.send({ status: "ok", data: categoryRespone });
  } catch (err) {
    res.send({ status: "error", message: err.message });
  }
};

export const updateCategory = async (req, res) => {
  const { id, adminId } = req.params;
  if (
    !mongoose.Types.ObjectId.isValid(id) ||
    !mongoose.Types.ObjectId.isValid(idAdmin)
  ) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const category = await Category.findOne({ _id: ObjectId });

    const ObjectAdminId = Types.ObjectId.createFromHexString(adminId);
    const admin = await User.findOne({ _id: ObjectAdminId });
    if (!category) {
      return res.send({
        status: "notok",
        data: "Category is not exist",
      });
    }
    await Category.updateOne(
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
    res.send({ status: "ok", data: "Update Category Succeessfully" });
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

export const deleteCategory = async (req, res) => {
  const { id, adminId } = req.params;
  if (
    !mongoose.Types.ObjectId.isValid(id) ||
    !mongoose.Types.ObjectId.isValid(adminId)
  ) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const category = await Category.findOne({ _id: ObjectId });
    if (!category) {
      return res.send({
        status: "notok",
        data: "Category is not exist",
      });
    }
    const ObjectAdminId = Types.ObjectId.createFromHexString(adminId);
    const admin = await User.findOne({ _id: ObjectAdminId });
    await category.updateOne(
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
