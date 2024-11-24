import dotenv from "dotenv";
dotenv.config();
import { genSalt, hash as _hash, compare } from "bcrypt";
import User from "../models/UserDetail.js";
const saltRounds = parseInt(process.env.saltRounds) || 10;
import { Types } from "mongoose";

import mongoose from "mongoose";

export const getAllUser = async (req, res) => {
  try {
    const users = await User.find({ isDeleted: false });
    res.send({ status: "ok", data: users });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const addUser = async (req, res) => {
  const { fullName, address, email, phone, password, image, role } = req.body;
  const { adminId } = req.params;
  if (!mongoose.Types.ObjectId.isValid(adminId)) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  const oldUser = await User.findOne({ email: email });
  let encryptPassword;
  if (oldUser) {
    return res.send({ status: "notok", data: "User already registered" });
  } else {
    const ObjectId = Types.ObjectId.createFromHexString(adminId);
    const admin = await User.findOne({ _id: ObjectId });
    await genSalt(saltRounds)
      .then((salt) => {
        return _hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    try {
      const userObject = {
        fullName: fullName,
        email: email,
        phone: phone,
        password: encryptPassword,
        image,
        address,
        role,
        createdBy: {
          _id: ObjectId,
          email: admin.email,
        },
      };
      const savedUser = await User.create(userObject);
      res.send({
        status: "ok",
        data: `User created successfully:${savedUser._id.toString()}`,
      });
    } catch (err) {
      res.send({ status: "error", data: err.toString() });
    }
  }
};

export const registerUser = async (req, res) => {
  const { fullName, address, email, phone, password, image, role } = req.body;

  const oldUser = await User.findOne({ email: email });
  let encryptPassword;
  if (oldUser) {
    return res.send({ status: "notok", data: "User already registered" });
  } else {
    await genSalt(saltRounds)
      .then((salt) => {
        return _hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    try {
      const userObject = {
        fullName: fullName,
        email: email,
        phone: phone,
        password: encryptPassword,
        image,
        address,
        role,
      };
      const savedUser = await User.create(userObject);
      res.send({
        status: "ok",
        data: `User created successfully:${savedUser.insertedId.toString()}`,
      });
    } catch (err) {
      res.send({ status: "error", data: err.toString() });
    }
  }
};

export const resgisterUserByGoogle = async (req, res) => {
  const { fullName, email, phone, password, image } = req.body;
  const oldUser = await User.findOne({ email });
  if (oldUser) {
    return res.send({
      status: "ok",
      data: `Login Succesfully:${oldUser._id.toString()}:${oldUser.fullName.toString()}:${oldUser.image.toString()}:${oldUser.address.toString()}:${oldUser.phone.toString()}}`,
    });
  }
  let encryptPassword;
  try {
    await genSalt(saltRounds)
      .then((salt) => {
        return _hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    const userObject = {
      fullName: fullName,
      email: email,
      phone: phone,
      password: encryptPassword,
      image,
      address,
    };
    const savedUser = await User.create(userObject);
    res.send({
      status: "ok",
      data: `User created successfully:${savedUser._id.toString()}`,
    });
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const loginUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    const oldUser = await User.findOne({ email: email });

    if (!oldUser) {
      return res.send({
        status: "notok",
        data: "Email or password is not found",
      });
    }

    const match = compare(password, oldUser.password);
    if (match) {
      res.send({
        status: "ok",
        data: `Login Succesfully:${oldUser._id.toString()}:${oldUser.fullName.toString()}:${oldUser.image.toString()}:${oldUser.address.toString()}:${oldUser.phone.toString()}:${oldUser.role.toString()}`,
      });
    } else {
      return res.send({
        status: "notok",
        data: "Email or password is not found",
      });
    }
  } catch (err) {
    res.send({ status: "error", data: err.toString() });
  }
};

export const phoneIsExist = async (req, res) => {
  try {
    const { phone } = req.query;
    const user = await User.findOne({ phone: phone });

    if (!user) {
      return res.send({
        status: "notok",
        data: "Phone is not exist",
      });
    } else {
      return res.send({
        status: "ok",
        data: `Phone is exist:${user._id.toString()}`,
      });
    }
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

export const changePassword = async (req, res) => {
  const { id } = req.params;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  const { password } = req.query;

  let encryptPassword;
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const user = await User.findOne({
      _id: ObjectId,
    });
    if (!user) {
      return res.send({
        status: "notok",
        data: "User is not exist",
      });
    }

    await genSalt(saltRounds)
      .then((salt) => {
        return _hash(password, salt);
      })
      .then((hash) => {
        encryptPassword = hash;
      })
      .catch((err) => console.error(err.message));

    await User.updateOne(
      { _id: ObjectId },
      {
        $set: {
          password: encryptPassword,
        },
      }
    );
    res.send({ status: "ok", data: "Change Password Successfully" });
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

export const detailUser = async (req, res) => {
  const { email } = req.query;
  try {
    const user = await User.findOne({ email: email });
    if (!user) {
      return res.send({
        status: "notok",
        message: "User is not exist",
      });
    }

    const userRespone = {
      id: user._id.toString(),
      email: user.email,
      phone: user.phone,
      password: user.password,
      image: user.image,
      address: user.address,
    };

    res.send({ status: "ok", data: userRespone });
  } catch (err) {
    res.send({ status: "error", message: err.message });
  }
};

export const updateUser = async (req, res) => {
  const { id } = req.params;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  const { fullName, address, phone, image, email } = req.body;
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const user = await User.findOne({ _id: ObjectId });
    if (!user) {
      return res.send({
        status: "notok",
        data: "User is not exist",
      });
    }
    await User.updateOne(
      { _id: ObjectId },
      {
        $set: {
          fullName,
          email,
          phone,
          image,
          address,
          email,
        },
      }
    );
    res.send({ status: "ok", data: "Update User Succeessfully" });
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};

export const deleteUser = async (req, res) => {
  const { id } = req.params;
  const { idAdmin } = req.query;
  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.send({ status: "error", data: "Id is not valid" });
  }
  try {
    const ObjectId = Types.ObjectId.createFromHexString(id);
    const user = await User.findOne({ _id: ObjectId });
    if (!user) {
      return res.send({
        status: "notok",
        data: "User is not exist",
      });
    }
    if (!mongoose.Types.ObjectId.isValid(idAdmin)) {
      return res.send({ status: "error", data: "Id is not valid" });
    }
    const ObjectAdminId = Types.ObjectId.createFromHexString(idAdmin);
    const admin = await User.findOne({ _id: ObjectAdminId });
    await User.updateOne(
      { _id: id },
      {
        ...req.body,
        deletedBy: {
          _id: ObjectId,
          email: admin.email,
        },
        isDeleted: true,
      }
    );
  } catch (err) {
    res.send({ status: "error", data: err.message });
  }
};
